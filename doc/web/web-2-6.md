2021-09-12 17:20

[toc]

# 回答面试题的套路 

1､先说这个点的明确定义，或者是特性；

2､再说具体的应用场景；

3､说说自己的看法、观点；

4､可以稍微举一反三，说说同类特性，或者类似的框架，更好的方案。

# 简短的概括

## 常考基础点

  1. 对 SPA 单页面应用的理解，优缺点是什么？

  2. new Vue() 发生了什么？

  3. Vue.use是干什么的？原理是什么？

  4. 请说一下响应式数据的理解？

  5. Vue如何检测数组变化？

  6. Vue.set 方法是如何实现的？

  7. Vue中模板编译原理？

  8. Proxy 与 Object.defineProperty 优劣对比

  9. Vue3.x响应式数据原理

##  常考-生命周期

  1. Vue的生命周期方法有哪些？一般在哪一步发起请求及原因

  2. 生命周期钩子是如何实现的？

  3. Vue 的父组件和子组件生命周期钩子执行顺序

##  常考-组件通信

 1. Vue中的组件的data 为什么是一个函数？

 2. Vue 组件间通信有哪几种方式？

 3. 组件中写 name选项有哪些好处及作用？

 4. keep-alive平时在哪里使用？原理是？

 5. Vue.minxin的使用场景和原理？

##  常考-路由

 1. Vue-router有几种钩子函数？具体是什么及执行流程是怎样的？

 2. vue-router 两种模式的区别？

##  常考-属性作用与对比

  1. nextTick在哪里使用？原理是？

  2. Vue 为什么需要虚拟DOM？  虚拟DOM的优劣如何？

  3. Vue中key的作用和工作原理，说说你对它的理解

  4. Vue 中的diff原理

  5. v-if 与 v-for的优先级

  6. v-if与v-show的区别  

  7. computed 和 watch 的区别和运用的场景？

  8. 如何理解自定义指令？

##  常考-性能优化

  1. 编码阶段

  2. 用户体验

  3. SEO优化

  4. 打包优化

**正文从这里开始~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**

# 一、常考-基础点

**对 SPA 单页面应用的理解，优缺点是什么？**

核心答案：

SPA（ single-page application ）仅在 Web 页面初始化时加载相应的 HTML、JavaScript 和 CSS。⼀旦⻚⾯加载完成，SPA 不会因为用户的操作而进行页面的重新加载或跳转；取而代之的是利用路由机制实现 HTML 内容的变换，UI 与用户的交互，避免页面的重新加载。

优点：

1）用户体验好、快，内容的改变不需要重新加载整个页面，避免了不必要的跳转和重复渲染；

2）SPA 相对对服务器压力小；

3）前后端职责分离，架构清晰，前端进行交互逻辑，后端负责数据处理；

缺点：

1）首屏（初次）加载慢：为实现单页面 Web 应用功能及显示效果，需要在加载资源的时候将JavaScript、CSS 统一加载，部分页面按需加载；

2）不利于 SEO：由于所有的内容都在一个页面中动态替换显示，所以在 SEO 上其有着天然的弱势。

**new Vue() 发生了什么？**

核心答案：

1）结论：new Vue()是创建Vue实例，它内部执行了根实例的初始化过程。

2）具体包括以下操作：

选项合并

```
$children，$refs，$slots，$createElement等实例属性的方法初始化
```

自定义事件处理

数据响应式处理

生命周期钩子调用 （beforecreate created）

可能的挂载

3）总结：new Vue()创建了根实例并准备好数据和方法，未来执行挂载时，此过程还会递归的应用于它的子组件上，最终形成一个有紧密关系的组件实例树。

**Vue.use是干什么的？原理是什么？**

核心答案：

vue.use 是用来使用插件的，我们可以在插件中扩展全局组件、指令、原型方法等。

1､检查插件是否注册，若已注册，则直接跳出；

2､处理入参，将第一个参数之后的参数归集，并在首部塞入 this 上下文；

