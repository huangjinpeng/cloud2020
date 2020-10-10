package com.jpdc.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.jpdc.springcloud.controller.myhandler.CustomerBlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

//流控Controller
@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA(){
        try {
            //休眠0.8s
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  "------testA";
    }
    @GetMapping("/testB")
    public String testB(){
        log.info(Thread.currentThread().getName()+"\t"+"testB");
        return  "------testB";
    }
    @GetMapping("/testD")
    public String testD(){
//        try {
//            //休眠1s
//            TimeUnit.MILLISECONDS.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("testD异常比例");
        int age=10/0;
        System.out.println("123456");
        return  "------testD";
}
@GetMapping("/testE")
    public String testE(){
        log.info("testE测试异常数");
        int a=10/0;
        return "-----testE 测试异常数";
}
@GetMapping("/testHotKey")
//设置规则名称以及兜底方法
@SentinelResource(value = "testHoyKey",blockHandler = "deal_testHotKey")
public String testHotkey(@RequestParam(value = "p1",required = false)String p1,
                         @RequestParam(value = "p2",required = false)String p2){
        int age=10/0;
    return "-----testHotKey";
}
public String deal_testHotKey(String p1, String P2, BlockException e){
    return "-----testHotKey /(ㄒoㄒ)/~~";
}
@GetMapping("/rateLimit/byUrl")
@SentinelResource(value ="byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按url限流测试ok",new Payment(2020L,"serial002"));
}
//CustomerBlockHandler
@GetMapping("/rateLimit/CustomerBlockHandler")
@SentinelResource(value ="CustomerBlockHandler",
        blockHandlerClass = CustomerBlockHandler.class,
        blockHandler = "handlerException2")
public CommonResult CustomerBlockHandler(){
    return new CommonResult(200,"按客户自定义",new Payment(2020L,"serial003"));
}
}
