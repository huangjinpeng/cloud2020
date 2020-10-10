package com.aiguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ConsulController {
    @Resource
    private RestTemplate restTemplate;
    private static final String serverName="http://consul-provider-payment";
    @GetMapping(value = "consumer/payment/consul/{name}")
    public String showConsul(@PathVariable(value = "name") String name){
      String str=  restTemplate.getForObject(serverName+"/payment/consul/"+name,String.class);
    return str;
    }
}
