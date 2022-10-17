package com.wei.springcloud.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageDao {

    void upData(@Param("productId") Integer productId,@Param("used") int used);
}
