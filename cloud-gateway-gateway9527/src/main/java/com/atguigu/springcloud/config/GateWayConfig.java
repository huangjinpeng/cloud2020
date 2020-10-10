package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//编码方式配置路由
@Configuration
public class GateWayConfig {
    @Bean
    //当访问地址为http://localhost:9527/guonei会自动转发到http://news.baidu.com/guonei
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_atguigu",
                r ->r.path("/guonei").
                        uri("http://news.baidu.com/guonei")).build();
        return  routes.build();

    }
    @Bean
    //当访问地址为http://localhost:9527/guonei会自动转发到http://news.baidu.com/guonei
    public RouteLocator customRouteLocator2(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_atguigu2",
                r ->r.path("/guoji").
                        uri("http://news.baidu.com/mil")).build();
        return  routes.build();

    }
}
