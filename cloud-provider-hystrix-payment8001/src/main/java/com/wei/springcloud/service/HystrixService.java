package com.wei.springcloud.service;


public interface HystrixService {
    public String paymentInfo_OK(Integer id);
    public String paymentInfo_Timeout(Integer id);
    public String paymentCircuitBreaker(Integer id);
}