3､执行注册方法，调用定义好的 install 方法，传入处理的参数，若没有 install 方法并且插件本身为 function 则直接进行注册；

 1) 插件不能重复的加载

install 方法的第一个参数是vue的构造函数，其他参数是Vue.set中除了第一个参数的其他参数； 代码：args.unshift(this)

 2) 调用插件的install 方法 代码：typeof plugin.install === 'function'

 3) 插件本身是一个函数，直接让函数执行。 代码：plugin.apply(null, args)

 4) 缓存插件。 代码：installedPlugins.push(plugin)

**请说一下响应式数据的理解？**

核心答案：

根据数据类型来做不同处理，数组和对象类型当值变化时如何劫持。

1)对象内部通过defineReactive方法，使用Object.defineProperty() 监听数据属性的 get 来进行数据依赖收集，再通过 set 来完成数据更新的派发；

 2) 数组则通过重写数组方法来实现的。扩展它的 7 个变更⽅法，通过监听这些方法可以做到依赖收集和派发更新；( push/pop/shift/unshift/splice/reverse/sort )

这里在回答时可以带出一些相关知识点 （比如多层对象是通过递归来实现劫持，顺带提出vue3中是使用 proxy来实现响应式数据）

补充回答：

内部依赖收集是怎么做到的，每个属性都拥有自己的dep属性，存放他所依赖的 watcher，当属性变化后会通知自己对应的 watcher去更新。

响应式流程：

1､defineReactive  把数据定义成响应式的；

2､给属性增加一个 dep，用来收集对应的那些watcher；

3､等数据变化进行更新

dep.depend() // get 取值：进行依赖收集

dep.notify() // set 设置时：通知视图更新

这里可以引出性能优化相关的内容：

1)对象层级过深，性能就会差。

2)不需要响应数据的内容不要放在data中。

3)object.freeze()  可以冻结数据。

**Vue如何检测数组变化？**

核心答案：

数组考虑性能原因没有用defineProperty对数组的每一项进行拦截，而是选择重写数组 方法以进行重写。当数组调用到这 7 个方法的时候，执行 ob.dep.notify() 进行派发通知 Watcher 更新；

重写数组方法：push/pop/shift/unshift/splice/reverse/sort

补充回答：

在Vue中修改数组的索引和长度是无法监控到的。需要通过以下7种变异方法修改数组才会触发数组对应的wacther进行更新。数组中如果是对象数据类型也会进行递归劫持。

说明：那如果想要改索引更新数据怎么办？

可以通过Vue.set()来进行处理 =》 核心内部用的是 splice 方法。

// 取出原型方法；

const arrayProto = Array.prototype 

// 拷贝原型方法；

export const arrayMethods = Object.create(arrayProto) 

// 重写数组方法；

def(arrayMethods, method, function mutator (...args) { }

ob.dep.notify() // 调用方法时更新视图；

**Vue.set 方法是如何实现的？**

核心答案：

为什么$set可以触发更新，我们给对象和数组本身都增加了dep属性，当给对象新增不存在的属性则触发对象依赖的watcher去更新，当修改数组索引时我们调用数组本身的splice方法去更新数组。

补充回答：

官方定义Vue.set(object, key, value) 

 1) 如果是数组，调用重写的splice方法 （这样可以更新视图 ）

代码：target.splice(key, 1, val)

 2) 如果不是响应式的也不需要将其定义成响应式属性。

 3) 如果是对象，将属性定义成响应式的  defineReactive(ob.value, key, val)

通知视图更新  ob.dep.notify()

**Vue中模板编译原理？**

核心答案：

如何将template转换成render函数(这里要注意的是我们在开发时尽量不要使用template，因为将template转化成render方法需要在运行时进行编译操作会有性能损耗，同时引用带有complier包的vue体积也会变大) 默认.vue文件中的 template处理是通过vue-loader 来进行处理的并不是通过运行时的编译。

 1) 将 template 模板转换成 ast 语法树 - parserHTML

 2) 对静态语法做静态标记 - markUp

 3) 重新生成代码 - codeGen

