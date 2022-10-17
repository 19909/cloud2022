package com.wei.springcloud.alibaba.controller;

import com.wei.springcloud.alibaba.domain.CommonResult;
import com.wei.springcloud.alibaba.domain.Storage;
import com.wei.springcloud.alibaba.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    @GetMapping("/consumer/upDataStorage")
    public CommonResult upDataStorage(){
        Integer productId = 1; //商品id
        int used = 2;
        storageService.upData(productId,used);
        String message = "减库存成功！";
        return new CommonResult(200,message);
    }
}
