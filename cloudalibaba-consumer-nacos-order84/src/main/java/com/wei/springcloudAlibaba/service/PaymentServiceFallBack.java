package com.wei.springcloudAlibaba.service;

import com.wei.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//@FeignClient(value = "nacos-payment-provider",fallback = PaymentServiceFallBackImpl.class)
public interface PaymentServiceFallBack {

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id);
}