补充回答：

模板引擎的实现原理就是new Function + with来进行实现的。

vue-loader中处理template属性主要靠的是vue-template-compiler

**vue-loader**

// template => ast => codegen => with+function 实现生成render方法 

let {ast, render } = VueTemplateCompiler.compile(`<div>{{aaa}}</div>`)

console.log(ast, render)

// 模板引擎的实现原理 with + new Function

console.log(new Function(render).tostring())

// render方法执行完毕后生成的是虚拟 dom

// with(this){return _c('div',[_s(aaa)])}

// 代码生成

源码设置：

const ast = parse(template.trim(), options) // 将代码解析成ast语法树

 if (options.optimize !== false) {

  optimize(ast, options) // 优化代码 标记静态点 标记树

 }

 const code = generate(ast, options) // 生成代码 

**Proxy 与 Object.defineProperty 优劣对比**

核心答案：

Proxy 的优势如下:

1）Proxy 可以直接监听对象而非属性；

2）Proxy 可以直接监听数组的变化；

3）Proxy 有多达 13 种拦截方法,不限于 apply、ownKeys、deleteProperty、has 等等是 Object.defineProperty 不具备的；

4）Proxy 返回的是一个新对象,我们可以只操作新的对象达到目的,而 Object.defineProperty 只能遍历对象属性直接修改；

5）Proxy 作为新标准将受到浏览器厂商重点持续的性能优化，也就是传说中的新标准的性能红利；

Object.defineProperty 的优势如下:

兼容性好，支持 IE9，而Proxy 的存在浏览器兼容性问题，而且无法用 polyfill 磨平，因此 Vue 的作者才声明需要等到下个大版本( 3.0 )才能用 Proxy 重写。

**Vue3.x响应式数据原理**

核心答案：

Vue3.x改用Proxy替代Object.defineProperty。因为Proxy可以直接监听对象和数组的变化，并且有多达13种拦截方法。并且作为新标准将受到浏览器厂商重点持续的性能优化。

Proxy只会代理对象的第一层，那么Vue3又是怎样处理这个问题的呢？

判断当前Reflect.get的返回值是否为Object，如果是则再通过reactive方法做代理， 这样就实现了深度观测。

监测数组的时候可能触发多次get/set，那么如何防止触发多次呢？

我们可以判断key是否为当前被代理对象target自身属性，也可以判断旧值与新值是否相等，只有满足以上两个条件之一时，才有可能执行trigger。

# **二、常考-生命周期** 

**Vue的生命周期方法有哪些？一般在哪一步发起请求及原因**

核心答案：

总共分为8个阶段：创建前/后，载入前/后，更新前/后，销毁前/后。

**1､创建前/后：**

 1) beforeCreate阶段：vue实例的挂载元素el和数据对象data都为undefined，还未初始化。

说明：在当前阶段data、methods、computed以及watch上的数据和方法都不能被访问。

 2) created阶段：vue实例的数据对象data有了，el还没有。

说明：可以做一些初始数据的获取，在当前阶段无法与Dom进行交互，如果非要想，可以通过vm.$nextTick来访问Dom。

**2､载入前/后：**

1)beforeMount阶段：vue实例的$el和data都初始化了，但还是挂载之前为虚拟的dom节点。

说明：当前阶段虚拟Dom已经创建完成，即将开始渲染。在此时也可以对数据进行更改，不会触发updated。

2)mounted阶段：vue实例挂载完成，data.message成功渲染。

说明：在当前阶段，真实的Dom挂载完毕，数据完成双向绑定，可以访问到Dom节点，使用$refs属性对Dom进行操作。

**3､更新前/后**：

1)beforeUpdate阶段：响应式数据更新时调用，发生在虚拟DOM打补丁之前，适合在更新之前访问现有的DOM，比如手动移除已添加的事件监听器。

