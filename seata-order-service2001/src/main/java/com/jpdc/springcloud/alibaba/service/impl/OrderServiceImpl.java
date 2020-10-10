package com.jpdc.springcloud.alibaba.service.impl;

import com.jpdc.springcloud.alibaba.dao.OrderDao;
import com.jpdc.springcloud.alibaba.domain.Order;
import com.jpdc.springcloud.alibaba.service.AccountService;
import com.jpdc.springcloud.alibaba.service.OrderService;
import com.jpdc.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;
    @Override
    //任何异常都回滚
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class) //全局事务控制
    public void create(Order order) {
        //新建订单
      log.info("---->开始新建订单");
      orderDao.create(order);
      log.info("---->订单微服务开始调用库存，做扣减Count");
      //扣减库存
   storageService.decrease(order.getProductId(),order.getCount());
        log.info("---->订单微服务开始调用库存，做扣减end");
        //扣减账户
        log.info("---->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("---->订单微服务开始调用账户，做扣减end");
        //4.修改订单状态从0到1
        log.info("---->修改订单状态");
        orderDao.update(order.getUserId(),0);
        log.info("---->修改订单状态end");
        log.info("---->下订单结束了，O(∩_∩)O");
    }
}
