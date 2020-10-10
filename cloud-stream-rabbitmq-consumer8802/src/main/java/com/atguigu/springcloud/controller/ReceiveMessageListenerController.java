package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
@EnableBinding(Sink.class)//指定信道channel和Exchange绑定
public class ReceiveMessageListenerController {
    @Value("${server.port}")
    private String serverPort;
    @StreamListener(Sink.INPUT)//监听队列用于消费者接收消息
    public void input(Message<String>message){
        //接收Message 使用getPayload
        System.out.println("消费者1号"+message.getPayload()+"\t port"+serverPort);
    }
}
