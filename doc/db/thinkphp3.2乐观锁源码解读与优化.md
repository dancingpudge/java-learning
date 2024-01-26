## 索引

一、使用乐观锁的目的
二、乐观锁实现的方法
三、thinkphp3.2中乐观锁的实现
四、优化thinkphp3.2中的乐观锁

## 使用乐观锁的目的 

简单的来说，使用乐观锁的目的就是保证数据不会被错误的写入，并且在保护写入的过程中，并不影响其他用户对这个数据的读取（乐观的去读，认为我读的数据都是别人没有改过的）。

## 乐观锁实现的方法

    乐观锁实现的方法，换句话说就是如何保护数据不被错误的写入？
    举个错误的写入例子，一个教务系统中，某个同学的考试成绩总分数算错了，需要科目A老师扣除总分中的10分，需要科目B的老师扣除总分中的20分；这时科目A的老师和科目B的老师都查阅了该学生的分数是180分，然后科目A的老师扣除10分，输入170分完成修改；科目B的老师扣除20分后输入160完成修改；整个过程完成后，这位同学的分数就变成了160分，实际正确的修改应该是150分才对（180-10-20）。
    那如何保证这位同学的分数被正确的写入呢？这个就是乐观锁实现的方法：`提交版本必须大于记录当前版本`才能执行更新。为这位同学的分数加个数据版本，这个数据版本就是一个数字，记录当前是第几次修改（数据库保存）。
    php中的实现：每次查询成功时，记录当前数据的版本号，可以使用一个隐藏表单记录（如果是api接口就拿个变量存储一下），再提交修改时，将这个值+1操作过后一起提交过去（数据表要新增一个数据版本字段，用于记录版本），提交过去就存在以下情况：

提交的数据版本大于数据库版本，满足 “ ***提交版本必须大于记录当前版本才能执行更新*** “ 的乐观锁策略，执行更新的操作；
提交的数据版本小于或者等于当前的数据版本时，不满足提交版本必须大于记录当前版本才能执行更新 “ 的乐观锁策略，说明数据已经发送过更改了，就不允许用户修改，程序提示数据已过期，请重新读取后再操作。

## thinkphp3.2中乐观锁的实现

thinkphp3.2中，乐观锁实现的对应代码的位置是：simplewind/Core/Library/Think/Model/AdvModel.class.php
具体代码就不贴出了，简单说一下tp3.2实现的过程：

新数据插入时，自动插入数据版本对应的值，方法入口：

```php
// 写入前的回调方法
protected function _before_insert(&$data,$options='') {
    // 记录乐观锁
    $data = $this->recordLockVersion($data);
    //..............
}
```


数据插入完成后就用了初始的版本号：0

2.每次查询成功的回调中，缓存当前查询结果行的数据版本值，方法入口：

```php
// 查询成功后的回调方法
protected function _after_find(&$result,$options='') {
    //..............
    // 缓存乐观锁
    $this->cacheLockVersion($result);
}
```


当当前的查询，包含数据版本这个字段时，这个方法就将当前行的主键ID形成一个唯一key，记录数据版本，储存到session中

3.每次执行更新前，检测session是否存在对应的数据版本key，方法入口：

```php
// 更新前的回调方法
protected function _before_update(&$data,$options='') {
    // 检查乐观锁
    $pk     =   $this->getPK();
    if(isset($options['where'][$pk])){
        $id     =   $options['where'][$pk];   
        if(!$this->checkLockVersion($id,$data)) {
            return false;
        }
    }
  //................
}
```

  checkLockVersion方法中，就是判断session中一开始查询成功时储存的当前行的数据版本是否与当前模型对应数据库中的数据版本是否一致，当一致时再继续进行更新的操作，并且将数据版本+1提交到修改中；如果不一致就直接返回false，中止修改。

可能存在的问题与优化

tp中乐观锁检测的部分：

```php
/**
 * 检查乐观锁
 * @access protected
 * @param inteter $id  当前主键     
 * @param array $data  当前数据
 * @return mixed
 */
protected function checkLockVersion($id,&$data) {
    // 检查乐观锁
    $identify   = $this->name.'_'.$id.'_lock_version';
    if($this->optimLock && isset($_SESSION[$identify])) {
        $lock_version = $_SESSION[$identify];
        $vo   =  $this->field($this->optimLock)->find($id);
        $_SESSION[$identify]     =   $lock_version;
        $curr_version = $vo[$this->optimLock];
        if(isset($curr_version)) {
            if($curr_version>0 && $lock_version != $curr_version) {
                // 记录已经更新
                $this->error = L('_RECORD_HAS_UPDATE_');
                return false;
            }else{
                // 更新乐观锁
                $save_version = $data[$this->optimLock];
                if($save_version != $lock_version+1) {
                    $data[$this->optimLock]  =   $lock_version+1;
                }
                $_SESSION[$identify]     =   $lock_version+1;
            }
        }
    }
    return true;
}
```



### 可能存在的问题1 
乐观锁验证通过后，立马将session的值改变了，代码：$_SESSION[$identify] = $lock_version+1;；

###  可能存在的问题2 

