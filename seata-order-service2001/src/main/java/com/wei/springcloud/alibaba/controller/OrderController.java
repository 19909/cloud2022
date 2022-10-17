package com.wei.springcloud.alibaba.controller;

import com.wei.springcloud.alibaba.domain.CommonResult;
import com.wei.springcloud.alibaba.domain.Order;
import com.wei.springcloud.alibaba.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/consumer/createOrder")
    public CommonResult<Order> createOrder(){
        long userId = 1;    //用户id
        long productId = 1; //商品id
        Order order = new Order(null,userId,productId,2,new BigDecimal(200),0);
        orderService.create(order);
        String message = "创建订单成功！";
        return new CommonResult<Order>(200,message);
    }
}
