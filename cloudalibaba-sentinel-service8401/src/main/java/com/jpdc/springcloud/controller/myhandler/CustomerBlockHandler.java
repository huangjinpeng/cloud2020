package com.jpdc.springcloud.controller.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException e){
        return new CommonResult(444,"按客户自定义,global",new Payment(2020L,"globalException-----1"));
    }
    public static CommonResult handlerException2(BlockException e){
        return new CommonResult(444,"按客户自定义,global",new Payment(2020L,"globalException-----2"));
    }
}