说明：可以在当前阶段进行更改数据，不会造成重渲染。

 2) updated阶段：虚拟DOM重新渲染和打补丁之后调用，组成新的DOM已经更新，避免在这个钩子函数中操作数据，防止死循环。

说明：当前阶段组件Dom已完成更新。要注意的是避免在此期间更改数据，因为这可能会导致无限循环的更新。

**4､销毁前/后**：

1)beforeDestroy阶段：实例销毁前调用，实例还可以用，this能获取到实例，常用于销毁定时器，解绑事件。

说明：在当前阶段实例完全可以被使用，我们可以在这时进行善后收尾工作，比如清除计时器。

 2) destroyed阶段：实例销毁后调用，调用后所有事件监听器会被移除，所有的子实例都会被销毁。

说明：当前阶段组件已被拆解，数据绑定被卸除，监听被移出，子实例也统统被销毁。

补充回答：

第一次页面加载时会触发：beforeCreate, created, beforeMount, mounted。

1)created 实例已经创建完成，因为它是最早触发的原因可以进行一些数据，资源的请求。(服务器渲染支持created方法)

2)mounted实例已经挂载完成，可以进行一些DOM操作。(接口请求)

**生命周期钩子是如何实现的？**

核心答案：

Vue的生命周期钩子就是回调函数而已，当创建组件实例的过程中会调用对应的钩子方法。

补充回答：

内部主要是使用callHook方法来调用对应的方法。核心是一个发布订阅模式，将钩子订阅好(内部采用数组的方式存储)，在对应的阶段进行发布。

**Vue 的父组件和子组件生命周期钩子执行顺序**

核心答案：

第一次页面加载时会触发 beforeCreate, created, beforeMount, mounted 这几个钩子。

渲染过程：

父组件挂载完成一定是等子组件都挂载完成后，才算是父组件挂载完，所以父组件的mounted在子组件mouted之后

父beforeCreate -> 父created -> 父beforeMount -> 子beforeCreate -> 子created -> 子beforeMount -> 子mounted -> 父mounted

子组件更新过程：

影响到父组件：父beforeUpdate -> 子beforeUpdate->子updated -> 父updted

不影响父组件：子beforeUpdate -> 子updated

父组件更新过程：

影响到子组件：父beforeUpdate -> 子beforeUpdate->子updated -> 父updted

不影响子组件：父beforeUpdate -> 父updated

销毁过程：

父beforeDestroy -> 子beforeDestroy -> 子destroyed -> 父destroyed

重要：父组件等待子组件完成后，才会执行自己对应完成的钩子。

# **三、常考-组件通信**

**Vue中的组件的data 为什么是一个函数？**

核心答案：

每次使用组件时都会对组件进行实例化操作，并且调用data函数返回一个对象作为组件的数据源。这样可以保证多个组件间数据互不影响。

如果data是对象的话，对象属于引用类型，会影响到所有的实例。所以为了保证组件不同的实例之间data不冲突，data必须是一个函数。

**Vue 组件间通信有哪几种方式？**

核心答案：

Vue 组件间通信只要指以下 3 类通信：父子组件通信、隔代组件通信、兄弟组件通信

下面我们分别介绍每种通信方式且会说明此种方法可适用于哪类组件间通信。

1、props / $emit 适用 父子组件通信

这种方法是 Vue 组件的基础，相信大部分同学耳闻能详，所以此处就不举例展开介绍。

2、ref 与 $parent / $children 适用 父子组件通信

1）ref：如果在普通的 DOM 元素上使用，引用指向的就是 DOM 元素；如果用在子组件上，引用就指向组件实例

2）$parent / $children：访问父 / 子实例

3、EventBus （$emit / $on） 适用于 父子、隔代、兄弟组件通信

这种方法通过一个空的 Vue 实例作为中央事件总线（事件中心），用它来触发事件和监听事件，从而实现任何组件间的通信，包括父子、隔代、兄弟组件。

4、$attrs/$listeners 适用于 隔代组件通信

