20210911 19:00

  - 推荐使用2~3周的时间来消化接下来的面试题，
  - 遇到不会的没听说过名词请立刻去搜；
  - 文章中只是简答，如果想要详细了解的话还需要你自觉去搜索
[toc]

## 数据类型 

### 什么是基本数据类型 

答：⭐⭐⭐⭐⭐什么是引用数据类型？以及各个数据类型是如何存储的？

基本数据类型有

- Number
- String
- Boolean
- Null
- Undefined
- Symbol(ES6新增数据类型)
- bigInt

引用数据类型统称为Object类型，细分的话有

- Object
- Array
- Date
- Function
- RegExp

**基本数据类型的数据直接存储在栈中**；而**引用数据类型的数据存储在堆中**，每个对象在堆中有一个引用地址。**引用类型在栈中会保存他的引用地址**，以便快速查找到堆内存中的对象。

顺便提一句，**栈内存是自动分配内存的**。而**堆内存是动态分配内存的，不会自动释放**。所以每次使用完对象的时候都要把它设置为null，从而减少无用内存的消耗

## 类型转换

### 为什么0.2+0.1>0.3?

**答：⭐⭐⭐⭐**

因为在JS中，浮点数是使用64位固定长度来表示的，其中的1位表示符号位，11位用来表示指数位，剩下的52位尾数位，由于只有52位表示尾数位。

而`0.1`转为二进制是一个无限循环数`0.0001100110011001100......`(1100循环)

> 小数的十进制转二进制方法：https://jingyan.baidu.com/article/425e69e6e93ca9be15fc1626.html
> 要知道，小数的十进制转二进制的方法是和整数不一样的，推荐看一看

由于只能存储52位尾数位，所以会出现精度缺失，把它存到内存中再取出来转换成十进制就不是原来的`0.1`了，就变成了`0.100000000000000005551115123126`,而为什么02+0.1是因为

```js
// 0.1 和 0.2 都转化成二进制后再进行运算

0.00011001100110011001100110011001100110011001100110011010 +

0.0011001100110011001100110011001100110011001100110011010 =

0.0100110011001100110011001100110011001100110011001100111

// 转成十进制正好是 0.30000000000000004
```

 

### 那为什么0.2+0.3=0.5呢? 

```js
// 0.2 和 0.3 都转化为二进制后再进行计算

0.001100110011001100110011001100110011001100110011001101 +

0.0100110011001100110011001100110011001100110011001101 = 

0.10000000000000000000000000000000000000000000000000001 //尾数为大于52位

// 而实际取值只取52位尾数位，就变成了

0.1000000000000000000000000000000000000000000000000000   //0.5
```

 

 答：⭐⭐⭐⭐

`0.2` 和`0.3`分别转换为二进制进行计算：在内存中，它们的尾数位都是等于52位的，而他们相加必定大于52位，而他们相加又恰巧前52位尾数都是`0`，截取后恰好是`0.1000000000000000000000000000000000000000000000000000`也就是0.5

###  console.log(0.1)是0.1呢?

 答： ⭐⭐⭐ 那既然0.1不是0.1了，为什么？

在`console.log`的时候会\**二进制转换为十进制，十进制再会转为字符串**的形式，在转换的过程中发生了取近似值，所以打印出来的是一个近似值的字符串

### 判断数据类型有几种方法

答：⭐⭐⭐⭐⭐

- `typeof`

  - 缺点：`typeof null`的值为`Object`，无法分辨是`null`还是`Object`

- `instanceof`

  - 缺点：只能判断对象是否存在于目标对象的原型链上

- `constructor`

- `Object.prototype.toString.call()`

  - 一种最好的基本类型检测方式 `Object.prototype.toString.call()` ;它可以区分 null 、 string 、

    boolean 、 number 、 undefined 、 array 、 function 、 object 、 date 、 math 数据类型。

  - 缺点：不能细分为谁谁的实例

```js
// -----------------------------------------typeof

typeof undefined // 'undefined' 

typeof '10' // 'String' 

typeof 10 // 'Number' 

typeof false // 'Boolean' 

typeof Symbol() // 'Symbol' 

typeof Function // ‘function' 

typeof null // ‘Object’ 

typeof [] // 'Object' 

typeof {} // 'Object'

// -----------------------------------------instanceof

function Foo() { }

var f1 = new Foo();

var d = new Number(1)

console.log(f1 instanceof Foo);// true

console.log(d instanceof Number); //true

console.log(123 instanceof Number); //false   -->不能判断字面量的基本数据类型

// -----------------------------------------constructor

var d = new Number(1)

var e = 1

function fn() {

  console.log("ming");

}

var date = new Date();

var arr = [1, 2, 3];

var reg = /[hbc]at/gi;

console.log(e.constructor);//ƒ Number() { [native code] }

console.log(e.constructor.name);//Number

console.log(fn.constructor.name) // Function 

console.log(date.constructor.name)// Date 

console.log(arr.constructor.name) // Array 

console.log(reg.constructor.name) // RegExp

//-----------------------------------------Object.prototype.toString.call()

console.log(Object.prototype.toString.call(undefined)); // "[object Undefined]" 

console.log(Object.prototype.toString.call(null)); // "[object Null]" 

console.log(Object.prototype.toString.call(123)); // "[object Number]" 

console.log(Object.prototype.toString.call("abc")); // "[object String]" 

console.log(Object.prototype.toString.call(true)); // "[object Boolean]" 

function fn() {

  console.log("ming");

}

var date = new Date();

var arr = [1, 2, 3];

var reg = /[hbc]at/gi;

console.log(Object.prototype.toString.call(fn));// "[object Function]" 

console.log(Object.prototype.toString.call(date));// "[object Date]" 

console.log(Object.prototype.toString.call(arr)); // "[object Array]"

console.log(Object.prototype.toString.call(reg));// "[object RegExp]"
```



**instanceof原理**⭐⭐⭐⭐⭐

- instanceof原理实际上就是查找目标对象的原型链

```js
function myInstance(L, R) {//L代表instanceof左边，R代表右边

  var RP = R.prototype

  var LP = L.__proto__

  while (true) {

    if(LP == null) {

      return false

    }

    if(LP == RP) {

      return true

    }

    LP = LP.__proto__

  }

}

console.log(myInstance({},Object)); 
```

 

### 为什么typeof null是Object

**答：**⭐⭐⭐⭐

因为在`JavaScript`中，不同的对象都是使用二进制存储的，如果二进制前三位都是0的话，系统会判断为是`Object`类型，而null的二进制全是0，自然也就判断为`Object`

这个bug是初版本的JavaScript中留下的，扩展一下其他五种标识位：

- `000` 对象
- `1` 整型
- `010` 双精度类型
- `100`字符串
- `110`布尔类型

###   `==`和`===`有什么区别 

**答：**⭐⭐⭐⭐⭐

`===`是严格意义上的相等，会比较两边的数据类型和值大小

- 数据类型不同返回false
- 数据类型相同，但值大小不同，返回false

`==`是非严格意义上的相等，

- 两边类型相同，比较大小
- 两边类型不同，根据下方表格，再进一步进行比较。
  - Null == Undefined ->true
  - String == Number ->先将String转为Number，在比较大小
  - Boolean == Number ->现将Boolean转为Number，在进行比较
  - Object == String，Number，Symbol -> Object 转化为原始类型

### 手写call、apply、bind**⭐⭐⭐⭐⭐

**答：**

- call和apply实现思路主要是：
  - 判断是否是函数调用，若非函数调用抛异常
  - 通过新对象(context)来调用函数
    - 给context创建一个`fn`设置为需要调用的函数
    - 结束调用完之后删除`fn`
- bind实现思路
  - 判断是否是函数调用，若非函数调用抛异常
  - 返回函数
    - 判断函数的调用方式，是否是被new出来的
      - new出来的话返回空对象，但是实例的`__proto__`指向`_this`的`prototype`
  - 完成函数柯里化
    - `Array.prototype.slice.call()`

call:

```
Function.prototype.myCall = function (context) {



      // 先判断调用myCall是不是一个函数



      // 这里的this就是调用myCall的



      if (typeof this !== 'function') {



        throw new TypeError("Not a Function")



      }



 



      // 不传参数默认为window



      context = context || window



 



      // 保存this



      context.fn = this



 



      // 保存参数



      let args = Array.from(arguments).slice(1)   //Array.from 把伪数组对象转为数组



 



      // 调用函数



      let result = context.fn(...args)



 



      delete context.fn



 



      return result



 



    }



 
```

 

```
apply
Function.prototype.myApply = function (context) {



      // 判断this是不是函数



      if (typeof this !== "function") {



        throw new TypeError("Not a Function")



      }



 



      let result



 



      // 默认是window



      context = context || window



 



      // 保存this



      context.fn = this



 



      // 是否传参



      if (arguments[1]) {



        result = context.fn(...arguments[1])



      } else {



        result = context.fn()



      }



      delete context.fn



 



      return result



    }



 



 
```

 

```
bind
Function.prototype.myBind = function(context){



      // 判断是否是一个函数



      if(typeof this !== "function") {



        throw new TypeError("Not a Function")



      }



      // 保存调用bind的函数



      const _this = this 



      // 保存参数



      const args = Array.prototype.slice.call(arguments,1)



      // 返回一个函数



      return function F () {



        // 判断是不是new出来的



        if(this instanceof F) {



          // 如果是new出来的



          // 返回一个空对象，且使创建出来的实例的__proto__指向_this的prototype，且完成函数柯里化



          return new _this(...args,...arguments)



        }else{



          // 如果不是new出来的改变this指向，且完成函数柯里化



          return _this.apply(context,args.concat(...arguments))



        }



      } 



    }
```

 

### 字面量创建对象和new创建对象有什么区别，new内部都实现了什么，手写一个new**⭐⭐⭐⭐⭐

**答：**

字面量：

- 字面量创建对象更简单，方便阅读
- 不需要作用域解析，速度更快

new内部：

- 创建一个新对象
- 使新对象的`__proto__`指向原函数的`prototype`
- 改变this指向(指向新的obj)并执行该函数，执行结果保存起来作为result
- 判断执行函数的结果是不是null或Undefined，如果是则返回之前的新对象，如果不是则返回result

手写new

```
// 手写一个new



    function myNew(fn, ...args) {



      // 创建一个空对象



      let obj = {}



      // 使空对象的隐式原型指向原函数的显式原型



      obj.__proto__ = fn.prototype



      // this指向obj



      let result = fn.apply(obj, args)



      // 返回



      return result instanceof Object ? result : obj



    }
```

 

###  new，Object.create(null)

**答：**⭐⭐⭐ 字面量new出来的对象和 `Object.create(null)`创建出来的对象有什么区别

- 字面量和new创建出来的对象会继承Object的方法和属性，他们的隐式原型会指向Object的显式原型，
- 而 `Object.create(null)`创建出来的对象原型为null，作为原型链的顶端，自然也没有继承Object的方法和属性

## 执行栈和执行上下文

### 什么是作用域，作用域链？

