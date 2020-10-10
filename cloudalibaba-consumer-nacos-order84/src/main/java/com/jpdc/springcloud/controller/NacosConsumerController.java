package com.jpdc.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.jpdc.springcloud.service.PaymentFallbackService;
import com.jpdc.springcloud.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class NacosConsumerController {
    public static final String SERVICE_URL="http://nacos-payment-provider";
    @Resource
    private RestTemplate restTemplate;
    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback",fallback = "fallbackMethod")
    //fallback：java异常 blockHandler:控制台违规
    @SentinelResource(value = "fallback",blockHandler = "blockHandler",fallback ="fallbackMethod"//从兜底方法中排除异常该异常仍然报错
            )
    public CommonResult<Payment> fallback(@PathVariable Long id){
        CommonResult result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id==4){
            throw new IllegalArgumentException(" IllegalArgumentException,非法参数异常");
        }else if (result.getData()==null){
            throw new NullPointerException("NullPointerException,没有该id记录空指针异常");
        }
        return result;
    }
    //本例是控制台配置违规
    public CommonResult<Payment> blockHandler(@PathVariable Long id, BlockException e){
        return new CommonResult(445,"blockException：控制台配置违规"+e.getMessage(),new Payment());
    }
    //运行异常：兜底方法fallback
    public CommonResult fallbackMethod(@PathVariable Long id,Throwable e){
        return new CommonResult(444,"兜底异常，内容："+e.getMessage(),new Payment());
    }
    //==========OpenFeign=====
    @Resource
    private PaymentService paymentService;
    @GetMapping(value = "/consumer/paymentSQL/{id}")
    @SentinelResource(value = "paymentSQL",fallback = "fallbackMethod")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){

        CommonResult<Payment> result = paymentService.paymentSQL(id);
        if (result.getData()==null){
            throw new NullPointerException("NullPointerException,没有该id记录空指针异常");
        }
        return result;

    }
}
