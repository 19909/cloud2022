package com.wei.springcloud.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountDao {

    void upData(@Param("userId") Integer userId,@Param("used") int used);
}
