1. 五种数据结构
2. 支持持久化 RDB快照，AOF 命令记录
3. 比memchache好用
4. 发布订阅问题
5. 缓冲问题
6. 雪崩
7. 穿透
8. 安全
9. 常用命令
10. 业务场景
11. 更多的可能性



基于内存，非关系k-v数据库。

字符，集合，有序集合，列表，散列表

事务，持久化，lua脚步，集群，lru驱动事件



基于内存

数据结构简单

多路IO复用模型，非阻塞IO

单线程



string list set zset hash

kv缓存

粉丝列表，文章评论列表

粉丝列表的交集

去重支持排序

对象



计数器，缓存（热点数据），会话缓存

消息队列，发布订阅



淘汰策略

线程模型

事务

集群

主从架构

分布式

缓存异常

管理工具

## 其他问题

### 1. Redis与Memcached的区别？

两者都是非关系型内存键值数据库，现在公司一般都是用 Redis 来实现缓存，而且 Redis 自身也越来越强大了！Redis 与 Memcached 主要有以下不同：

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9tbWJpei5xcGljLmNuL21tYml6X3BuZy9iVVJQamdGcEdNUmR0djBRUHROYnQxSWxhNUpXVTdTd0ZpYVQ4czF0c0hKMkdoOFlPVWliN09UeEpkNjZHNzRIaWFKRjRmU0kzbkU5ajRjOHgwYmtoOGNPQS82NDA?x-oss-process=image/format,png)

(1) memcached所有的值均是简单的字符串，redis作为其替代者，支持更为丰富的数据类型

(2) redis的速度比memcached快很多

(3) redis可以持久化其数据

### 2. 如何保证缓存与数据库双写时的数据一致性？

你只要用缓存，就可能会涉及到缓存与数据库双存储双写，你只要是双写，就一定会有数据一致性的问题，那么你如何解决一致性问题？

一般来说，就是如果你的系统不是严格要求缓存+数据库必须一致性的话，缓存可以稍微的跟数据库偶尔有不一致的情况，最好不要做这个方案，读请求和写请求串行化，串到一个内存队列里去，这样就可以保证一定不会出现不一致的情况

串行化之后，就会导致系统的吞吐量会大幅度的降低，用比正常情况下多几倍的机器去支撑线上的一个请求。

还有一种方式就是可能会暂时产生不一致的情况，但是发生的几率特别小，就是先更新数据库，然后再删除缓存。

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9tbWJpei5xcGljLmNuL21tYml6X3BuZy9iVVJQamdGcEdNUmR0djBRUHROYnQxSWxhNUpXVTdTd3kzNnBLWXBPN0p6TDJrelJkUEYxak5Xb3N4ZkJ6ZUJUZExpYVpHOGliU2c1T05NZHBhVWppYW1qQS82NDA?x-oss-process=image/format,png)

### 3. Redis常见性能问题和解决方案？

1. Master最好不要做任何持久化工作，包括内存快照和AOF日志文件，特别是不要启用内存快照做持久化。
2. 如果数据比较关键，某个Slave开启AOF备份数据，策略为每秒同步一次。
3. 为了主从复制的速度和连接的稳定性，Slave和Master最好在同一个局域网内。
4. 尽量避免在压力较大的主库上增加从库
5. Master调用BGREWRITEAOF重写AOF文件，AOF在重写的时候会占大量的CPU和内存资源，导致服务load过高，出现短暂服务暂停现象。
6. 为了Master的稳定性，主从复制不要用图状结构，用单向链表结构更稳定，即主从关系为：Master<–Slave1<–Slave2<–Slave3…，这样的结构也方便解决单点故障问题，实现Slave对Master的替换，也即，如果Master挂了，可以立马启用Slave1做Master，其他不变。

### 4. Redis官方为什么不提供Windows版本？

因为目前Linux版本已经相当稳定，而且用户量很大，无需开发windows版本，反而会带来兼容性等问题。

### 5. 一个字符串类型的值能存储最大容量是多少？

512M

### 6. Redis如何做大量数据插入？

Redis2.6开始redis-cli支持一种新的被称之为pipe mode的新模式用于执行大量数据插入工作。

**7. 假如Redis里面有1亿个key，其中有10w个key是以某个固定的已知的前缀开头的，如果将它们全部找出来？**

使用keys指令可以扫出指定模式的key列表。

对方接着追问：如果这个redis正在给线上的业务提供服务，那使用keys指令会有什么问题？

这个时候你要回答redis关键的一个特性：redis的单线程的。keys指令会导致线程阻塞一段时间，线上服务会停顿，直到指令执行完毕，服务才能恢复。这个时候可以使用scan指令，scan指令可以无阻塞的提取出指定模式的key列表，但是会有一定的重复概率，在客户端做一次去重就可以了，但是整体所花费的时间会比直接用keys指令长。

**
\8. 使用Redis做过异步队列吗，是如何实现的？**

使用list类型保存数据信息，rpush生产消息，lpop消费消息，当lpop没有消息时，可以sleep一段时间，然后再检查有没有信息，如果不想sleep的话，可以使用blpop, 在没有信息的时候，会一直阻塞，直到信息的到来。redis可以通过pub/sub主题订阅模式实现一个生产者，多个消费者，当然也存在一定的缺点，当消费者下线时，生产的消息会丢失。

### 9. Redis如何实现延时队列？

使用sortedset，使用时间戳做score, 消息内容作为key,调用zadd来生产消息，消费者使用zrangbyscore获取n秒之前的数据做轮询处理。

**10. Redis回收进程如何工作的？**

1. 一个客户端运行了新的命令，添加了新的数据。
2. Redis检查内存使用情况，如果大于maxmemory的限制， 则根据设定好的策略进行回收。
3. 一个新的命令被执行，等等。
4. 所以我们不断地穿越内存限制的边界，通过不断达到边界然后不断地回收回到边界以下。

如果一个命令的结果导致大量内存被使用（例如很大的集合的交集保存到一个新的键），不用多久内存限制就会被这个内存使用量超越。

**11. Redis回收使用的是什么算法？**

LRU算法

> *作者：ThinkWon*
>
> *原文：https://thinkwon.blog.csdn.net/article/details/103522351*