1）$attrs：包含了父作用域中不被 prop 所识别 (且获取) 的特性绑定 ( class 和 style 除外 )。当一个组件没有声明任何 prop 时，这里会包含所有父作用域的绑定 ( class 和 style 除外 )，并且可以通过 v-bind="$attrs" 传入内部组件。通常配合 inheritAttrs 选项一起使用。

2）$listeners：包含了父作用域中的 (不含 .native 修饰器的) v-on 事件监听器。它可以通过 v-on="$listeners" 传入内部组件

5、provide / inject 适用于 隔代组件通信

祖先组件中通过 provider 来提供变量，然后在子孙组件中通过 inject 来注入变量。provide / inject API 主要解决了跨级组件间的通信问题，不过它的使用场景，主要是子组件获取上级组件的状态，跨级组件间建立了一种主动提供与依赖注入的关系。

6、Vuex 适用于 父子、隔代、兄弟组件通信

Vuex 是一个专为 Vue.js 应用程序开发的状态管理模式。每一个 Vuex 应用的核心就是 store（仓库）。“store” 基本上就是一个容器，它包含着你的应用中大部分的状态 ( state )。

**组件中写 name选项有哪些好处及作用？**

核心答案：

 1) 可以通过名字找到对应的组件（ 递归组件 ）

 2) 可以通过name属性实现缓存功能 (keep-alive)

 3) 可以通过name来识别组件（跨级组件通信时非常重要）

Vue.extend = function () {

  if(name) {

​    Sub.options.componentd[name] = Sub

  }

}

**keep-alive平时在哪里使用？原理是？**

核心答案：

keep-alive 主要是组件缓存，采用的是LRU算法。最近最久未使用法。

常用的两个属性include/exclude，允许组件有条件的进行缓存。

两个生命周期activated/deactivated，用来得知当前组件是否处于活跃状态。

abstract: true, // 抽象组件 

props:{

  include: patternTypes, // 要缓存的有哪些

  exclude: patternTypes, // 要排除的有哪些

  max: [String, Number] //最大缓存数量 

}

if(cache[key]) { // 通过key 找到缓存，获取实例

  vnode.componentInstance = cache[key].componentInstance

  remove(keys, key) //将key删除掉 

  keys.push(key) // 放到末尾 

} else {

  cache[key] = vnode // 没有缓存过 

  keys.push(key) //存储key

  if(this.max && keys.length > parseInt(this.max)) { // 如果超过最大缓存数 

  // 删除最早缓存的 

  pruneCacheEntry(cache, keys[0], keys, this._vnode)

}

}

vnode.data.keepAlive = true // 标记走了缓存 

**Vue.minxin的使用场景和原理？**

核心答案：

Vue.mixin的作用就是抽离公共的业务逻辑，原理类似“对象的继承”，当组件初始化时会调用 mergeOptions方法进行合并，采用策略模式针对不同的属性进行合并，如果混入的数据和本身组件中的数据冲突，会采用“就近原则”以组件的数据为准。

补充回答：

mixin中有很多缺陷“命名冲突问题”，“依赖问题”，“数据来源问题”，这里强调一下mixin的数据是不会被共享的。

# **四、常考-路由** 

**Vue-router有几种钩子函数？具体是什么及执行流程是怎样的？**

核心答案：

路由钩子的执行流程，钩子函数种类有：全局守卫、路由守卫、组件守卫。

完整的导航解析流程

1.导航被触发；

2.在失活的组件里调用beforeRouteLeave守卫；

3.调用全局beforeEach守卫；

4.在复用组件里调用beforeRouteUpdate守卫；

5.调用路由配置里的beforeEnter守卫；

6.解析异步路由组件；

7.在被激活的组件里调用beforeRouteEnter守卫；

8.调用全局beforeResolve守卫；

9.导航被确认；

10.调用全局的afterEach钩子；

11.DOM更新；

12.用创建好的实例调用beforeRouteEnter守卫中传给next的回调函数。

