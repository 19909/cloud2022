package com.wei.springcloud.alibaba.service;


import com.wei.springcloud.alibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "seata-storage-service")
@Component
public interface StorageService {

    @GetMapping("/consumer/upDataStorage")
    public CommonResult upDataStorage();
}