**答：**⭐⭐⭐⭐

- 规定变量和函数的可使用范围称作作用域
- 每个函数都有一个作用域链，查找变量或者函数时，需要从局部作用域到全局作用域依次查找，这些作用域的集合称作作用域链。

###  什么是执行栈，执行上下文？

**答：**⭐⭐⭐⭐

执行上下文分为：

- 全局执行上下文
  - 创建一个全局的window对象，并规定this指向window，执行js的时候就压入栈底，关闭浏览器的时候才弹出
- 函数执行上下文
  - 每次函数调用时，都会新创建一个函数执行上下文
  - 执行上下文分为创建阶段和执行阶段
    - 创建阶段：函数环境会创建变量对象：arguments对象(并赋值)、函数声明(并赋值)、变量声明(不赋值)，函数表达式声明(不赋值)；会确定this指向；会确定作用域
    - 执行阶段：变量赋值、函数表达式赋值，使变量对象编程活跃对象
- eval执行上下文

执行栈：

- 首先栈特点：先进后出
- 当进入一个执行环境，就会创建出它的执行上下文，然后进行压栈，当程序执行完成时，它的执行上下文就会被销毁，进行弹栈。
- 栈底永远是全局环境的执行上下文，栈顶永远是正在执行函数的执行上下文
- 只有浏览器关闭的时候全局执行上下文才会弹出

## 闭包

