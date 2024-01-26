# 学习Swoole需要掌握哪些基础知识

一句话总结，先熟悉《linux高性能服务器编程》一书内容



## 多进程/多线程

- 了解`Linux`操作系统进程和线程的概念
- 了解`Linux`进程/线程切换调度的基本知识
- 了解进程间通信的基本知识，如管道、`UnixSocket`、消息队列、共享内存

https://www.cnblogs.com/linuxAndMcu/p/11064916.html



## SOCKET

- 了解`SOCKET`的基本操作如`accept/connect`、`send/recv`、`close`、`listen`、`bind`
- 了解`SOCKET`的接收缓存区、发送缓存区、阻塞/非阻塞、超时等概念

## IO复用

- 了解`select`/`poll`/`epoll`
- 了解基于`select`/`epoll`实现的事件循环，`Reactor`模型
- 了解可读事件、可写事件

## TCP/IP网络协议

- 了解`TCP/IP`协议
- 了解`TCP`、`UDP`传输协议

## 调试工具

- 使用 [gdb](https://wiki.swoole.com/wiki/page/p-gdb.html) 调试`Linux`程序
- 使用 [strace](https://wiki.swoole.com/wiki/page/p-strace.html) 跟踪进程的系统调用
- 使用 [tcpdump](https://wiki.swoole.com/wiki/page/p-tcpdump.html) 跟踪网络通信过程
- 其他`Linux`系统工具，如ps、[lsof](https://wiki.swoole.com/wiki/page/p-lsof.html)、top、vmstat、netstat、sar、ss等