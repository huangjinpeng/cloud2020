package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements OrderHystrixService {
    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "-----paymentFallbackService fall back-paymentInfo_TimeOut,/(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_Ok(Integer id) {
    return "-----paymentFallbackService fall back-paymentInfo_Ok,/(ㄒoㄒ)/~~";

    }
}
