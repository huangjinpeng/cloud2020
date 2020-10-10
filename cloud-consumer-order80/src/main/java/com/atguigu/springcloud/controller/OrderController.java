package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {
    @Resource
private RestTemplate restTemplate;
    //单机模式
//private static  final  String PAYMENT_URL="http://localhost:8001";
//集群调用模(eureka上的服务注册名) 还需要在config中配置@LoadBalanced注解配置RestTemplate负载均衡的能力
    private static  final  String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        //远程调用
        return  restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/create2")
    public CommonResult<Payment> create2(Payment payment){
        //远程调用
        return  restTemplate.postForEntity(PAYMENT_URL+"/payment/create", payment,CommonResult.class).getBody();

    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(value = "id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get2/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable(value = "id") Long id){
        ResponseEntity<CommonResult> entity=restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    if (entity.getStatusCode().is2xxSuccessful()){
        log.info(entity.getStatusCode()+"\t"+entity.getHeaders());
        return entity.getBody();
    }else {
        return new CommonResult<>(444,"操作失败",null);
    }
    }
    // zipkin+sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
       return restTemplate.getForObject("http://CLOUD-PAYMENT-SERVICE"+"/payment/zipkin",String.class);

    }
}
