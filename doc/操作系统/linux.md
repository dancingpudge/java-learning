linux 入门总结

蠢问题

目录，文件，文本，打包，压缩，用户，用户组，权限，备份，日志，安装，启动

聪明问题

深度剖析Linux硬链接和软链接，直击它们的本质！

9.Linux如何查看CPU运行状态？
10.Linux如何查看内存的使用情况？
11.Linux如何查看硬盘的读写性能？

更好的问题

2014年，Martin Fowler提出了微服务的概念，物理机、虚拟机、容器，轻量和高效

用上了docker，却还存在这些问题？







linux 基础面试

基本上都是一些面试常问到的题目

1、说一些你比较常用linux指令

　　1.1、ls/ll、cd、mkdir、rm-rf、cp、mv、ps -ef | grep xxx、kill、free-m、tar -xvf file.tar、（说那么十几二十来个估计差不多了）cd ls mkdir touch cp rm mv vi vim pwd find sed grep head echo tail tree cat tar zip chmod Chown useradd usermod userdel groupadd groupmod groupdel passwd clear alias unalias
nl ln yum rpm ifconfig su id ifup ifdown wget setup make ./configure fdisk mount df rz date man

2、查看进程（例：如何查看所有xx进程）

　　2.1、ps -ef | grep xxx

　　2.2、ps -aux | grep xxx（-aux显示所有状态）

3、杀掉进程

　　3.1、kill  -9[PID]    ---(PID用查看进程的方式查找)

4、启动/停止服务

　　4.1、cd到bin目录cd/

　　4.2、./startup.sh   --打开（先确保有足够的权限）

　　4.3、./shutdown.sh  ---关闭

5、查看日志

　　5.1、cd到服务器的logs目录（里面有xx.out文件）

　　5.2、tail -f xx.out  --此时屏幕上实时更新日志。ctr+c停止

　　5.3、查看最后100行日志 tail -100 xx.out 

　　5.4、查看关键字附件的日志。如：cat filename | grep -C 5 '关键字'（关键字前后五行。B表示前，A表示后，C表示前后） ----使用不多

　　5.5、还有vi查询啥的。用的也不多。

6、查看端口：（如查看某个端口是否被占用）

　　6.1、netstat -anp | grep 端口号（状态为LISTEN表示被占用）

7、查找文件

　　7.1、查找大小超过xx的文件： find . -type f -size +xxk  -----(find . -type f -mtime -1 -size +100k -size-400k)--查区间大小的文件

　　7.2、通过文件名：find / -name xxxx    ---整个硬盘查找

　　其余的基本上不常用

8、vim（vi）编辑器　　

　　有命令模式、输入模式、末行模式三种模式。
　　命令模式：查找内容(/abc、跳转到指定行(20gg)、跳转到尾行(G)、跳转到首行(gg)、删除行(dd)、插入行(o)、复制粘贴(yy,p)
　　输入模式：编辑文件内容
　　末行模式：保存退出(wq)、强制退出(q!)、显示文件行号(set number)
　　在命令模式下，输入a或i即可切换到输入模式，输入冒号(:)即可切换到末行模式；在输入模式和末行模式下，按esc键切换到命令模式
　 

> - 知识，技能，能力，选择，运气，出生



## h-l-cqbl

Shell

### ◆文件目录

ls list

cd change directory

pwd print work directory

diff different 

### ◆文件查找

whereis

which

find

### ◆目录属性

chmod -R 777

### ◆打包上传下载



### ◆磁盘

df

```
df -h 磁盘使用，挂载
df -hl 磁盘剩余
```

du

### ◆系统工具

reboot

poweroff

shutdown 

```
shotdown -h now
showdown -h 10
showdown -h 10:00
showdown -c
showdown -r now
```

wget 

cat

env 

hostname

who

free

date

ps

top

### ◆性能监控&优化命令

uname

```
uname -a 内核 os cpu
uname -r 内核
uname -m 处理器
```

top

ps

lsof

### ◆用户CRUD

useradd  xx

userdel -r xx

groups xx

su - xxx

passwd xx

w

### ◆ **网络和进程管理**

ifconfig

route -n

netstat

```
netstat -lntp
netstat -antp
netstat -lutp
```

ps -ef

kill -s  pid

kill -s name

top

iostat

### ◆ **常见系统服务命令**



### 其他

ln

crontab

### vi/vim



### git 

冲突

分支

### svn 



### docker 

