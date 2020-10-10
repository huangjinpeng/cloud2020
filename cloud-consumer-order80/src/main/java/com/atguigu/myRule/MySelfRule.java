package com.atguigu.myRule;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule(){
        /*会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，
        然后选择一个并发量最小的服务*/
        return new BestAvailableRule();//定义为最优访问
    }
}
