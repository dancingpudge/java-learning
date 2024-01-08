#1.指定基础镜像，并且必须是第一条指令
FROM centos:7
#2.指明该镜像的作者和其电子邮件
MAINTAINER liuhui "415649679@qq.com"
#3.在构建镜像时，指定镜像的工作目录，之后的命令都是基于此工作目录，如果不存在，则会创建目录
WORKDIR /usr/local
#4.一个复制命令，把jdk安装文件复制到镜像中，语法：ADD <src>... <dest>,注意：jdk*.tar.gz使用的是相对路径
ADD jdk-8u311-linux-x64.tar.gz /usr/local/jdk
#5.配置环境变量
ENV JAVA_HOME=/usr/local/jdk/jdk1.8.0_281
ENV CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH=$JAVA_HOME/bin:$PATH


