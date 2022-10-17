package com.wei.springcloud.alibaba.service.impl;

import com.wei.springcloud.alibaba.dao.OrderDao;
import com.wei.springcloud.alibaba.domain.Order;
import com.wei.springcloud.alibaba.service.AccountService;
import com.wei.springcloud.alibaba.service.OrderService;
import com.wei.springcloud.alibaba.service.StorageService;
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

    /**
     * rollbackFor参数表示出现什么异常都回滚事务
     * @param order
     */
    @Override
    @GlobalTransactional(name = "fps-create-order",rollbackFor = {Exception.class})    //全局事务回滚注解
    public void create(Order order) {
        log.info("---->开始新建订单");
        orderDao.create(order);

        log.info("订单微服务开始调用库存,做扣减Count");
        storageService.upDataStorage();
        log.info("订单微服务开始调用库存,做扣减end");

        log.info("订单微服务开始调用账户,做扣减Money");
        accountService.upDataAccount();
        log.info("订单微服务开始调用账户,做扣减end");

        log.info("修改订单状态开始");
        orderDao.upData(order.getUserId(),1);
        log.info("修改订单状态结束");

        log.info("---->>下单结束了");
    }
}
