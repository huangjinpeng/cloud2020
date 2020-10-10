package com.aiguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class PamentController {
    @Value("${server.port}")
    private String serverPort;
    @RequestMapping("/payment/consul/{name}")
    public String paymentConsul(@PathVariable String name){
        return "springcloud with consul:"+serverPort+name+"\t"+ UUID.randomUUID().toString();
    }
}
