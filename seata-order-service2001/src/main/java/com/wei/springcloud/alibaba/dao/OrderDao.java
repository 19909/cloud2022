package com.wei.springcloud.alibaba.dao;

import com.wei.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    //新建订单
    void create(Order order);
    //修改订单状态，从0改为1
    void upData(@Param("userId") Long userId,@Param("status") Integer status);
}
