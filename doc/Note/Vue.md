- 列表进入详情页的传参问题。
  - 参考：
    1. [vue路由传参的三种基本方式](https://segmentfault.com/a/1190000012393587)
    2. [vue 关于路由跳转方法](https://blog.csdn.net/gqzydh/article/details/81453990)
  - 路由懒加载（也叫延迟加载）
- 本地开发环境请求服务器接口跨域的问题(3个解决方案)
  - 使用 vue 的代理服务解决跨域问题！（也是最常用的）
  - 使用 vue cli 的配置
  - 使用浏览器特性
- axios封装和api接口的统一管理
  - axios库，它是基于promise的http库，可运行在浏览器端和node.js中。他有很多优秀的特性，例如拦截请求和响应、取消请求、转换json、客户端防御XSRF等。所以我们的尤大大也是果断放弃了对其官方库vue-resource的维护，直接推荐我们使用axios库
  - https://www.jb51.net/article/202891.htm
- UI库的按需加载
- 如何优雅的只在当前页面中覆盖ui库中组件的样式
- 定时器问题
- rem文件的导入问题
- Vue-Awesome-Swiper基本能解决你所有的轮播需求
- 打包后生成很大的.map文件的问题
- fastClick的300ms延迟解决方
- 组件中写选项的顺序



# 语法



# 生命周期

- Vue生命周期(11个钩子函数)

  https://blog.csdn.net/Sheng_zhenzhen/article/details/104623260

- Vue - 生命周期详解

  https://www.jianshu.com/p/672e967e201c

## beforeCreate( 创建前 )

在实例初始化之后，数据观测和事件配置之前被调用，此时组件的选项对象还未创建，el 和 data 并未初始化，因此无法访问methods， data， computed等上的方法和数据。

## created ( 创建后 ）

实例已经创建完成之后被调用，在这一步，实例已完成以下配置：数据观测、属性和方法的运算，watch/event事件回调，完成了data 数据的初始化，el没有。 然而，挂在阶段还没有开始, $el属性目前不可见，这是一个常用的生命周期，因为你可以调用methods中的方法，改变data中的数据，并且修改可以通过vue的响应式绑定体现在页面上，，获取computed中的计算属性等等，通常我们可以在这里对实例进行预处理，也有一些童鞋喜欢在这里发ajax请求，值得注意的是，这个周期中是没有什么方法来对实例化过程进行拦截的，因此假如有某些数据必须获取才允许进入页面的话，并不适合在这个方法发请求，建议在组件路由钩子beforeRouteEnter中完成

## beforeMount

挂在开始之前被调用，相关的render函数首次被调用（虚拟DOM），实例已完成以下的配置： 编译模板，把data里面的数据和模板生成html，完成了el和data 初始化，注意此时还没有挂在html到页面上。

## mounted

挂在完成，也就是模板中的HTML渲染到HTML页面中，此时一般可以做一些ajax操作，mounted只会执行一次。

## beforeUpdate

在数据更新之前被调用，发生在虚拟DOM重新渲染和打补丁之前，可以在该钩子中进一步地更改状态，不会触发附加地重渲染过程

## updated（更新后）

在由于数据更改导致地虚拟DOM重新渲染和打补丁只会调用，调用时，组件DOM已经更新，所以可以执行依赖于DOM的操作，然后在大多是情况下，应该避免在此期间更改状态，因为这可能会导致更新无限循环，该钩子在服务器端渲染期间不被调用

## beforeDestroy（销毁前）

在实例销毁之前调用，实例仍然完全可用，

1. 这一步还可以用this来获取实例，
2. 一般在这一步做一些重置的操作，比如清除掉组件中的定时器  和 监听的dom事件

## destroyed（销毁后）

在实例销毁之后调用，调用后，所以的事件监听器会被移出，所有的子实例也会被销毁，该钩子在服务器端渲染期间不被调用



# 问题

https://www.jianshu.com/p/b25cb6f4703d

# ![\color{red}{生命周期及作用}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7B%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F%E5%8F%8A%E4%BD%9C%E7%94%A8%7D)

beforeCreate:是new Vue()之后触发的第一个钩子函数。当前阶段vue对象的属性和方法都无法访问。
 created: 创建vue实例完成后发生，当前阶段完成了数据的观测，即可以使用和更改数据，但是更改数据不会触发update函数。一般再此阶段进行初始数据的获取，无法进行dom操作（可以使用vm.nextTick()）访问。
 beforeMount:在挂载完成之前发生，在这之前template模板已经导入渲染函数中编译。当前阶段虚拟Dom已经创建完成，可以更改数据，但是也不会触发update()函数。
 mounted: 发生在挂载之后，在当前阶段真实的dom已经挂载完毕，数据完成双向绑定，可以访问到Dom节点，使用$refs属性可以进行dom操作.
 beforeUpdate: 发生在更新之前，即虚拟dom触发前被触发，在当前阶段进行数据更改不会造成重渲染。
 updated: 发生在更新完成之后，当前阶段组件Dom已完成更新。
 beforeDestroy: 发生在实例销毁之前，在此进行收尾工作（清除计时器等）
 destroyed: 发生在实例销毁后。

# ![\color{red}{为什么组件的data必须是一个函数}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7B%E4%B8%BA%E4%BB%80%E4%B9%88%E7%BB%84%E4%BB%B6%E7%9A%84data%E5%BF%85%E9%A1%BB%E6%98%AF%E4%B8%80%E4%B8%AA%E5%87%BD%E6%95%B0%7D)

因为vue组件具有可复用性，因此可能在不同地方使用同一个组件。如果是对象的话，在一个地方修改了data的属性，会影响到其他的实例，所以data必须使用函数，为每一个实例创建一个只属于自己的data。

# ![\color{red}{Vue的响应式原理}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7BVue%E7%9A%84%E5%93%8D%E5%BA%94%E5%BC%8F%E5%8E%9F%E7%90%86%7D)

Vue为MVVM框架，当数据模型data变化时，页面视图会得到响应更新，其原理对data的getter/setter方法进行拦截（Object.defineProperty或者Proxy），利用发布订阅的设计模式，在getter方法中进行订阅，在setter方法中发布通知，让所有订阅者完成响应。
 在响应式系统中，Vue会为数据模型data的每一个属性新建一个订阅中心作为发布者，而监听器watch、计算属性computed、视图渲染template/render三个角色同时作为订阅者，对于监听器watch，会直接订阅观察监听的属性，对于计算属性computed和视图渲染template/render，如果内部执行获取了data的某个属性，就会执行该属性的getter方法，然后自动完成对该属性的订阅，当属性被修改时，就会执行该属性的setter方法，从而完成该属性的发布通知，通知所有订阅者进行更新。

# ![\color{red}{computed与watch的区别}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7Bcomputed%E4%B8%8Ewatch%E7%9A%84%E5%8C%BA%E5%88%AB%7D)

计算属性computed和监听器watch都可以观察属性的变化从而做出响应，不同的是：
 计算属性computed更多是作为缓存功能的观察者，它可以将一个或者多个data的属性进行复杂的计算生成一个新的值，提供给渲染函数使用，当依赖的属性变化时，computed不会立即重新计算生成新的值，而是先标记为脏数据，当下次computed被获取时候，才会进行重新计算并返回。
 而监听器watch并不具备缓存性，监听器watch提供一个监听函数，当监听的属性发生变化时，会立即执行该函数。

# ![\color{red}{vue组件传值}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7Bvue%E7%BB%84%E4%BB%B6%E4%BC%A0%E5%80%BC%7D)

父组件 -> 子组件：prop
 子组件 -> 父组件：![on/](https://math.jianshu.com/math?formula=on%2F)emit
 兄弟组件通信：
 Event Bus：每一个Vue实例都是一个Event Bus，都支持![on/](https://math.jianshu.com/math?formula=on%2F)emit，可以为兄弟组件的实例之间new一个Vue实例，作为Event Bus进行通信。
 跨级组件通信：使用provide/inject

# ![\color{red}{slot}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7Bslot%7D)

插槽slot是子组件的一个模板标签元素，而这一个标签元素是否显示，以及怎么显示是由父组件决定的。
 slot又分三类，默认插槽，具名插槽和作用域插槽。
 默认插槽：又名匿名插槽，当slot没有指定name属性值的时候一个默认显示插槽，一个组件内只有有一个匿名插槽。
 具名插槽：带有具体名字的插槽，也就是带有name属性的slot，一个组件可以出现多个具名插槽。
 作用域插槽：默认插槽、具名插槽的一个变体，可以是匿名插槽，也可以是具名插槽，该插槽的不同点是在子组件渲染作用域插槽时，可以将子组件内部的数据传递给父组件，让父组件根据子组件的传递过来的数据决定如何渲染该插槽。
 slot主要用于组件的个性化定制功能。

# ![\color{red}{Vue模板渲染的原理}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7BVue%E6%A8%A1%E6%9D%BF%E6%B8%B2%E6%9F%93%E7%9A%84%E5%8E%9F%E7%90%86%7D)

vue中的模板template无法被浏览器解析并渲染，因为这不属于浏览器的标准，不是正确的HTML语法，所有需要将template转化成一个JavaScript函数，这样浏览器就可以执行这一个函数并渲染出对应的HTML元素，就可以让视图跑起来了，这一个转化的过程，就成为模板编译。
 模板编译又分三个阶段，解析parse，优化optimize，生成generate，最终生成可执行函数render。

#### 1.parse阶段：使用大量的正则表达式对template字符串进行解析，将标签、指令、属性等转化为抽象语法树AST。

#### 2.optimize阶段：遍历AST，找到其中的一些静态节点并进行标记，方便在页面重渲染的时候进行diff比较时，直接跳过这一些静态节点，优化runtime的性能。

#### 3.generate阶段：将最终的AST转化为render函数字符串。

# ![\color{red}{template和jsx的区别}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7Btemplate%E5%92%8Cjsx%E7%9A%84%E5%8C%BA%E5%88%AB%7D)

对于 runtime 来说，只需要保证组件存在 render 函数即可，而我们有了预编译之后，我们只需要保证构建过程中生成 render 函数就可以。
 在 webpack 中，我们使用vue-loader编译.vue文件，内部依赖的vue-template-compiler模块，在 webpack 构建过程中，将template预编译成 render 函数。
 与 react 类似，在添加了jsx的语法糖解析器babel-plugin-transform-vue-jsx之后，就可以直接手写render函数。
 所以，template和jsx的都是render的一种表现形式，不同的是：
 JSX相对于template而言，具有更高的灵活性，在复杂的组件中，更具有优势，而 template 虽然显得有些呆滞。但是 template 在代码结构上更符合视图与逻辑分离的习惯，更简单、更直观、更好维护。

# ![\color{red}{Virtual DOM和DIFF算法}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7BVirtual%20DOM%E5%92%8CDIFF%E7%AE%97%E6%B3%95%7D)

Virtual DOM 是 DOM 节点在 JavaScript 中的一种抽象数据结构，之所以需要虚拟DOM，是因为浏览器中操作DOM的代价比较昂贵，频繁操作DOM会产生性能问题。虚拟DOM的作用是在每一次响应式数据发生变化引起页面重渲染时，Vue对比更新前后的虚拟DOM，匹配找出尽可能少的需要更新的真实DOM，从而达到提升性能的目的。
 在新老虚拟DOM对比时

#### 首先，对比节点本身，判断是否为同一节点，如果不为相同节点，则删除该节点重新创建节点进行替换

#### 如果为相同节点，进行patchVnode，判断如何对该节点的子节点进行处理，先判断一方有子节点一方没有子节点的情况(如果新的children没有子节点，将旧的子节点移除)

#### 比较如果都有子节点，则进行updateChildren，判断如何对这些新老节点的子节点进行操作（diff核心）。

#### 匹配时，找到相同的子节点，递归比较子节点

在diff中，只对同层的子节点进行比较，放弃跨级的节点比较，使得时间复杂从O(n^3)降低值O(n)，也就是说，只有当新旧children都为多个子节点时才需要用核心的Diff算法进行同层级比较。

# ![\color{red}{key属性的作用是什么}](https://math.jianshu.com/math?formula=%5Ccolor%7Bred%7D%7Bkey%E5%B1%9E%E6%80%A7%E7%9A%84%E4%BD%9C%E7%94%A8%E6%98%AF%E4%BB%80%E4%B9%88%7D)

在对节点进行diff的过程中，判断是否为相同节点的一个很重要的条件是key是否相等，如果是相同节点，则会尽可能的复用原有的DOM节点。所以key属性是提供给框架在diff的时候使用的，而非开发者。

 