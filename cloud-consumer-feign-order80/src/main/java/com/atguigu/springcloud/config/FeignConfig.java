package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



//配置Feign的接口调用日志级别
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignloggerLevel(){
        return  Logger.Level.FULL;
    }
}
