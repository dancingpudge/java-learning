SPI（Service Provider Interface）和API（Application Programming Interface）都是用于软件开发和组件交互的接口，但它们之间有一些关键区别。

SPI（Service Provider Interface）是一种服务提供者接口，它定义了一组用于实现特定服务的接口或抽象类。SPI允许多个不同的实现提供相同的功能，从而使得软件模块在运行时可以动态地选择和加载不同的实现。这样，开发人员可以在不修改原有代码的基础上，扩展和替换现有的功能。SPI广泛应用于Java的核心库和许多第三方库中，以支持可插拔的组件和服务。

API（Application Programming Interface）是一种应用程序编程接口，它定义了一组用于与特定软件组件或服务进行交互的函数、方法和数据结构。API为开发人员提供了一种抽象层，使得他们可以在不了解底层实现细节的情况下，利用和构建复杂的软件系统。API可以是一种语言绑定的库（例如Java或Python的API），也可以是一种通用的网络协议（例如RESTful API或GraphQL API）。

总结一下，SPI和API之间的主要区别如下：

目的：SPI主要用于实现可插拔的服务和组件，使得软件模块在运行时可以动态选择和加载不同的实现。API主要用于提供一种与特定软件组件或服务进行交互的抽象层，简化开发过程。

使用者：SPI通常由服务提供者（如库或框架的开发者）实现，以提供特定功能的多种实现。API通常由应用开发者使用，以便与软件组件或服务进行交互。

动态性：SPI支持在运行时动态加载和选择实现，使得软件模块可以灵活地扩展和替换功能。API通常在编译时就确定了与特定组件或服务的交互方式，虽然某些API（如网络API）也可以在运行时动态地调用和更新。

尽管SPI和API在目的和使用方式上有所不同，但它们都是软件开发中的重要概念，有助于实现模块化、可扩展和易于维护的软件系统。

原文链接：https://blog.csdn.net/weixin_72084017/article/details/130170990
# [Java 中 SPI 机制](https://zhuanlan.zhihu.com/p/84337883)


# [springboot核心基础之spring.factories机制](https://zhuanlan.zhihu.com/p/444331676)