**vue-router 两种模式的区别？**

核心答案：

vue-router 有 3 种路由模式：hash、history、abstract。

 1) hash模式：hash + hashChange

特点：

hash虽然在URL中，但不被包括在HTTP请求中

用来指导浏览器动作，对服务端安全无用，hash不会重加载页面

通过监听 hash（#）的变化来执行js代码 从而实现 页面的改变

核心代码：

window.addEventListener(‘hashchange‘,function(){

  self.urlChange()

})

 2) history模式：historyApi + popState

HTML5推出的history API，由pushState()记录操作历史，监听popstate事件来监听到状态变更；

因为 只要刷新 这个url（www.ff.ff/jjkj/fdfd/fdf/fd）就会请求服务器，然而服务器上根本没有这个资源，所以就会报404，解决方案就 配置一下服务器端。

说明：

1）hash: 使用 URL hash 值来作路由。支持所有浏览器，包括不支持 HTML5 History Api 的浏览器；

2）history : 依赖 HTML5 History API 和服务器配置。具体可以查看 HTML5 History 模式；

3）abstract : 支持所有 JavaScript 运行环境，如 Node.js 服务器端。如果发现没有浏览器的 API，路由会自动强制进入这个模式.

**五、常考-属性作用与对比** 

**nextTick在哪里使用？原理是？**

核心答案：

nextTick的回调是在下次DOM更新循环结束之后执行的延迟回调。在修改数据之后立即使用这个方法，获取更新后的DOM。nextTick主要使用了宏任务和微任务。原理就是异步方法(promise, mutationObserver, setImmediate, setTimeout)经常与事件循环一起来问。

补充回答：

vue多次更新数据，最终会进行批处理更新。内部调用的就是nextTick实现了延迟更新，用户自定义的nextTick中的回调会被延迟到更新完成后调用，从而可以获取更新后的DOM。

**Vue 为什么需要虚拟DOM？ 虚拟DOM的优劣如何？**

核心答案：

Virtual DOM 就是用js对象来描述真实DOM，是对真实DOM的抽象，由于直接操作DOM性能低但是js层的操作效率高，可以将DOM操作转化成对象操作，最终通过diff算法比对差异进行更新DOM (减少了对真实DOM的操作)。虚拟DOM不依赖真实平台环境从而也可以实现跨平台。

补充回答：

虚拟DOM的实现就是普通对象包含tag、data、children等属性对真实节点的描述。（本质上就是在JS和DOM之间的一个缓存）

Vue2的 Virtual DOM 借鉴了开源库snabbdom的实现。

VirtualDOM映射到真实DOM要经历VNode的create、diff、patch等阶段。

**Vue中key的作用和工作原理，说说你对它的理解**

核心答案：

例如：v-for="(item, itemIndex) in tabs" :key="itemIndex"

key的作用主要是为了高效的更新虚拟DOM，其原理是vue在patch过程中通过key可以精准判断两个节点是否是同一个，从而避免频繁更新不同元素，使得整个patch过程更加高效，减少DOM操作量，提高性能。

补充回答：

1、若不设置key还可能在列表更新时引发一些隐蔽的bug

2、vue中在使用相同标签名元素的过渡切换时，也会使用到key属性，其目的也是为了让vue可以区分它们，否则vue只会替换其内部属性而不会触发过渡效果。

**Vue 中的diff原理**

核心答案：

vue的diff算法是平级比较，不考虑跨级比较的情况。内部采用深度递归的方式 + 双指针的方式进行比较。

补充回答：

 1) 先比较是否是相同节点

 2) 相同节点比较属性，并复用老节点

 3) 比较儿子节点，考虑老节点和新节点儿子的情况

 4) 优化比较：头头、尾尾、头尾、尾头

 5) 比对查找进行复用

Vue2 与 Vue3.x 的diff算法：

Vue2的核心Diff算法采用了双端比较的算法，同时从新旧children的两端开始进行比较，借助key值找到可复用的节点，再进行相关操作。

