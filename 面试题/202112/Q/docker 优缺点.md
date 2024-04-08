1. 容器，轻量
2. docker file
3. 概念
4. 配置
5. 对比虚拟机
6. 心得



容器化平台，环境和程序依赖打包

docker 不是虚拟化，开销小，可以自动部署应用单进程的。

镜像可以创建容器

独立运行，包含程序需要的依赖

运行，暂停，重新启动，已经退出

DockerFile	编写镜像生成过程文件

FROM LABEL RUN CMD

COPY SRC 本地

pull push rm rmi  images ps

portainer 普题乐