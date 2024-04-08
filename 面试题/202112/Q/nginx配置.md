1. 下载安装
2. 配置
3. 方向代理
4. ip限制，限制流量，黑白名单
5. 跨域访问



apache 同步多进程，一个链接一个进程 阻塞型

nginx 异步，多个链接一个进程 异步非阻塞



cgi 通用网关接口

fastCgi 常住cgi

php-cgi是php 实现自带的Fast-cgi管理器

fpm是PHP进程管理器，master ，worker 两种进程

master 负责监听，web server的请求

worker进程有多个，PHP解释器



https://blog.csdn.net/Rodgexue/article/details/79976396