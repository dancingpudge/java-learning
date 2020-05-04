FROM registry.cn-hangzhou.aliyuncs.com/longbolife/openjdk:8-jdk-slim

RUN mkdir /home/wwwlogs

ENV TZ=Asia/Shanghai
ENV JAVA_APP_JAR study-1.0.0-SNAPSHOT.jar
ADD target/$JAVA_APP_JAR /deployments/

EXPOSE 8080


