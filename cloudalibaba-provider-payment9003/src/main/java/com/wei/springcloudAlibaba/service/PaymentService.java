package com.wei.springcloudAlibaba.service;

import com.wei.springcloud.entities.Payment;

public interface PaymentService {
    //写
    public int create(Payment payment);
    //读
    public Payment getPaymentById(Long id);
}
