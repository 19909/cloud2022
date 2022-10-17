package com.wei.springcloudAlibaba.service.impl;


import com.wei.springcloud.entities.Payment;

import com.wei.springcloudAlibaba.dao.PaymentDao;
import com.wei.springcloudAlibaba.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment){
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
