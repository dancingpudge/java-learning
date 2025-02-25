package com.shark.demo.rabbit;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.InetAddress;

/**
 * @author: LiuH
 * @date: 2024/4/26 16:33
 */
public class SubscribeClient {
    private static final String hostUrl = "tcp://{domain}:{port}";
    private static final String MQTT_TOPIC = "topic/#";
    private static final String username = "username";
    private static final String password = "password";




    /**
     * 客户端连接服务端
     */
    public static void connect() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            // 创建MQTT客户端对象
            MqttClient client = new MqttClient(hostUrl, username, new MemoryPersistence());
            // 连接设置
            MqttConnectOptions options = new MqttConnectOptions();
            // 是否清空session，设置为false表示服务器会保留客户端的连接记录，客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
            // 设置为true表示每次连接到服务端都是以新的身份
            options.setCleanSession(true);
            // 自动重连
            options.setAutomaticReconnect(true);
            // 设置连接用户名
            options.setUserName(username);
            // 设置连接密码
            options.setPassword(password.toCharArray());
            // 设置超时时间，单位为秒
            options.setConnectionTimeout(100);
            // 设置心跳时间 单位为秒，表示服务器每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
            options.setKeepAliveInterval(20);
            // 设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
            options.setWill("willTopic", (username + "与服务器断开连接").getBytes(), 0, false);
            // 设置回调
            client.setCallback(new MqttCallbackExtended() {
                @SneakyThrows
                @Override
                public void connectComplete(boolean b, String s) {
                    System.out.println(("连接成功"));
                    // 订阅主题
                    // 消息等级，和主题数组一一对应，服务端将按照指定等级给订阅了主题的客户端推送消息
                    int[] qos = {0};
                    // 主题
                    String[] topics = {MQTT_TOPIC};
                    // 订阅主题
                    client.subscribe(topics, qos);
                    System.out.println(("订阅主题完毕"));
                }

                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("与服务器断开连接");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    try {
                        System.out.println(String.format("接收消息主题 : %s", topic));
                        // event
                        String event = topic.substring(topic.lastIndexOf("/") + 1);
                        // 消息
                        String payload = new String(message.getPayload());
                        System.out.println(String.format("接收消息内容解密前 : %s", payload));
                        // 消息序列化
                        JSONObject jsonObject = JSONObject.parseObject(payload);
                        // 处理
                        System.out.println(jsonObject.toJSONString());
                    } catch (Exception e) {
                        System.out.println("messageArrivedError" + e.getMessage());
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            client.connect(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
