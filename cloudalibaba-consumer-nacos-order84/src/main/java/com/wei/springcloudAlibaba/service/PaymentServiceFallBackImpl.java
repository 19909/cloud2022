package com.wei.springcloudAlibaba.service;

import com.wei.springcloud.entities.CommonResult;
import com.wei.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallBackImpl implements PaymentServiceFallBack {
    @Override
    public CommonResult getPaymentById(Long id) {
        String message = " 服务降级放回，----";
        return new CommonResult<Payment>(444,message,new Payment(id,"errorSerial"));
    }
}