Vue3.x借鉴了ivi算法和 inferno算法，该算法中还运用了动态规划的思想求解最长递归子序列。(实际的实现可以结合Vue3.x源码看。)

**v-if 与 v-for的优先级**

核心答案：

1、v-for优先于v-if被解析

2、如果同时出现，每次渲染都会先执行循环再判断条件，无论如何循环都不可避免，浪费了性能

3、要避免出现这种情况，则在外层嵌套template，在这一层进行v-if判断，然后在内部进行v-for循环

4、如果条件出现在循环内部，可通过计算属性提前过滤掉那些不需要显示的项

**v-if与v-show的区别**

核心答案：

v-if 是真正的条件渲染，直到条件第一次变为真时，才会开始渲染。

v-show 不管初始条件是什么会渲染，并且只是简单地基于 CSS 的 “display” 属性进行切换。

注意：v-if 适用于不需要频繁切换条件的场景；v-show 则适用于需要非常频繁切换条件的场景。

**computed 和 watch 的区别和运用的场景？**

核心答案：

computed： 计算属性。依赖其它属性值，并且 computed 的值有缓存，只有它依赖的属性值发生改变，下一次获取 computed 的值时才会重新计算 computed 的值；

watch： 监听数据的变化。更多的是「观察」的作用，类似于某些数据的监听回调 ，每当监听的数据变化时都会执行回调进行后续操作；

运用场景：

1）当我们需要进行数值计算，并且依赖于其它数据时，应该使用 computed，因为可以利用 computed 的缓存特性，避免每次获取值时，都要重新计算；

2）当我们需要在数据变化时执行异步或开销较大的操作时，应该使用 watch，使用 watch 选项允许我们执行异步操作 ( 访问一个 API )，限制我们执行该操作的频率，并在我们得到最终结果前，设置中间状态。这些都是计算属性无法做到的。

**如何理解自定义指令？**

核心答案：

指令的实现原理，可以从编译原理 =>代码生成=> 指令钩子实现进行概述

1､在生成 ast 语法树时，遇到指令会给当前元素添加directives属性

2､通过 genDirectives 生成指令代码

3､在patch前将指令的钩子提取到 cbs中，在patch过程中调用对应的钩子。

4､当执行指令对应钩子函数时，调用对应指令定义的方法

**V-model的原理是什么？**

核心答案：

v-model本质就是一个语法糖，可以看成是value + input方法的语法糖。可以通过model属性的prop和event属性来进行自定义。原生的v-model，会根据标签的不同生成不同的事件和属性。

v-model 在内部为不同的输入元素使用不同的属性并抛出不同的事件：

1）text 和 textarea 元素使用 value 属性和 input 事件；

2）checkbox 和 radio 使用 checked 属性和 change 事件；

3）select 字段将 value 作为 prop 并将 change 作为事件。

# **六、常考-性能优化** 

**Vue性能优化**

1、你都做过哪些Vue的性能优化？（ 统计后的结果 ）

**1）编码阶段**

尽量减少data中的数据，data中的数据都会增加getter和setter，会收集对应的watcher；

如果需要使用v-for给每项元素绑定事件时使用事件代理；

SPA 页面采用keep-alive缓存组件；

在更多的情况下，使用v-if替代v-show；

key保证唯一；

使用路由懒加载、异步组件；

防抖、节流；

第三方模块按需导入；

长列表滚动到可视区域动态加载；

图片懒加载；

**2）用户体验：**

骨架屏；

PWA；

还可以使用缓存(客户端缓存、服务端缓存)优化、服务端开启gzip压缩等。

**3）SEO优化**

预渲染；

服务端渲染SSR；

**4）打包优化**

压缩代码；

Tree Shaking/Scope Hoisting；

使用cdn加载第三方模块；

多线程打包happypack；

splitChunks抽离公共文件；

sourceMap优化；

**说明：**优化是个大工程，会涉及很多方面

 