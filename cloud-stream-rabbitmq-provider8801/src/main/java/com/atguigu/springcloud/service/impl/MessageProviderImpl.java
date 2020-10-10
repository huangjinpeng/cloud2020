package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

//定义消息的推送管道
@EnableBinding(Source.class)//指定信道channel和Exchange绑定
public class MessageProviderImpl implements IMessageProvider {
    @Resource
    private MessageChannel output;//消息发送管道
    @Override
    public String send() {
        String serial= UUID.randomUUID().toString();
        //通过管道构建消息发送
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("*****serial"+serial);
        return null;
    }
}
