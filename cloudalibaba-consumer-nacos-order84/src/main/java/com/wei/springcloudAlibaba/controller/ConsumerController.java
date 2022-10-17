package com.wei.springcloudAlibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.wei.springcloud.entities.CommonResult;
import com.wei.springcloud.entities.Payment;
import com.wei.springcloudAlibaba.fallback.FallBack;
import com.wei.springcloudAlibaba.service.PaymentServiceFallBack;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Value("${service-uri.nacos-user-service}")
    private String service_url;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private PaymentServiceFallBack paymentServiceFallBack;

    @SentinelResource(value = "fallBack",
            fallbackClass = FallBack.class,
            fallback = "handlerException",
            blockHandler = "fallbackException")
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> fallBack(@PathVariable("id") Integer id){

        CommonResult result = restTemplate.getForObject(service_url + "/payment/get/" + id, CommonResult.class, id);

        if (id == 2){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常");
        }else if (result.getData() == null){
            throw new NullPointerException("NullPointerException,没有对应的id，空指针异常");
        }
        return result;
    }

    //限流规则回调
    public CommonResult<Payment> fallbackException(Integer id,Throwable throwable){
        return new CommonResult<Payment>(id,throwable.getMessage(),new Payment(4444,"error--serial"));
    }

    //Feign测试
//    @GetMapping("/payment/get/{id}")
//    public CommonResult getPaymentById(@PathVariable("id") Long id){
//        return paymentServiceFallBack.getPaymentById(id);
//    }
}
