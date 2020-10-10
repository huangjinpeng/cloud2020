package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymenyServiceImpl  implements PaymentService {
    @Override
    //正常访问方法
    public String paymentInfo_Ok(Integer id) {
        return "线程池"+Thread.currentThread().getName()+"payment_Ok,id:"+id+"\t"+"O(∩_∩)O哈哈~";
    }

//指定兜底方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            //规定当前线程的访问限制为3s内
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {

        //int age=10/0;
        try {
            TimeUnit.MILLISECONDS.sleep(3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池"+Thread.currentThread().getName()+"payment_TimeOut,id:"+id+"\t"+"O(∩_∩)O不哈哈~耗时"+"s";

    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池"+Thread.currentThread().getName()+"系统繁忙或者运行报错，请稍后再试,id:"+id+"\t"+"/(ㄒoㄒ)/~~";
    }
    //============================服务熔断========================
    /*开启断路器，且在10s内10次请求中有60%的失败率就熔断服务*/
  @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
          @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
  })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id<0){
            throw new RuntimeException("***id不能为负数");
        }
        //UUID.random.toString
        String serialNumber= IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号"+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
     return "id不能为负数，请稍后再试，/(ㄒoㄒ)/~~ id:"+id;
    }
}