由于更新时，**并不具备原子性**，可能两个并发的更新会同时执行，均符合乐观锁的版本判断。（并发时，读取出来的版本和数据库均一致，都提交了更新操作）

## 优化：
### 解决问题1 
不使用session进行记录，这样操作会使已经读取的记录版本发生混乱，应该将记录值和提交的数据绑定在一起，例如是一次表单提交，查询成功后就使用一个hidden表单，记录当前查询的版本值，和其他数据一并提交；再或者是一个api接口，提交更新时，查询当前行的版本号，和其他数据一并提交；然后再检测乐观锁部分，也就是checkLockVersion($id,&$data)中取出，data中的版本号字段和数据库的版本再进行对比。

### 解决问题2 
每次执行修改前，加一个redis的锁，可以使用set($redisLock,1,['NX', 'EX' => 10])) 达到原子性，其中的NX参数表示，只有当key不存在时，才设置成功，设置成功才可以进行修改操作的提交，这个操作是具有原子性的，并发进来的修改只有一个修改能成功；如果设置不成功，说明这行记录已经再进行修改的过程中了，我们就可以选择直接结束这个请求，或者让这个请求进行等待；然后再执行完操作过后，删除这个redis锁del($redisLock);即可；

```php
if (!$this->redis->set($redisLock,1,['NX', 'EX' => $this->redisLockExpire])) {   //设置失败，进行重试
    addDebugLog('设置redis锁失败；开始重试；重试次数'.($retry_count+1).'数据表：'.$this->name.'；数据行：'.$id.'；本次操作版本：'.$lock_version);
    usleep($this->waitTime * 1000000);
    $retry_count+=1;
    return  $this->checkLockVersion($id,$data,$retry_count,$lock_version);
}
```

### 优化总结

成功后的回调不必记录版本号的值，再提交修改时，把当前的数据版本一起提交过去，在修改回调中取出这个提交修改的版本号的值，与数据库对比；每次执行检测时，使用redis中set方法的NX参数，实现更改的原子性。


## 原创 thinkphp3.2乐观锁源码解读与优化

  索引一、使用乐观锁的目的二、乐观锁实现的方法三、thinkphp3.2中乐观锁的实现四、优化thinkphp3.2中的乐观锁使用乐观锁的目的：简单的来说，使用乐观锁的目的就是保证数据不会被错误的写入，并且在保护写入的过程中，并不影响其他用户对这个数据的读取（乐观的去读，认为我读的数据都是别人没有改过的）。乐观锁实现的方法    乐观锁实现的方法，换句话说就是如何保护数据不被错误的...

  2020-03-29 16:49:16 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/readCountWhite.png) 373 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/commentCountWhite.png) 1

## 原创 thinkphp3.2源码-----错误和异常处理

  写在前面：tp3.2中每次载入入口文件时都会进行错误和异常的捕获，解读这一部分代码可以对以后的优化很有好处。处理概览：   错误捕获与处理：致命错误捕获：我们尝试在 Home/Index/index 下调用一个未定义的函数，会看到这样的提示页面：   我们可以看到tp3.2处理了致命异常的输出，并且

  2017-05-31 00:17:28 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/readCountWhite.png) 6319 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/commentCountWhite.png) 0

## 原创 thinkphp3.2源码 ----自动加载

  知识预备 命名空间 __autoload的工作机制写在前面 tp3.2实现的自动加载可以通过命名空间自动定位到类文件，实现这样的效果除了合理的处理手段外，比较有规律的项目结构也是必不可少的。源码(ThinkPHP/Library/Think/Think.class.php)： 注册自动加载函数public static function start() { /

  2017-06-02 11:46:12 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/readCountWhite.png) 1172 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/commentCountWhite.png) 0

## 转载 thinkphp3.2源码-----Driver.class.php

  原文地址：http://blog.csdn.net/lijingshan34/article/details/51979335#comments<?php// +----------------------------------------------------------------------// | ThinkPHP [ WE CAN DO IT JUST THINK IT ]//

  2017-06-07 14:59:10 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/readCountWhite.png) 1082 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/commentCountWhite.png) 0

##  原创 thinkphp3.2源码----获取并保存模型对象与数据库连接实例

  摘自文档： 在ThinkPHP中基础的模型类就是 Think\Model 类，该类完成了基本的CURD、ActiveRecord模式、连贯 操作和统计查询，一些高级特性被封装到另外的模型扩展中。 基础模型类的设计非常灵活，甚至可以无需进行任何模型定义，就可以进行相关数据表的ORM和CURD操 作，只有在需要封装单独的业务逻辑的时候，模型类才是必须被定义的。使用模型类

  2017-06-20 14:46:44 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/readCountWhite.png) 2544 ![img](https://csdnimg.cn/release/blogv2/dist/pc/img/commentCountWhite.png) 2

## 参考

https://www.cnblogs.com/programb/p/12771123.html

https://xie.infoq.cn/article/2085a95ad6b486d3905adc7d6

读取频繁使用乐观锁，写入频繁使用悲观锁。

有高并发和写，必须悲观锁；并发程度低，使用乐观锁；只有读，没有线程安全问题，不考虑锁