> 很多人都吃不透js闭包，这里推荐一篇文章：[彻底理解js中的闭包](https://blog.csdn.net/dovlie/article/details/76339244)

### 什么是闭包？

**答：**⭐⭐⭐⭐⭐闭包的作用？闭包的应用？

函数执行，形成私有的执行上下文，使内部私有变量不受外界干扰，起到**保护**和**保存**的作用

作用：

- 保护
  - 避免命名冲突
- 保存
  - 解决循环绑定引发的索引问题
- 变量不会销毁
  - 可以使用函数内部的变量，使变量不会被垃圾回收机制回收

应用：

- 设计模式中的单例模式
- for循环中的保留i的操作
- 防抖和节流
- 函数柯里化

缺点

- 会出现内存泄漏的问题

## 原型和原型链

### 什么是原型？

**答：** ⭐⭐⭐⭐⭐ 什么是原型链？如何理解 

**原型：** 原型分为隐式原型和显式原型，每个对象都有一个隐式原型，它指向自己的构造函数的显式原型。

**原型链：** 多个`__proto__`组成的集合成为原型链

- 所有实例的`__proto__`都指向他们构造函数的`prototype`
- 所有的`prototype`都是对象，自然它的`__proto__`指向的是`Object()`的`prototype`
- 所有的构造函数的隐式原型指向的都是`Function()`的显示原型
- Object的隐式原型是null

## 继承

### 常用的继承方式有哪些？

**答：**⭐⭐⭐⭐⭐以及各个继承方式的优缺点。

原型继承、组合继承、寄生组合继承、ES6的extend

#### 原型继承

```js
// ----------------------方法一：原型继承
// 原型继承
// 把父类的实例作为子类的原型
// 缺点：子类的实例共享了父类构造函数的引用属性   不能传参
var person = {
  friends: ["a", "b", "c", "d"]
}
var p1 = Object.create(person)
p1.friends.push("aaa")//缺点：子类的实例共享了父类构造函数的引用属性
console.log(p1);
console.log(person);//缺点：子类的实例共享了父类构造函数的引用属性

```

#### 组合继承

```js
// ----------------------方法二：组合继承
// 在子函数中运行父函数，但是要利用call把this改变一下，
// 再在子函数的prototype里面new Father() ,使Father的原型中的方法也得到继承，最后改变Son的原型中的constructor
// 缺点：调用了两次父类的构造函数，造成了不必要的消耗，父类方法可以复用
// 优点可传参，不共享父类引用属性
function Father(name) {
  this.name = name
  this.hobby = ["篮球", "足球", "乒乓球"]
}
Father.prototype.getName = function () {
  console.log(this.name);
}
function Son(name, age) {
  Father.call(this, name)
  this.age = age
}
Son.prototype = new Father()
Son.prototype.constructor = Son
var s = new Son("ming", 20)
console.log(s);
```

#### 寄生组合继承

```js
// ----------------------方法三：寄生组合继承
function Father(name) {
  this.name = name
  this.hobby = ["篮球", "足球", "乒乓球"]
}
Father.prototype.getName = function () {
  console.log(this.name);
}
function Son(name, age) {
  Father.call(this, name)
  this.age = age
}
Son.prototype = Object.create(Father.prototype)
Son.prototype.constructor = Son
var s2 = new Son("ming", 18)
console.log(s2);
```

#### extend

```js
// ----------------------方法四：ES6的extend(寄生组合继承的语法糖)
//     子类只要继承父类，可以不写 constructor ，一旦写了，则在 constructor 中的第一句话
// 必须是 super 。
class Son3 extends Father { // Son.prototype.__proto__ = Father.prototype
  constructor(y) {
    super(200)  // super(200) => Father.call(this,200)
    this.y = y
  }
}
```

 

## 内存泄露、垃圾回收机制

### 什么是内存泄漏

**答：**⭐⭐⭐⭐⭐

 内存泄露是指不再用的内存没有被及时释放出来，导致该段内存无法被使用就是内存泄漏

### 为什么会导致的内存泄漏

**答：**⭐⭐⭐⭐⭐

内存泄漏指我们无法在通过js访问某个对象，而垃圾回收机制却认为该对象还在被引用，因此垃圾回收机制不会释放该对象，导致该块内存永远无法释放，积少成多，系统会越来越卡以至于崩溃

### 垃圾回收机制都有哪些策略？

**答：**⭐⭐⭐⭐⭐

- 标记清除法
  - 垃圾回收机制获取根并标记他们，然后访问并标记所有来自它们的引用，然后在访问这些对象并标记它们的引用…如此递进结束后若发现有没有标记的(不可达的)进行删除，进入执行环境的不能进行删除
- 引用计数法
  - 当声明一个变量并给该变量赋值一个引用类型的值时候，该值的计数+1，当该值赋值给另一个变量的时候，该计数+1，当该值被其他值取代的时候，该计数-1，当计数变为0的时候，说明无法访问该值了，垃圾回收机制清除该对象

## 深拷贝和浅拷贝

**手写浅拷贝深拷贝**⭐⭐⭐⭐⭐

```js
// ----------------------------------------------浅拷贝
// 只是把对象的属性和属性值拷贝到另一个对象中
var obj1 = {
  a: {
    a1: { a2: 1 },
    a10: { a11: 123, a111: { a1111: 123123 } }
  },
  b: 123,
  c: "123"
}
// 方式1
function shallowClone1(o) {
  let obj = {}
  for (let i in o) {
    obj[i] = o[i]
  }
  return obj
}

// 方式2
var shallowObj2 = { ...obj1 }
// 方式3
var shallowObj3 = Object.assign({}, obj1)
let shallowObj = shallowClone1(obj1);
shallowObj.a.a1 = 999
shallowObj.b = true
console.log(obj1);  //第一层的没有被改变，一层以下就被改变了
// ----------------------------------------------深拷贝
// 简易版  
function deepClone(o) {
  let obj = {}
  for (var i in o) {
    // if(o.hasOwnProperty(i)){
    if (typeof o[i] === "object") {
      obj[i] = deepClone(o[i])
    } else {
      obj[i] = o[i]
    }
    // }
  }
  return obj
}

var myObj = {
 a: {
    a1: { a2: 1 },
    a10: { a11: 123, a111: { a1111: 123123 } }
  },
  b: 123,
  c: "123"
}
var deepObj1 = deepClone(myObj)
deepObj1.a.a1 = 999
deepObj1.b = false
console.log(myObj);
// 简易版存在的问题：参数没有做检验，传入的可能是 Array、null、regExp、Date
function deepClone2(o) {
  if (Object.prototype.toString.call(o) === "[object Object]") {  //检测是否为对象
    let obj = {}
    for (var i in o) {
      if (o.hasOwnProperty(i)) {
        if (typeof o[i] === "object") {
          obj[i] = deepClone(o[i])
        } else {
          obj[i] = o[i]
        }
      }
    }
    return obj
  } else {
    return o
  }
}
function isObject(o) {
  return Object.prototype.toString.call(o) === "[object Object]" || Object.prototype.toString.call(o) === "[object Array]"
}
// 继续升级，没有考虑到数组，以及ES6中的map、set、weakset、weakmap
function deepClone3(o) {
  if (isObject(o)) {//检测是否为对象或者数组
    let obj = Array.isArray(o) ? [] : {}
    for (let i in o) {
      if (isObject(o[i])) {
        obj[i] = deepClone(o[i])
      } else {
        obj[i] = o[i]
      }
    }
    return obj
  } else {
    return o
  }
}
// 有可能碰到循环引用问题  var a = {}; a.a = a; clone(a);//会造成一个死循环
// 循环检测
// 继续升级
function deepClone4(o, hash = new map()) {
  if (!isObject(o)) return o//检测是否为对象或者数组
  if (hash.has(o)) return hash.get(o)
  let obj = Array.isArray(o) ? [] : {}
  hash.set(o, obj)
  for (let i in o) {
    if (isObject(o[i])) {
      obj[i] = deepClone4(o[i], hash)
    } else {
      obj[i] = o[i]
    }
  }
  return obj
}
// 递归易出现爆栈问题
//  将递归改为循环，就不会出现爆栈问题了
var a1 = { a: 1, b: 2, c: { c1: 3, c2: { c21: 4, c22: 5 } }, d: 'asd' };
var b1 = { b: { c: { d: 1 } } }
function cloneLoop(x) {
  const root = {};
  // 栈 
  const loopList = [  //->[]->[{parent:{a:1,b:2},key:c,data:{ c1: 3, c2: { c21: 4, c22: 5 } }}]
    {
      parent: root,
      key: undefined,
      data: x,
    }
  ];
  while (loopList.length) {
    // 深度优先
    const node = loopList.pop();
    const parent = node.parent; //{} //{a:1,b:2}
    const key = node.key; //undefined //c
    const data = node.data; //{ a: 1, b: 2, c: { c1: 3, c2: { c21: 4, c22: 5 } }, d: 'asd' }  //{ c1: 3, c2: { c21: 4, c22: 5 } }}
    // 初始化赋值目标，key 为 undefined 则拷贝到父元素，否则拷贝到子元素
    let res = parent; //{}->{a:1,b:2,d:'asd'} //{a:1,b:2}->{}
    if (typeof key !== 'undefined') {
      res = parent[key] = {};
    }
    for (let k in data) {
      if (data.hasOwnProperty(k)) {
        if (typeof data[k] === 'object') {
          // 下一次循环 
          loopList.push({
            parent: res,
            key: k,
            data: data[k],
          })
        } else {
          res[k] = data[k];
        }
      }
    }
  }
  return root
}
function deepClone5(o) {
   let result = {}
  let loopList = [
    {
       parent: result,
      key: undefined,
      data: o
    }
   ]
  while (loopList.length) {
    let node = loopList.pop()
    let { parent, key, data } = node
    let anoPar = parent
    if (typeof key !== 'undefined') {
      anoPar = parent[key] = {}
    }
    for (let i in data) {
      if (typeof data[i] === 'object') {
        loopList.push({
          parent: anoPar,
          key: i,
          data: data[i]
        })
      } else {
        anoPar[i] = data[i]
      }
    }
  }
  return result
}
let cloneA1 = deepClone5(a1)
cloneA1.c.c2.c22 = 5555555
console.log(a1);
console.log(cloneA1);
// ------------------------------------------JSON.stringify()实现深拷贝
function cloneJson(o) {
  return JSON.parse(JSON.stringify(o))
}
// let obj = { a: { c: 1 }, b: {} };
// obj.b = obj;
// console.log(JSON.parse(JSON.stringify(obj))) // 报错 // Converting circular structure to JSON
```

> 深拷贝能使用hash递归的方式写出来就可以了
> 不过技多不压身，推荐还是看一看使用while实现深拷贝方法

## 单线程，同步异步

### 为什么JS是单线程的？

**答：**⭐⭐⭐⭐⭐

因为JS里面有可视的Dom，如果是多线程的话，这个线程正在删除DOM节点，另一个线程正在编辑Dom节点，导致浏览器不知道该听谁的

### 如何实现异步编程？

**答：**回调函数

### Generator是怎么样使用的以及各个阶段的变化如何？

**答：**⭐⭐⭐

- 首先生成器是一个函数，用来返回迭代器的
- 调用生成器后不会立即执行，而是通过返回的迭代器来控制这个生成器的一步一步执行的
- 通过调用迭代器的next方法来请求一个一个的值，返回的对象有两个属性，一个是value，也就是值；另一个是`done`，是个布尔类型，done为true说明生成器函数执行完毕，没有可返回的值了，
- `done`为`true`后继续调用迭代器的next方法，返回值的`value`为`undefined`

状态变化：

- 每当执行到`yield`属性的时候，都会返回一个对象
- 这时候生成器处于一个非阻塞的挂起状态
- 调用迭代器的next方法的时候，生成器又从挂起状态改为执行状态，继续上一次的执行位置执行
- 直到遇到下一次`yield`依次循环
- 直到代码没有`yield`了，就会返回一个结果对象`done`为`true`，`value`为`undefined`

### 说说 Promise 的原理？你是如何理解 Promise 的？

**答：**⭐⭐⭐⭐⭐做到会写简易版的promise和all函数就可以

```js
class MyPromise2 {
   constructor(executor) {
    // 规定状态
    this.state = "pending"
    // 保存 `resolve(res)` 的res值
    this.value = undefined
    // 保存 `reject(err)` 的err值
    this.reason = undefined
    // 成功存放的数组
    this.successCB = []
    // 失败存放的数组
    this.failCB = []
    let resolve = (value) => {
      if (this.state === "pending") {
        this.state = "fulfilled"
        this.value = value
        this.successCB.forEach(f => f())
      }
    }
    let reject = (reason) => {
      if (this.state === "pending") {
        this.state = "rejected"
        this.value = value
        this.failCB.forEach(f => f())
      }
    }
    try {
      // 执行
      executor(resolve, reject)
    } catch (error) {
      // 若出错，直接调用reject
      reject(error)
    }
  }
  then(onFulfilled, onRejected) {
    if (this.state === "fulfilled") {
      onFulfilled(this.value)
    }
    if (this.state === "rejected") {
      onRejected(this.value)
    }
    if (this.state === "pending") {
      this.successCB.push(() => { onFulfilled(this.value) })
      this.failCB.push(() => { onRejected(this.reason) })
    }
  }
}
Promise.all = function (promises) {
  let list = []
  let count = 0
  function handle(i, data) {
    list[i] = data
    count++
    if (count == promises.length) {
      resolve(list)
    }
  }
  return Promise((resolve, reject) => {
    for (let i = 0; i < promises.length; i++) {
      promises[i].then(res => {
        handle(i, res)
      }, err => reject(err))
    }
  })
}

```

### 以下代码的执行顺序是什么

**答：**⭐⭐⭐⭐⭐

```
async function async1() {



   console.log('async1 start')



   await async2()



   console.log('async1 end')



  }



  async function async2() {



   console.log('async2')



  }



  async1()



  console.log('script start')



 



//执行到await时，如果返回的不是一个promise对象，await会阻塞下面代码(当前async代码块的代码)，会先执行async外的同步代码(在这之前先看看await中函数的同步代码，先把同步代码执行完)，等待同步代码执行完之后，再回到async内部继续执行



//执行到await时，如果返回的是一个promise对象，await会阻塞下面代码(当前async代码块的代码)，会先执行async外的同步代码(在这之前先看看await中函数的同步代码，先把同步代码执行完)，等待同步代码执行完之后，再回到async内部等promise状态达到fulfill的时候再继续执行下面的代码



//所以结果为



//async1 start



//async2



//script start



//async1 end



 
```

 

### 宏任务和微任务都有哪些

**答：**⭐⭐⭐⭐⭐

- 宏任务：`script`、`setTimeOut`、`setInterval`、`setImmediate`
- 微任务:`promise.then`,`process.nextTick`、`Object.observe`、`MutationObserver`
- **注意：Promise是同步任务**

### 宏任务和微任务都是怎样执行的 

**答： **⭐⭐⭐⭐⭐

- 执行宏任务script，
- 进入script后，所有的同步任务主线程执行
- 所有宏任务放入宏任务执行队列
- 所有微任务放入微任务执行队列
- 先清空微任务队列，
- 再取一个宏任务，执行，再清空微任务队列
- 依次循环

**例题1**

```
setTimeout(function(){



    console.log('1')



});



new Promise(function(resolve){



    console.log('2');



    resolve();



}).then(function(){



    console.log('3')



});



console.log('4');



new Promise(function(resolve){



    console.log('5');



    resolve();



}).then(function(){



    console.log('6')



});



setTimeout(function(){



    console.log('7')



});



function bar(){



    console.log('8')



    foo()



}



function foo(){



    console.log('9')



}



console.log('10')



bar()



 
```

 

**解析**

1. 首先浏览器执行Js代码由上至下顺序，遇到setTimeout，把setTimeout分发到宏任务Event Queue中
2. new Promise属于主线程任务直接执行打印2
3. Promis下的then方法属于微任务，把then分到微任务 Event Queue中
4. console.log(‘4’)属于主线程任务，直接执行打印4
5. 又遇到new Promise也是直接执行打印5，Promise 下到then分发到微任务Event Queue中
6. 又遇到setTimouse也是直接分发到宏任务Event Queue中，等待执行
7. console.log(‘10’)属于主线程任务直接执行
8. 遇到bar()函数调用，执行构造函数内到代码，打印8，在bar函数中调用foo函数，执行foo函数到中代码，打印9
9. 主线程中任务执行完后，就要执行分发到微任务Event Queue中代码，实行先进先出，所以依次打印3，6
10. 微任务Event Queue中代码执行完，就执行宏任务Event Queue中代码，也是先进先出，依次打印1，7。

- 最终结果：2，4，5，10，8，9，3，6，1，7

**例题2**

```
setTimeout(() => {



      console.log('1');



      new Promise(function (resolve, reject) {



        console.log('2');



        setTimeout(() => {



          console.log('3');



        }, 0);



        resolve();



      }).then(function () {



        console.log('4')



      })



    }, 0);



    console.log('5'); //5 7 10 8 1 2 4 6 3



    setTimeout(() => {



      console.log('6');



    }, 0);



    new Promise(function (resolve, reject) {



      console.log('7');



      // reject();



      resolve();



    }).then(function () {



      console.log('8')



    }).catch(function () {



      console.log('9')



    })



    console.log('10');



 
```

 

**运行结果： 5 7 10 8 1 2 4 6 3**

## 变量提升

### 变量和函数怎么进行提升的？

**答：**⭐⭐⭐⭐优先级是怎么样的？

- 对所有函数声明进行提升(除了函数表达式和箭头函数)，引用类型的赋值
  - 开辟堆空间
  - 存储内容
  - 将地址赋给变量
- 对变量进行提升，只声明，不赋值，值为`undefined`

### var let const 有什么区别

**答：**⭐⭐⭐⭐⭐

- var
  - var声明的变量可进行变量提升，let和const不会
  - var可以重复声明
  - var在非函数作用域中定义是挂在到window上的
- let
  - let声明的变量只在局部起作用
  - let防止变量污染
  - 不可在声明
- const
  - 具有let的所有特征
  - 不可被改变
    - 如果使用const声明的是对象的话，是可以修改对象里面的值的

### 箭头函数和普通函数的区别？

箭头函数可以当做构造函数 new 吗？⭐⭐⭐⭐⭐

- 箭头函数是普通函数的简写，但是它不具备很多普通函数的特性
- 第一点，this指向问题，箭头函数的this指向它定义时所在的对象，而不是调用时所在的对象
- 不会进行函数提升
- 没有arguments对象，不能使用arguments，如果要获取参数的话可以使用`rest`运算符
- 没有`yield`属性，不能作为生成器Generator使用
- 不能new
  - 没有自己的this，不能调用call和apply
  - 没有prototype，new关键字内部需要把新对象的`_proto_`指向函数的prototype

### 说说你对代理的理解

- 代理有几种定义方式⭐⭐⭐
  - 字面量定义，对象里面的 get和set
  - 类定义， class 中的`get`和`set`
  - Proxy对象，里面传两个对象，第一个对象是目标对象target，第二个对象是专门放get和set的`handler`对象。Proxy和上面两个的区别在于Proxy专门对对象的属性进行get和set
- 代理的实际应用有
  - Vue的双向绑定 vue2用的是`Object.defineProperty`，vue3用的是`proxy`
  - 校验值
  - 计算属性值(get的时候加以修饰)

### 为什么要使用模块化？

都有哪几种方式可以实现模块化，各有什么特点？⭐⭐⭐

- 为什么要使用模块化
  - 防止命名冲突
  - 更好的分离，按需加载
  - 更好的复用性
  - 更高的维护性

### `exports`和`module.exports`

有什么区别？⭐⭐⭐

- 导出方式不一样
  - `exports.xxx='xxx'`
  - `module.export = {}`
- `exports`是`module.exports`的引用，两个指向的是用一个地址，而require能看到的只有`module.exports`

### JS模块包装格式有哪些？

- commonjs ⭐⭐⭐
  - 同步运行，不适合前端
- `AMD`
  - 异步运行
  - 异步模块定义，主要采用异步的方式加载模块，模块的加载不影响后面代码的执行。所有依赖这个模块的语句都写在一个回调函数中，模块加载完毕，再执行回调函数
- `CMD`
  - 异步运行
  - seajs 规范

### ES6和commonjs的区别

- `commonjs`模块输出的是值的拷贝，而ES6输出的值是值的引用⭐⭐⭐
- `commonjs`是在运行时加载，是一个对象，ES6是在编译时加载，是一个代码块
- `commonjs`的this指向当前模块，ES6的this指向undefined

> 哎呀呀呀，不简单，你竟然都看到这里了，看看进度条，已经达到一半了
> 不过——在这之前，先问问自己，前面的都掌握了吗？？
> 如果你还没有，赶紧滚回去看！
> 如果你掌握前面的了，那么准备迎接下一个boss——计算机网络

 

![在这里插入图片描述](https://img-blog.csdnimg.cn/img_convert/8195c6ef7fdbb8f20da0b8806b3f4793.png)

## 跨域

### 跨域的方式都有哪些？

他们的特点是什么

- JSONP ⭐⭐⭐⭐⭐

  - `JSONP`通过同源策略涉及不到的"漏洞"，也就是像`img`中的`src`，`link`标签的`href`,`script`的`src`都没有被同源策略限制到

  - `JSONP`只能get请求

  - 源码：

    ```js
    function addScriptTag(src) {
       var script = document.createElement("script")
       script.setAttribute('type','text/javascript')
       script.src = src
       document.appendChild(script)
    }
    // 回调函数
    function endFn(res) {
      console.log(res.message);
    }
    // 前后端商量好，后端如果传数据的话，返回`endFn({message:'hello'})`
    
    ```
    
     
  
- document.domain⭐

  - 只能跨一级域名相同的域(www.qq.om和www.id.qq.com , 二者都有qq.com)

  - 使用方法

    - `>`表示输入， `<`表示输出 ，以下是在`www.id.qq.com`网站下执行的操作

    - ```
      > var w = window.open("https://www.qq.com")
       < undefined
       > w.document
       ✖ VM3061:1 Uncaught DOMException: Blocked a frame with origin "https://id.qq.com" from accessing a cross-origin frame.
          at <anonymous>:1:3
       
      > document.domain
       < "id.qq.com"
       > document.domain = 'qq.com'
       < "qq.com"
       > w.document
       < #document
       
      ```
      
       
  
- `location.hash`+`iframe`⭐⭐

  - 因为hash传值只能单向传输，所有可以通过一个中间网页，a若想与b进行通信，可以通过一个与a同源的c作为中间网页，a传给b，b传给c，c再传回a

    - 具体做法：在a中放一个回调函数，方便c回调。放一个`iframe`标签，随后传值

    - ```js
      <iframe id="iframe" src="http://www.domain2.com/b.html" style="display:none;"></iframe>
       <script>
          var iframe = document.getElementById('iframe');
          // 向b.html传hash值
          setTimeout(function() {
              iframe.src = iframe.src + '#user=admin';
          }, 1000);
          // 开放给同域c.html的回调方法
          function onCallback(res) {
              alert('data from c.html ---> ' + res);
          }
       </script>
      ```
      
       
      
    - 在b中监听哈希值改变，一旦改变，把a要接收的值传给
    
    - ```
      <iframe id="iframe" src="http://www.domain1.com/c.html" style="display:none;"></iframe>
      
      
      
      <script>
      
      
      
          var iframe = document.getElementById('iframe');
      
      
      
       
      
      
      
          // 监听a.html传来的hash值，再传给c.html
      
      
      
          window.onhashchange = function () {
      
      
      
              iframe.src = iframe.src + location.hash;
      
      
      
          };
      
      
      
      </script>
      
      
      
       
      ```
    
       
    
    - 在c中监听哈希值改变，一旦改变，调用a中的回调函数
    
    - ```
      <script>
      
      
      
          // 监听b.html传来的hash值
      
      
      
          window.onhashchange = function () {
      
      
      
              // 再通过操作同域a.html的js回调，将结果传回
      
      
      
              window.parent.parent.onCallback('hello: ' + location.hash.replace('#user=', ''));
      
      
      
          };
      
      
      
      </script>
      ```
    
       
  
- `window.name`+`iframe`⭐⭐

  - 利Access用`window.name`不会改变(而且很大)来获取数据，
  - a要获取b的数据，b中把数据转为json格式放到`window.name`中

- `postMessage`⭐

  - a窗口向b窗口发送数据，先把data转为json格式，在发送。提前设置好messge监听
  - b窗口进行`message`监听，监听到了以同样的方式返回数据，
  - a窗口监听到message，在进行一系列操作

- `CORS`⭐⭐⭐⭐⭐

  - 通过自定义请求头来让服务器和浏览器进行沟通
  - 有简单请求和非简单请求
  - 满足以下条件，就是简单请求
    - 请求方法是HEAD、POST、GET
    - 请求头只有`Accept`、`AcceptLanguage`、`ContentType`、`ContentLanguage`、`Last-Event-Id`
  - 简单请求，浏览器自动添加一个Origin字段
    - 同时后端需要设置的请求头
      - Access-Control-Allow-Origin --必须
      - Access-Control-Expose-Headers
        - `XMLHttpRequest`只能拿到六个字段，要想拿到其他的需要在这里指定
    - Access-Control-Allow-Credentials --是否可传cookie
    - 要是想传cookie，前端需要设置`xhr.withCredentials = true`，后端设置Access-Control-Allow-Credentials
  - 非简单请求，浏览器判断是否为简单请求，如果是非简单请求，则 浏览器先发送一个header头为option的请求进行预检
    - 预检请求格式(请求行 的请求方法为OPTIONS(专门用来询问的))
      - Origin
      - Access-Control-Request-Method
      - Access-Control-Request-Header
    - 浏览器检查了Origin、Access-Control-Allow-Method和Access-Control-Request-Header之后确认允许就可以做出回应了
    - 通过预检后，浏览器接下来的每次请求就类似于简单请求了

- nginx代理跨域⭐⭐⭐⭐

  - nginx模拟一个虚拟服务器，因为服务器与服务器之间是不存在跨域的，
  - 发送数据时 ，客户端->nginx->服务端
  - 返回数据时，服务端->nginx->客户端

## 网络原理

### 讲一讲三次握手四次挥手

⭐⭐⭐⭐⭐为什么是三次握手四而不是两次握手？

- 客户端和服务端之间通过三次握手建立连接，四次挥手释放连接
- 三次握手，客户端先向服务端发起一个SYN包，进入SYN_SENT状态，服务端收到SYN后，给客户端返回一个ACK+SYN包，表示已收到SYN，并进入SYN_RECEIVE状态，最后客户端再向服务端发送一个ACK包表示确认，双方进入establish状态。
  - 之所以是三次握手而不是两次，是因为如果只有两次，在服务端收到SYN后，向客户端返回一个ACK确认就进入establish状态，万一这个请求中间遇到网络情况而没有传给客户端，客户端一直是等待状态，后面服务端发送的信息客户端也接受不到了。
- 四次挥手，首先客户端向服务端发送一个FIN包，进入FIN_WAIT1状态，服务端收到后，向客户端发送ACK确认包，进入CLOSE_WAIT状态，然后客户端收到ACK包后进入FIN_WAIT2状态，然后服务端再把自己剩余没传完的数据发送给客户端，发送完毕后在发送一个FIN+ACK包，进入LAST_ACK(最后确认)状态，客户端收到FIN+ACK包后，再向服务端发送ACK包，在等待两个周期后在关闭连接
  - 之所以等待两个周期是因为最后服务端发送的ACK包可能会丢失，如果不等待2个周期的话，服务端在没收收到ACK包之前，会不停的重复发送FIN包而不关闭，所以得等待两个周期

### HTTP的结构

- 请求行 请求头 空行 请求体⭐⭐⭐⭐
  - 请求行包括 http版本号，url，请求方式
  - 响应行包括版本号，状态码，原因

### HTTP头都有哪些字段

- 请求头⭐⭐⭐⭐
  - cache-control 是否使用缓存
  - Connection：keep-alive 与服务器的连接状态
  - Host 主机域
- 返回头
  - cache-control
  - etag 唯一标识，缓存用的
  - last-modified最后修改时间

### 说说你知道的状态码

- 2开头的表示成功⭐⭐⭐⭐⭐
  - 一般见到的就是200
- 3开头的表示重定向
  - 301永久重定向
  - 302临时重定向
  - 304表示可以在缓存中取数据(协商缓存)
- 4开头表示客户端错误
  - 403跨域
  - 404请求资源不存在
- 5开头表示服务端错误
  - 500

### 网络OSI七层模型都有哪些？

TCP是哪一层的**⭐⭐⭐⭐

- 七层模型
  - 应用层
  - 表示层
  - 会话层
  - 传输层
  - 网络层
  - 数据链路层
  - 物理层
- TCP属于传输层

### http1.0和http1.1，http2

还有http2有什么区别？⭐⭐⭐⭐

- http0.9只能进行get请求
- http1.0添加了POST，HEAD，OPTION，PUT，DELETE等
- http1.1增加了长连接keep-alive，增加了host域，而且节约带宽
- http2 多路复用，头部压缩，服务器推送

### https和http有什么区别

⭐⭐⭐⭐⭐https的实现原理？ 

- http无状态无连接，而且是明文传输，不安全
- https传输内容加密，身份验证，保证数据完整性
- https实现原理⭐⭐⭐⭐⭐
  - 首先客户端向服务端发起一个随机值，以及一个加密算法
  - 服务端收到后返回一个协商好的加密算法，以及另一个随机值
  - 服务端在发送一个公钥CA
  - 客户端收到以后先验证CA是否有效，如果无效则报错弹窗，有过有效则进行下一步操作
  - 客户端使用之前的两个随机值和一个预主密钥组成一个会话密钥，在通过服务端传来的公钥加密把会话密钥发送给服务端
  - 服务端收到后使用私钥解密，得到两个随机值和预主密钥，然后组装成会话密钥
  - 客户端在向服务端发起一条信息，这条信息使用会话秘钥加密，用来验证服务端时候能收到加密的信息
  - 服务端收到信息后返回一个会话秘钥加密的信息
  - 都收到以后SSL层连接建立成功

### localStorage、SessionStorage、cookie、session 之间有什么区别 

- localStorage⭐⭐⭐⭐⭐
  - 生命周期：关闭浏览器后数据依然保留，除非手动清除，否则一直在
  - 作用域：相同浏览器的不同标签在同源情况下可以共享localStorage
- sessionStorage
  - 生命周期：关闭浏览器或者标签后即失效
  - 作用域：只在当前标签可用，当前标签的iframe中且同源可以共享
- cookie
  - 是保存在客户端的，一般由后端设置值，可以设置过期时间
  - 储存大小只有4K
  - 一般用来保存用户的信息的
  - 在http下cookie是明文传输的,较不安全
  - cookie属性有
    - http-only:不能被客户端更改访问，防止XSS攻击(保证cookie安全性的操作)
    - Secure：只允许在https下传输
    - Max-age: cookie生成后失效的秒数
    - expire: cookie的最长有效时间，若不设置则cookie生命期与会话期相同
- session
  - session是保存在服务端的
  - session的运行依赖sessionId，而sessionId又保存在cookie中，所以如果禁用的cookie，session也是不能用的，不过硬要用也可以，可以把sessionId保存在URL中
  - session一般用来跟踪用户的状态
  - session 的安全性更高，保存在服务端，不过一般为使服务端性能更加，会考虑部分信息保存在cookie中

**localstorage存满了怎么办？**⭐⭐⭐

- 划分域名，各域名下的存储空间由各业务组统一规划使用
- 跨页面传数据：考虑单页应用、采用url传输数据
- 最后兜底方案：情调别人的存储

**怎么使用cookie保存用户信息**⭐⭐⭐

- document.cookie(“名字 = 数据;expire=时间”)

**怎么删除cookie**⭐⭐⭐

- 目前没有提供删除的操作，但是可以把它的Max-age设置为0，也就是立马失效，也就是删除了

### Get和Post的区别

https://www.zhihu.com/question/28586791 ⭐⭐⭐⭐⭐

- 冪等/不冪等(可缓存/不可缓存)
  - get请求是冪等的，所以get请求的数据是可以缓存的
  - 而post请求是不冪等的，查询查询对数据是有副作用的，是不可缓存的
- 传参
  - get传参，参数是在url中的
    - 准确的说get传参也可以放到body中，只不过不推荐使用
  - post传参，参数是在请求体中
    - 准确的说post传参也可以放到url中，只不过不推荐使用
- 安全性
  - get较不安全
  - post较为安全
  - 准确的说两者都不安全，都是明文传输的，在路过公网的时候都会被访问到，不管是url还是header还是body，都会被访问到，要想做到安全，就需要使用https
- 参数长度
  - get参数长度有限，是较小的
    - 准确来说，get在url传参的时候是很小的
  - post传参长度不受限制
- 发送数据
  - post传参发送两个请求包，一个是请求头，一个是请求体，请求头发送后服务器进行验证，要是验证通过的话就会给客户端发送一个100-continue的状态码，然后就会发送请求体
- 字符编码
  - get在url上传输的时候只允许ASCII编码

### 讲讲http缓存

https://www.jianshu.com/p/9c95db596df5 ⭐⭐⭐⭐⭐

- 缓存分为强缓存和协商缓存

- 强缓存

  - 在浏览器加载资源时，先看看`cache-control`里的`max-age`，判断数据有没有过期，如果没有直接使用该缓存 ，有些用户可能会在没有过期的时候就点了刷新按钮，这个时候浏览器就回去请求服务端，要想避免这样做，可以在`cache-control`里面加一个`immutable`.
  - public
    - 允许客户端和虚拟服务器缓存该资源，cache-control中的一个属性
  - private
    - 只允许客户端缓存该资源
  - no-storage
    - 不允许强缓存，可以协商缓存
  - no-cache
    - 不允许缓存

- 协商缓存

  - 浏览器加载资源时，没有命中强缓存，这时候就去请求服务器，去请求服务器的时候，会带着两个参数，一个是`If-None-Match`，也就是响应头中的`etag`属性，每个文件对应一个`etag`;另一个参数是`If-Modified-Since`,也就是响应头中的`Last-Modified`属性，带着这两个参数去检验缓存是否真的过期，如果没有过期，则服务器会给浏览器返回一个304状态码，表示缓存没有过期，可以使用旧缓存。

  - ```
    etag
    ```

    的作用

    - 有时候编辑了文件，但是没有修改，但是`last-modified`属性的时间就会改变，导致服务器会重新发送资源，但是`etag`的出现就完美的避免了这个问题，他是文件的唯一标识

缓存位置：

- 内存缓存Memory-Cache
- 离线缓存Service-Worker
- 磁盘缓存Disk-Cache
- 推送缓存Push-Cache

### `tcp` 和`udp`有什么区别**⭐⭐⭐⭐⭐

- 连接方面
  - tcp面向连接，udp不需要连接
    - tcp需要三次握手四次挥手请求连接
- 可靠性
  - tcp是可靠传输；一旦传输过程中丢包的话会进行重传
  - udp是不可靠传输，但会最大努力交付
- 工作效率
  - UDP实时性高，比TCP工作效率高
    - 因为不需要建立连接，更不需要复杂的握手挥手以及复杂的算法，也没有重传机制
- 是否支持多对多
  - TCP是点对点的
  - UDP支持一对一，一对多，多对多
- 首部大小
  - tcp首部占20字节
  - udp首部占8字节

### 从浏览器输入url后都经历了什么**⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐具重要！

- 先进行DNS域名解析，先查看本地hosts文件，查看有没有当前域名对应的ip地址，若有直接发起请求，没有的话会在本地域名服务器去查找，该查找属于递归查找，如果本地域名服务器没查找到，会从根域名服务器查找，该过程属于迭代查找，根域名会告诉你从哪个与服务器查找，最后查找到对应的ip地址后把对应规则保存到本地的hosts文件中。

- 如果想加速以上及之后的http请求过程的话可以使用缓存服务器CDN，CDN过程如下：

  - 用户输入url地址后，本地DNS会解析url地址，不过会把最终解析权交给CNAME指向的CDN的DNS服务器
  - CDN的DNS服务器会返回给浏览器一个全局负载均衡IP
  - 用户会根据全局负载均衡IP去请求全局负载均衡服务器
  - 全局负载均衡服务器会根据用户的IP地址，url地址，会告诉用户一个区域负载均衡设备，让用户去请求它。
  - 区域负载均衡服务器会为用户选择一个离用户较近的最优的缓存服务器，并把ip地址给到用户
  - 用户想缓存服务器发送请求，如果请求不到想要的资源的话，会一层层向上一级查找，知道查找到为止。

- 进行http请求，三次握手四次挥手建立断开连接

- 服务器处理，可能返回304也可能返回200

  - 返回304说明客户端缓存可用，直接使用客户端缓存即可，该过程属于协商缓存
  - 返回200的话会同时返回对应的数据

- 客户端自上而下执行代码

  - 其中遇到CSS加载的时候，CSS不会阻塞DOM树的解析，但是会阻塞DOM树的渲染，并且CSS会阻塞下面的JS的执行

  - 然后是JS加载，JS加载会影响DOM的解析，之所以会影响，是因为JS可能会删除添加节点，如果先解析后加载的话，DOM树还得重新解析，性能比较差。如果不想阻塞DOM树的解析的话，可以给script添加一个

    ```
    defer
    ```

    或者

    ```
    async
    ```

    的标签。

    - defer：不会阻塞DOM解析，等DOM解析完之后在运行，在`DOMContentloaed`之前
    - async: 不会阻塞DOM解析，等该资源下载完成之后立刻运行

  - 进行DOM渲染和Render树渲染

    - 获取html并解析为Dom树
    - 解析css并形成一个cssom(css树)
    - 将cssom和dom合并成渲染树(render树)
    - 进行布局(layout)
    - 进行绘制(painting)
    - 回流重绘
      - 回流必将引起重绘，重绘不一定引起回流

**滑动窗口和拥塞窗口有什么区别**⭐⭐⭐

> [解析TCP之滑动窗口(动画演示)](https://blog.csdn.net/yao5hed/article/details/81046945)
> 以动画的形式解释滑动窗口，

- 滑动窗口
  - 发送窗口永远小于或等于接收窗口，发送窗口的大小取决于接收窗口的大小
  - 控制流量来保证TCP的可靠传输(不控制流量的话可能会溢出)
  - 发送方的数据分为
    - 1已发送，接收到ACK的
    - 2已发送，未接收到ACK的
    - 3未发送，但允许发送的
    - 4未发送，但不允许发送的
    - 2和3表示发送窗口
  - 接收方
    - 1.已接收
    - 2.未接受但准备接受
    - 3.未接受不准备接受
- 拥塞窗口
  - **防止过多的数据注入到网络中，这样可以使网络中的路由器或链路不致过载。**
  - 是一个全局性的过程
  - 方法
    - 慢开始、拥塞避免、快重传、快恢复

**什么是CDN？**⭐⭐⭐⭐

> [关于 cdn、回源等问题一网打尽](https://juejin.cn/post/6844903604596244493)

1.首先访问本地的 DNS ，如果没有命中，继续递归或者迭代查找，直到命中拿到对应的 IP 地址。

2.拿到对应的 IP 地址之后服务器端发送请求到目的地址。注意这里返回的不直接是 cdn 服务器的 IP 地址，而是全局负载均衡系统的 IP 地址

4.全局负载均衡系统会根据客户端的 IP地址和请求的 url 和相应的区域负载均衡系统通信

5.区域负载均衡系统拿着这两个东西获取距离客户端最近且有相应资源的cdn 缓存服务器的地址，返回给全局负载均衡系统

6.全局负载均衡系统返回确定的 cdn 缓存服务器的地址给客户端。

7.客户端请求缓存服务器上的文件

**什么是xss？什么是csrf？**⭐⭐⭐⭐⭐

- xss脚本注入
  - 不需要你做任何的登录认证，它会通过合法的操作(比如在url中输入、在评论框中输入)，向你的页面注入脚本(可能是js、hmtl代码块等)。
  - 防御
    - 编码：对用户输入的数据进行HTML Entity 编码。把字符转换成 转义字符。Encode的作用是将$var等一些字符进行转化，使得浏览器在最终输出结果上是一样的。
    - 过滤：移除用户输入的和事件相关的属性。
- csrf跨域请求伪造
  - 在未退出A网站的前提下访问B，B使用A的cookie去访问服务器
  - 防御：token，每次用户提交表单时需要带上token(伪造者访问不到)，如果token不合法，则服务器拒绝请求

**OWASP top10 (10项最严重的Web应用程序安全风险列表)都有哪些?**⭐⭐⭐

- SQL注入
  - 在输入框里输入sql命令
- 失效的身份验证
  - 拿到别人的cookie来向服务端发起请求，就可以做到登陆的目的
- 敏感数据泄露
  - 明文传输状态下可能被抓包拦截，这时候就造成数据泄露
    - 想做到抓包，比如在网吧，共享一个猫上网，这时候抓包就可行，方法网上一搜一大把
  - 不过此风险大部分网站都能得到很好的解决，https或者md5加密都可以
- XML 外部实体
- 失效的访问控制
- 安全配置错误
- XSS
- 不安全的反序列化
- 使用含有已知漏洞的组件
- 不足的日志记录和监控

> 怎么样？计算机网络是不是没有想象中的那么难，如果你没看过瘾的话，推荐你这篇文章：[【长文】前端需要了解的计算机网络知识](https://blog.csdn.net/qq_29438877/article/details/105132220)
> 是不是得感激我一下【手动滑稽】

 

![谢谢](https://img-blog.csdnimg.cn/img_convert/9c674565839bde5572566d01538bd111.png)

### 什么是回流 什么是重绘？**⭐⭐⭐⭐⭐

- 回流
  - render树中一部分或全部元素需要改变尺寸、布局、或着需要隐藏而需要重新构建，这个过程叫做回流
  - 回流必将引起重绘
- 重绘
  - render树中一部分元素改变，而不影响布局的，只影响外观的，比如颜色。该过程叫做重绘
- 页面至少经历一次回流和重绘(第一次加载的时候)

## 杂项

**事件冒泡和事件捕捉有什么区别**⭐⭐⭐⭐⭐

- 事件冒泡
  - 在addEventListener中的第三属性设置为false(默认)
  - 从下至上(儿子至祖宗)执行
- 事件捕捉
  - 在addEventListener中的第三属性设置为true
  - 从上至下(祖宗到儿子)执行

**什么是防抖？什么是节流？手写一个**⭐⭐⭐⭐⭐

- 防抖
  - n秒后在执行该事件，若在n秒内被重复触发，则重新计时
- 节流
  - n秒内只运行一次，若在n秒内重复触发，只有一次生效

```
// ---------------------------------------------------------防抖函数



    function debounce(func, delay) {



      let timeout



      return function () {



        let arg = arguments



        if (timeout) clearTimeout(timeout)



        timeout = setTimeout(() => {



          func(arg)



        }, delay);



      }



    }



 



    // ---------------------------------------------------------立即执行防抖函数



    function debounce2(fn, delay) {



      let timer



 



      return function () {



        let args = arguments



        if (timer) clearTimeout(timer)



 



 



        let callNow = !timer



        timer = setTimeout(() => {



          timer = null



        }, delay);



        if (callNow) { fn(args) }



      }



    }



    // ---------------------------------------------------------立即执行防抖函数+普通防抖



    function debounce3(fn, delay, immediate) {



      let timer



 



      return function () {



        let args = arguments



        let _this = this



        if (timer) clearTimeout(timer)



 



        if (immediate) {



          let callNow = !timer



          timer = setTimeout(() => {



            timer = null



          }, delay);



 



          if (callNow) { fn.apply(_this, args) }



        } else {



          timeout = setTimeout(() => {



            func.apply(_this, arguments)



          }, delay);



        }



      }



    }



 



    // ---------------------------------------------------------节流 ，时间戳版



 



    function throttle(fn, wait) {



 



      let previous = 0



      return function () {



        let now = Date.now()



        let _this = this



        let args = arguments



        if (now - previous > wait) {



          fn.apply(_this, arguments)



          previous = now



        }



      }



    }



 



    // ---------------------------------------------------------节流 ，定时器版



    function throttle2(fn, wait) {



      let timer



      return function () {



        let _this = this



        let args = arguments



        if (!timer) {



          timer = setTimeout(() => {



            timer = null



            fn.apply(_this, arguments)



          }, wait);



        }



      }



    }
```

 

 

**函数柯里化原理**⭐⭐⭐⭐⭐

```
function add() {



      var args = Array.prototype.slice.call(arguments)



 



      var adder = function () {



        args.push(...arguments)



        return adder



      }



 



      adder.toString = function () {



        return args.reduce((prev, curr) => {



          return prev + curr



        }, 0)



      }



 



      return adder



    }



 



    let a = add(1, 2, 3)



    let b = add(1)(2)(3)



    console.log(a)



    console.log(b)



    console.log(add(1, 2)(3));



    console.log(Function.toString)
```

 

**什么是requestAnimationFrame？**⭐⭐⭐⭐

- requestAnimationFrame请求数据帧可以用做动画执行

- 可以自己决定什么时机调用该回调函数

- 能保证每次频幕刷新的时候只被执行一次

- 页面被隐藏或者最小化的时候暂停执行，返回窗口继续执行，有效节省CPU

- ```
  var s = 0
  
  
  
      function f() {
  
  
  
        s++
  
  
  
        console.log(s);
  
  
  
        if (s < 999) {
  
  
  
          window.requestAnimationFrame(f)
  
  
  
        }
  
  
  
      }
  
  
  
      window.requestAnimationFrame(f)
  ```

   

**js常见的设计模式**⭐⭐⭐⭐⭐

- 单例模式、工厂模式、构造函数模式、发布订阅者模式、迭代器模式、代理模式

- 单例模式

  - 不管创建多少个对象都只有一个实例

  - ```
    var Single = (function () {
    
    
    
          var instance = null
    
    
    
          function Single(name) {
    
    
    
            this.name = name
    
    
    
          }
    
    
    
          return function (name) {
    
    
    
            if (!instance) {
    
    
    
              instance = new Single()
    
    
    
            }
    
    
    
            return instance
    
    
    
          }
    
    
    
        })()
    
    
    
     
    
    
    
        var oA = new Single('hi')
    
    
    
        var oB = new Single('hello')
    
    
    
        console.log(oA);
    
    
    
        console.log(oB);
    
    
    
        console.log(oB === oA);
    ```

     

- 工厂模式

  - 代替new创建一个对象，且这个对象想工厂制作一样，批量制作属性相同的实例对象(指向不同)

  - ```
    function Animal(o) {
    
    
    
          var instance = new Object()
    
    
    
          instance.name = o.name
    
    
    
          instance.age = o.age
    
    
    
          instance.getAnimal = function () {
    
    
    
            return "name:" + instance.name + " age:" + instance.age
    
    
    
          }
    
    
    
          return instance
    
    
    
        }
    
    
    
     
    
    
    
        var cat = Animal({name:"cat", age:3})
    
    
    
        console.log(cat);
    ```

     

- 构造函数模式

- 发布订阅者模式

- ```
  class Watcher {
  
  
  
        // name模拟使用属性的地方
  
  
  
        constructor(name, cb) {
  
  
  
          this.name = name
  
  
  
          this.cb = cb
  
  
  
        }
  
  
  
        update() {//更新
  
  
  
          console.log(this.name + "更新了");
  
  
  
          this.cb() //做出更新回调
  
  
  
        }
  
  
  
      }
  
  
  
   
  
  
  
      class Dep {//依赖收集器
  
  
  
        constructor() {
  
  
  
          this.subs = []
  
  
  
        }
  
  
  
        addSubs(watcher) {
  
  
  
          this.subs.push(watcher)
  
  
  
        }
  
  
  
        notify() {//通知每一个观察者做出更新
  
  
  
          this.subs.forEach(w => {
  
  
  
            w.update()
  
  
  
          });
  
  
  
        }
  
  
  
      }
  
  
  
   
  
  
  
      // 假如现在用到age的有三个地方
  
  
  
      var w1 = new Watcher("我{{age}}了", () => { console.log("更新age"); })
  
  
  
      var w2 = new Watcher("v-model:age", () => { console.log("更新age"); })
  
  
  
      var w3 = new Watcher("I am {{age}} years old", () => { console.log("更新age"); })
  
  
  
   
  
  
  
      var dep = new Dep()
  
  
  
      dep.addSubs(w1)
  
  
  
      dep.addSubs(w2)
  
  
  
      dep.addSubs(w3)
  
  
  
   
  
  
  
   
  
  
  
      // 在Object.defineProperty 中的 set中运行
  
  
  
      dep.notify()
  ```

   

- 代理模式

- 迭代器模式

**JS性能优化的方式**⭐⭐⭐⭐⭐

- 垃圾回收
- 闭包中的对象清楚
- 防抖节流
- 分批加载(setInterval,加载10000个节点)
- 事件委托
- 少用with
- requestAnimationFrame的使用
- script标签中的defer和async
- CDN

# Vue

## Vue双向绑定

**数据劫持：** vue.js是采用数据劫持结合发布者-订阅者模式的方式，通过`Object.defineProperty()`来劫持各个属性的`setter`,`getter`,在数据变动时发布消息给订阅者，触发相应的监听回调

**阐述一下你所理解的MVVM响应式原理**⭐⭐⭐⭐⭐

vue是采用数据劫持配合发布者-订阅者的模式的方式，通过`Object.defineProperty()`来劫持各个属性的getter和setter，在数据变动时，发布消息给依赖收集器(dep中的subs)，去通知(notify)观察者，做出对应的回调函数，去更新视图

MVVM作为绑定的入口，整合Observer,Compile和Watcher三者，通过Observer来监听model数据变化，通过Compile来解析编译模板指令，最终利用Watcher搭起Observer，Compile之间的通信桥路，达到数据变化=>视图更新；视图交互变化=>数据model变更的双向绑定效果。

**杂乱笔记**

- data中每一个数据都绑定一个Dep，这个Dep中都存有所有用到该数据的观察者

- 当数据改变时，发布消息给dep(依赖收集器)，去通知每一个观察者。做出对应的回调函数

- ```
  const dep = new Dep()
  
  
  
      // 劫持并监听所有属性
  
  
  
      Object.defineProperty(obj, key, {
  
  
  
        enumerable: true,
  
  
  
        configurable: false,
  
  
  
        get() {
  
  
  
          // 订阅数据变化时，在Dep中添加观察者
  
  
  
          Dep.target && dep.addSub(Dep.target)
  
  
  
          return value
  
  
  
        },
  
  
  
        set: (newVal) => {
  
  
  
          if (newVal !== value) {
  
  
  
            this.observe(newVal)
  
  
  
            value = newVal
  
  
  
          }
  
  
  
          // 告诉Dep通知变化
  
  
  
          dep.notify()
  
  
  
   
  
  
  
        },
  
  
  
      })
  ```

   

### 说说vue的生命周期**⭐⭐⭐⭐⭐

- ```
  beforeCreate
  ```

  - 创建之前，此时还没有data和Method

- ```
  Created
  ```

  - 创建完成，此时data和Method可以使用了
  - 在Created之后beforeMount之前如果没有el选项的话那么此时生命周期结束，停止编译，如果有则继续

- ```
  beforeMount
  ```

  - 在渲染之前

- ```
  mounted
  ```

  - 页面已经渲染完成，并且`vm`实例中已经添加完`$el`了，已经替换掉那些DOM元素了(双括号中的变量)，这个时候可以操作DOM了(但是是获取不了元素的高度等属性的，如果想要获取，需要使用`nextTick()`)

- ```
  beforeUpdate
  ```

  - `data`改变后，对应的组件重新渲染之前

- ```
  updated
  ```

  - `data`改变后，对应的组件重新渲染完成

- ```
  beforeDestory
  ```

  - 在实例销毁之前，此时实例仍然可以使用

- ```
  destoryed
  ```

  - 实例销毁后

### vue中父子组件的生命周期**⭐⭐⭐⭐⭐

- 父子组件的生命周期是一个嵌套的过程
- 渲染的过程
  - 父`beforeCreate`->父`created`->父`beforeMount`->子`beforeCreate`->子`created`->子`beforeMount`->子`mounted`->父`mounted`
- 子组件更新过程
  - 父`beforeUpdate`->子`beforeUpdate`->子`updated`->父`updated`
- 父组件更新过程
  - 父`beforeUpdate`->父`updated`
- 销毁过程
  - 父`beforeDestroy`->子`beforeDestroy`->子`destroyed`->父`destroyed`

**Vue中的`nextTick`**⭐⭐⭐⭐⭐

- nextTick
  - 解释
    - `nextTick`：在下次 DOM 更新循环结束之后执行延迟回调。在修改数据之后立即使用这个方法，获取更新后的 DOM。
  - 应用
    - 想要在Vue生命周期函数中的`created()`操作DOM可以使用`Vue.nextTick()`回调函数
    - 在数据改变后要执行的操作，而这个操作需要等数据改变后而改变DOM结构的时候才进行操作，需要用到`nextTick`

### computed和watch的区别**⭐⭐⭐⭐⭐

- computed

  - 计算属性，依赖其他属性，当其他属性改变的时候下一次获取computed值时也会改变，`computed`的值会有缓存

- ```
  watch
  ```

  - 类似于数据改变后的回调
  - 如果想深度监听的话，后面加一个`deep:true`
  - 如果想监听完立马运行的话，后面加一个`immediate:true`

### Vue优化方式**⭐⭐⭐⭐⭐

- v-if 和v-show
- 使用`Object.freeze()`方式冻结data中的属性，从而阻止数据劫持
- 组件销毁的时候会断开所有与实例联系，但是除了`addEventListener`，所以当一个组件销毁的时候需要手动去`removeEventListener`
- 图片懒加载
- 路由懒加载
- 为减少重新渲染和创建dom节点的时间，采用虚拟dom

### Vue-router的模式**⭐⭐⭐⭐⭐

- hash模式
  - 利用onhashchange事件实现前端路由，利用url中的hash来模拟一个hash，以保证url改变时，页面不会重新加载。
- history模式
  - 利用pushstate和replacestate来将url替换但不刷新，但是有一个致命点就是，一旦刷新的话，就会可能404，因为没有当前的真正路径，要想解决这一问题需要后端配合，将不存在的路径重定向到入口文件。

### MVC与MVVM有什么区别**⭐⭐⭐⭐⭐

> 哎呀呀，这个要参考的就多了。[mvc和mvvm的区别](https://www.jianshu.com/p/b0aab1ffad93)[基于Vue实现一个简易MVVM](https://juejin.cn/post/6844904099704471559)[不好意思！耽误你的十分钟，让MVVM原理还给你](https://juejin.cn/post/6844903586103558158)

- MVC

  - Model(模型)是应用程序中用于处理应用程序**数据逻辑的部分**。通常模型对象负责在数据库中**存取数据**。

  - View(视图)是应用程序中处理**数据显示的部分**。通常视图是依据模型数据创建的。

  - Controller(控制器)是应用程序中

    处理用户交互的部分

    。

    - 通常控制器负责**从视图读取数据，控制用户输入，并向模型发送数据**。

**diff算法**⭐⭐⭐⭐⭐

- diff算法是指对新旧虚拟节点进行对比，并返回一个patch对象，用来存储两个节点不同的地方，最后利用patch记录的消息局部更新DOM

**虚拟DOM的优缺点**⭐⭐⭐⭐⭐

- 缺点
  - 首次渲染大量DOM时，由于多了一层虚拟DOM的计算，会比innerHTML插入慢
- 优点
  - 减少了dom操作，减少了回流与重绘
  - 保证性能的下限，虽说性能不是最佳，但是它具备局部更新的能力，所以大部分时候还是比正常的DOM性能高很多的

**Vue的Key的作用** ⭐⭐⭐⭐

- key
  - key主要用在虚拟Dom算法中，每个虚拟节点VNode有一个唯一标识Key，通过对比新旧节点的key来判断节点是否改变，用key就可以大大提高渲染效率，这个key类似于缓存中的etag。

**Vue组件之间的通信方式**⭐⭐⭐⭐⭐

- 子组件设置props + 父组件设置`v-bind:`/`:`

  - 父传子

- 子组件的$emit + 父组件设置`v-on`/`@`

  - 子传父

- 任意组件通信，新建一个空的全局Vue对象，利用 e m i t 发 送 ， emit发送， emit发送，on接收

  - 传说中的$bus

  - 任意组件

  - ```
    Vue.prototype.Event=new Vue();
    
    
    
    	
    
    
    
     
    
    
    
        Event.$emit(事件名,数据);
    
    
    
        Event.$on(事件名,data => {});
    ```

     

- Vuex

  - 里面的属性有：
    - state
      - 存储数据的
      - 获取数据最好推荐使用getters
      - 硬要使用的话可以用MapState， 先引用，放在compute中`...mapState(['方法名','方法名'])`
    - getters
      - 获取数据的
      - this.$store.getters.xxx
      - 也可使用mapGetters 先引用，放在compute中，`...mapGetters(['方法名','方法名'])`
    - mutations
      - 同步操作数据的
      - this.$store.commit(“方法名”,数据)
      - 也可使用mapMutations ，使用方法和以上一样
    - actions
      - 异步操作数据的
      - this.$store.dispatch(“方法名”,数据)
      - 也可使用mapActions ，使用方法和以上一样
    - modules
      - 板块，里面可以放多个vuex

- 父组件通过`v-bind:`/`:`传值，子组件通过`this.$attrs`获取

  - 父传子
  - 当子组件没有设置props的时候可以使用
  - `this.$attrs`获取到的是一个对象(所有父组件传过来的集合)

- 祖先组件使用provide提供数据，子孙组件通过inject注入数据

- p a r e n t / parent/ parent/children

- refs—$ref

- 还有一个，这个网上没有，我自己认为的，我觉得挺对的，slot-scope，本身父组件使用slot插槽是无法获取子组件的数据的，但是使用了slot-scope就可以获取到子组件的数据(拥有了子组件的作用域)

**Vue-router有哪几种钩子函数**⭐⭐⭐⭐⭐

- beforeEach
  - 参数有
    - to(Route路由对象)
    - from(Route路由对象)
    - next(function函数) 一定要调用才能进行下一步
- afterEach
- beforeRouterLeave

# Webpack

**webpack常用的几个对象及解释**⭐⭐⭐⭐

- entry 入口文件

- output 输出文件

  - 一般配合node的path模块使用

    ```
    // 入口文件
    
    
    
        entry:"./src/index.js",
    
    
    
        output:{
    
    
    
            // 输出文件名称
    
    
    
            filename:"bundle.js",
    
    
    
            // 输出的路径(绝对路径)
    
    
    
            path:path.resolve(__dirname,"dist") //利用node模块的path 绝对路径
    
    
    
        },
    
    
    
        // 设置模式
    
    
    
        mode:"development"
    ```

     

- mode 设计模式

- module(loader)

  - 里面有一个rules数组对某种格式的文件进行转换处理(转换规则)

  - use数组解析顺序是从下到上逆序执行的

  - ```
    module:{
    
    
    
           // 对某种格式的文件进行转换处理(转换规则)
    
    
    
           rules:[
    
    
    
               {
    
    
    
                   // 用到正则表达式
    
    
    
                   test:/\.css$/,      //后缀名为css格式的文件
    
    
    
                   use:[
    
    
    
                       // use数组解析顺序是从下到上逆序执行的
    
    
    
                       // 先用css-loader 再用style-loader
    
    
    
                       // 将js的样式内容插入到style标签里
    
    
    
                       "style-loader",
    
    
    
                       // 将css文件转换为js
    
    
    
                       "css-loader"
    
    
    
                   ]
    
    
    
               }
    
    
    
           ]
    
    
    
       }
    
    
    
     
    
    
    
    // -----------------------------------------------------vue的
    
    
    
    module.exports={
    
    
    
        module:{
    
    
    
            rules:[
    
    
    
                {
    
    
    
                    test: /\.vue$/,
    
    
    
                    use:["vue-loader"]
    
    
    
                }
    
    
    
            ]
    
    
    
        }
    
    
    
    }
    ```

     

- plugin

  - 插件配置

  - ```
    const uglifyJsPlugin = reqiure('uglifyjs-webpack-plugin')
    
    
    
     
    
    
    
    module.exports={
    
    
    
    	plugin:[
    
    
    
    		new uglifyJsPlugin()	//丑化
    
    
    
    	]
    
    
    
    }
    ```

     

- devServer

  - 热更新

  - ```
    devServer:{
    
    
    
            // 项目构建路径
    
    
    
            contentBase:path.resolve(__dirname,"dist"),
    
    
    
            // 启动gzip亚索
    
    
    
            compress:true,
    
    
    
            // 设置端口号
    
    
    
            port:2020,
    
    
    
            // 自动打开浏览器:否
    
    
    
            open:false,
    
    
    
            //页面实时刷新(实时监听)
    
    
    
            inline:true
    
    
    
        }
    ```

     

- resolve

  - 配置路径规则

  - alias 别名

  - ```
    module.exports= {
    
    
    
    	resolve:{
    
    
    
    		//如果导入的时候不想写后缀名可以在resolve中定义extensions
    
    
    
    		extensions:['.js','.css','.vue']
    
    
    
    		//alias:别名
    
    
    
    		alias:{
    
    
    
    			//导入以vue结尾的文件时，会去寻找vue.esm.js文件
    
    
    
    			'vue$':"vue/dist/vue.esm.js"
    
    
    
    		}
    
    
    
    	}
    
    
    
    }
    ```

     

- babel(ES6转ES5)

  - 下载插件`babel-loader`,在module(loader)中配置

**loader和plugin的区别是什么？**⭐⭐⭐

- loader
  - loader是用来解析非js文件的，因为Webpack原生只能解析js文件，如果想把那些文件一并打包的话，就需要用到loader，loader使webpack具有了解析非js文件的能力
- plugin
  - 用来给webpack扩展功能的，可以加载许多插件

# CSS/HTML

**flex布局**⭐⭐⭐⭐⭐

> 这个我就不例举了，看看阮一峰老师的文章叭！[Flex 布局教程](http://www.ruanyifeng.com/blog/2015/07/flex-grammar.html)

**grid布局**⭐⭐⭐⭐

> 同样是阮一峰老师的，[CSS Grid 网格布局教程](http://www.ruanyifeng.com/blog/2019/03/grid-layout-tutorial.html)

**常见的行内元素和块级元素都有哪些？**⭐⭐⭐⭐⭐

- 行内元素 inline
  - 不能设置宽高，不能自动换行
  - span、input、img、textarea、label、select
- 块级元素block
  - 可以设置宽高，会自动换行
  - p、h1/h2/h3/h4/h5、div、ul、li、table
- inline-block
  - 可以设置宽高，会自动换行

**请说明px,em,rem,vw,vh,rpx等单位的特性**⭐⭐⭐⭐⭐

- px
  - 像素
- em
  - 当前元素的字体大小
- rem
  - 根元素字体大小
- vw
  - 100vw是总宽度
- vh
  - 100vh是总高度
- rpx
  - 750rpx是总宽度

**常见的替换元素和非替换元素？**⭐⭐

- 替换元素
  - 是指若标签的属性可以改变标签的显示方式就是替换元素，比如input的type属性不同会有不同的展现，img的src等
  - img、input、iframe
- 非替换元素
  - div、span、p

**first-of-type和first-child有什么区别**⭐⭐⭐⭐

- first-of-type
  - 匹配的是从第一个子元素开始数，匹配到的那个的第一个元素
- first-child
  - 必须是第一个子元素

**`doctype`标签和`meta`标签**⭐⭐⭐⭐⭐

- doctype
  - 告诉浏览器以什么样的文档规范解析文档
  - 标准模式和兼容模式
    - 标准模式 ->正常，排版和js运作模式都是以最高标准运行
    - 兼容模式->非正常

**script标签中defer和async都表示了什么**⭐⭐⭐⭐⭐

- 众所周知script会阻塞页面的加载，如果我们要是引用外部js，假如这个外部js请求很久的话就难免出现空白页问题，好在官方为我们提供了defer和async

- defer

- ```
  <script src="d.js" defer></script>
  
  
  
  <script src="e.js" defer></script>
  ```

  - 不会阻止页面解析，并行下载对应的js文件
  - 下载完之后不会执行
  - 等所有其他脚本加载完之后，在`DOMContentLoaded`事件之前执行对应`d.js`、`e.js`

- async

- ```
  <script src="b.js" async></script>
  
  
  
  <script src="c.js" async></script>
  ```

  - 不会阻止DOM解析，并行下载对应的js文件
  - 下载完之后立即执行

- **补充**，`DOMContentLoaded`事件

  - 是等HTML文档完全加载完和解析完之后运行的事件
  - 在`load`事件之前。
  - 不用等样式表、图像等完成加载

**什么是BFC？**⭐⭐⭐⭐⭐

- BFC是一个独立渲染区域，它丝毫不会影响到外部元素
- BFC特性
  - 同一个BFC下margin会重叠
  - 计算BFC高度时会算上浮动元素
  - BFC不会影响到外部元素
  - BFC内部元素是垂直排列的
  - BFC区域不会与float元素重叠
- 如何创建BFC
  - position设为absolute或者fixed
  - float不为none
  - overflow设置为hidden
  - display设置为inline-block或者inline-table或flex

**如何清除浮动**⭐⭐⭐⭐⭐

- 额外标签clear:both

  - ```
    <!DOCTYPE html>
    
    
    
    <html lang="en">
    
    
    
    <head>
    
    
    
        <meta charset="UTF-8">
    
    
    
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    
    
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
    
    
    
        <title>Document</title>
    
    
    
        <style>
    
    
    
        .fahter{
    
    
    
            width: 400px;
    
    
    
            border: 1px solid deeppink;
    
    
    
        }
    
    
    
        .big{
    
    
    
            width: 200px;
    
    
    
            height: 200px;
    
    
    
            background: darkorange;
    
    
    
            float: left;
    
    
    
        }
    
    
    
        .small{
    
    
    
            width: 120px;
    
    
    
            height: 120px;
    
    
    
            background: darkmagenta;
    
    
    
            float: left;
    
    
    
        }
    
    
    
     
    
    
    
        .clear{
    
    
    
            clear:both;
    
    
    
        }
    
    
    
        </style>
    
    
    
    </head>
    
    
    
    <body>
    
    
    
        <div class="fahter">
    
    
    
            <div class="big">big</div>
    
    
    
            <div class="small">small</div>
    
    
    
            <div class="clear">额外标签法</div>
    
    
    
        </div>
    
    
    
    </body>
    ```

     

- 利用BFC

  - `overflow:hidden`

  - ```
    .fahter{
    
    
    
            width: 400px;
    
    
    
            border: 1px solid deeppink;
    
    
    
            overflow: hidden;
    
    
    
        }
    ```

     

- 使用after(推荐)

- ```
  <style>
  
  
  
      .clearfix:after{/*伪元素是行内元素 正常浏览器清除浮动方法*/
  
  
  
          content: "";
  
  
  
          display: block;
  
  
  
          height: 0;
  
  
  
          clear:both;
  
  
  
          visibility: hidden;
  
  
  
      }
  
  
  
      .clearfix{
  
  
  
          *zoom: 1;/*ie6清除浮动的方式 *号只有IE6-IE7执行，其他浏览器不执行*/
  
  
  
      }
  
  
  
  </style>
  
  
  
  <body>
  
  
  
      <div class="fahter clearfix">
  
  
  
          <div class="big">big</div>
  
  
  
          <div class="small">small</div>
  
  
  
          <!--<div class="clear">额外标签法</div>-->
  
  
  
      </div>
  ```

   

**什么是DOM事件流？什么是事件委托**⭐⭐⭐⭐⭐

- DOM事件流
  - 分为三个阶段
    - 捕获阶段
    - 目标阶段
    - 冒泡阶段
  - 在addeventListener()的第三个参数(useCapture)设为true，就会在捕获阶段运行，默认是false冒泡
- 事件委托
  - 利用冒泡原理(子向父一层层穿透)，把事件绑定到父元素中，以实现事件委托

**link标签和import标签的区别**⭐⭐⭐⭐

- link属于html，而@import属于css
- 页面被加载时，link会同时被加载，而@import引用的css会等到页面加载结束后加载。
- link是html标签，因此没有兼容性，而@import只有IE5以上才能识别。
- link方式样式的权重高于@import的。

# 算法

> 这里推荐一个排序算法的动画网站，应该是一个国外团队做的，[Sorting Algorithms](http://math.hws.edu/eck/jsdemo/sortlab.html)

**冒泡算法排序**⭐⭐⭐⭐⭐

```
// 冒泡排序



    /* 1.比较相邻的两个元素，如果前一个比后一个大，则交换位置。



　　　2.第一轮的时候最后一个元素应该是最大的一个。



　　　3.按照步骤一的方法进行相邻两个元素的比较，这个时候由于最后一个元素已经是最大的了，所以最后一个元素不用比较。 */



    function bubbleSort(arr) {



      for (var i = 0; i < arr.length; i++) {



        for (var j = 0; j < arr.length; j++) {



          if (arr[j] > arr[j + 1]) {



            var temp = arr[j]



            arr[j] = arr[j + 1]



            arr[j + 1] = temp



          }



        }



      }



    }



 



    var Arr = [3, 5, 74, 64, 64, 3, 1, 8, 3, 49, 16, 161, 9, 4]



    console.log(Arr, "before");



    bubbleSort(Arr)



    console.log(Arr, "after");



 
```

 

**快速排序**⭐⭐⭐⭐⭐

```
/*



    快速排序是对冒泡排序的一种改进，第一趟排序时将数据分成两部分，一部分比另一部分的所有数据都要小。



    然后递归调用，在两边都实行快速排序。  



    */



    



    function quickSort(arr) {



      if (arr.length <= 1) {



        return arr



      }



      var middle = Math.floor(arr.length / 2)



      var middleData = arr.splice(middle, 1)[0]



 



      var left = []



      var right = []



      



      for (var i = 0; i < arr.length; i++) {



        if (arr[i] < middleData) {



          left.push(arr[i])



        } else {



          right.push(arr[i])



        }



      }



 



      return quickSort(left).concat([middleData], quickSort(right))



    }



 



    var Arr = [3, 5, 74, 64, 64, 3, 1, 8, 3, 49, 16, 161, 9, 4]



    console.log(Arr, "before");



    var newArr = quickSort(Arr)



    console.log(newArr, "after");
```

 

**插入排序**⭐⭐⭐⭐

```
function insertSort(arr) {



      // 默认第一个排好序了



      for (var i = 1; i < arr.length; i++) {



        // 如果后面的小于前面的直接把后面的插到前边正确的位置



        if (arr[i] < arr[i - 1]) {



          var el = arr[i]



          arr[i] = arr[i - 1]



          var j = i - 1



          while (j >= 0 && arr[j] > el) {



            arr[j+1] = arr[j]



            j--



          }



          arr[j+1] = el



        }



      }



    }



 



    var Arr = [3, 5, 74, 64, 64, 3, 1, 8, 3, 49, 16, 161, 9, 4]



    console.log(Arr, "before");



    insertSort(Arr)



    console.log(Arr, "after");
```

 

**是否回文**⭐⭐⭐⭐⭐

```
function isHuiWen(str) {



      return str == str.split("").reverse().join("")



    }



 



    console.log(isHuiWen("mnm")); 
```

 

**正则表达式，千分位分隔符**⭐⭐⭐⭐

```
function thousand(num) {



 



      return (num+"").replace(/\d(?=(\d{3})+$)/g, "$&,")



    }



    console.log(thousand(123456789));
```

 

**斐波那契数列**⭐⭐⭐⭐⭐

```
// num1前一项



    // num2当前项



    function fb(n, num1 = 1, num2 = 1) {



      if(n == 0) return 0



      if (n <= 2) {



        return num2



      } else {



        return fb(n - 1, num2, num1 + num2)



      }



    }
```

 

**数组去重的方式**⭐⭐⭐⭐⭐

```
var arr = [1, 2, 1, 1, 1, 2, 3, 3, 3, 2]



 



    // 最low1



    let newArr2 = []



    for (let i = 0; i < arr.length; i++) {



      if (!newArr2.includes(arr[i])) {



        newArr2.push(arr[i])



      }



    }



    console.log(newArr2);



    // 最low2



    let arr2 = [1, 2, 1, 1, 1, 2, 3, 3, 3, 2]



    for (let i = 0; i < arr2.length; i++) {



      var item = arr2[i]



      for (let j = i + 1; j < arr2.length; j++) {



        var compare = arr2[j];



        if (compare === item) {



          arr2.splice(j, 1)



          j--



        }



      }



    }



    console.log(arr2);



 



 



    // 基于对象去重



    let arr3 = [1, 2, 1, 1, 1, 2, 3, 3, 3, 2]



    let obj = {}



    for (let i = 0; i < arr3.length; i++) {



 



      let item = arr3[i]



      if (obj[item]) {



        arr3[i] = arr3[arr3.length - 1]



        arr3.length--



        i--



        continue;



      }



      obj[item] = item



 



    }



    console.log(arr3);



    console.log(obj);



 



    // 利用Set



    let newArr1 = new Set(arr)



    console.log([...newArr1]);



 



 



    let arr4 = [1, 2, 1, 1, 1, 2, 3, 3, 3, 2]



 



    //利用reduce



    newArr4 = arr4.reduce((prev, curr) => prev.includes(curr)? prev : [...prev,curr],[])



    console.log(newArr4);



    console.log(document);
```

 

# git

**git的常用命令**⭐⭐⭐⭐⭐

- commit之后撤回
  - git reset soft HEAD^
- 分支
  - git branch xx 创建分支
  - git checkout xx切换分支
- 添加
  - git add .
  - git push
  - git commit -m

 

![在这里插入图片描述](https://img-blog.csdnimg.cn/img_convert/e3443b440db70ada83e3a56566a025a7.png)

> [git常用命令与常见面试题总结](https://blog.csdn.net/qq_36095679/article/details/91804051)

> **震惊！你竟然看完了，看来你距离大神就差一点点了！**

 

![在这里插入图片描述](https://img-blog.csdnimg.cn/img_convert/5357e6d57368f2e71534af78e4357f4e.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/img_convert/a5106f39d22ef5fae49c23ddd84f1c3d.png)


ok，今天的文章就到这里了。

- 如果你觉得文章不错的话，可以收藏点赞，也可以关注上我，今后我可能会根据一些大公司的面试题进行在总结。
