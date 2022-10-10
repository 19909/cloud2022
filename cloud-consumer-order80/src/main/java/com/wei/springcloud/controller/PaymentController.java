package com.wei.springcloud.controller;

import com.wei.springcloud.entities.CommonResult;
import com.wei.springcloud.entities.Payment;
import com.wei.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class PaymentController {

    public static final String PAYMENT_PATH = "http://CLOUD-PAYMENT-SERVICE";

    //远程调用微服务
    @Resource
    private RestTemplate restTemplate;

    //自定义的负载均衡
    @Resource
    private LoadBalancer loadBalancer;

    //服务发现
    @Resource
    private DiscoveryClient discoveryClient;


    //这里是POST请求，生产者是GET请求，这里插入就不演示了。
    @GetMapping("/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_PATH+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_PATH+"/payment/get/"+id,CommonResult.class);
    }

    @RequestMapping("/payment/lb")
    public String getPaymentLb(){

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null && instances.size() <= 0){
            return null;
        }else {
            ServiceInstance serviceInstance = loadBalancer.instance(instances);
            return restTemplate.getForObject(serviceInstance.getUri()+"/payment/lb",String.class);
        }
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin",String.class);
    }

}
