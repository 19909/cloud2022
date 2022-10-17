package com.wei.springcloud.alibaba.service;


import com.wei.springcloud.alibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "seata-account-service")
@Component
public interface AccountService {

    @GetMapping("/consumer/upDataAccount")
    public CommonResult upDataAccount();
}
