package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


@Component
@EnableBinding(Sink.class)
public class ReceiveMessageListenerController {
    @Value("${server.port}")
    private String serverPort;
   @StreamListener(Sink.INPUT)
   public void input(Message<String> message){
       //接收Message 使用getPayload
       System.out.println("消费者2号"+message.getPayload()+"\t port"+serverPort);
   }
}
