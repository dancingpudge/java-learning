package com.shark.demo.bus;

import com.shark.demo.rabbit.SubscribeClient;

/**
 * @description: TODO
 * @author: LiuH
 * @date: 2025/2/25 22:03
 */
public class SubscribeClientTest {
    public static void main(String[] args) throws MqttException, InterruptedException {
        SubscribeClient.connect();
    }
}
