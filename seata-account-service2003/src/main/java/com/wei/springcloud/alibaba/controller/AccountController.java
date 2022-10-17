package com.wei.springcloud.alibaba.controller;

import com.wei.springcloud.alibaba.domain.CommonResult;
import com.wei.springcloud.alibaba.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AccountController {

    @Resource
    private AccountService storageService;

    @GetMapping("/consumer/upDataAccount")
    public CommonResult upDataAccount(){
        Integer userId = 1; //商品id
        int used = 2;
        storageService.upData(userId,used);
        String message = "扣余额成功！";
        return new CommonResult(200,message);
    }
}
