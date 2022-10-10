package com.wei.springcloud.controller;

import com.wei.springcloud.entities.CommonResult;
import com.wei.springcloud.entities.Payment;
import com.wei.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    //服务发现（具体的获取到eureka中的有哪些微服务，每个微服务中有哪些指定的属性）
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/create")
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        log.info("*** 插入结果:{}",result);
        if (result>0){
            return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,"影响的行数："+result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*** 查询的结果:{}",payment);
        if (payment != null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        }else {
            return new CommonResult(444,"没有对应的记录，查询id："+id,null);
        }
    }

    //服务发现
    @GetMapping("/payment/discovery")
    public Object discovery(){

        List<String> services = discoveryClient.getServices();//获取eureka注册中心中全部的服务。
        for (String service : services) {
            log.info("******service ：{}",service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");//获取指定微服务名称下有多少个服务。
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId()+"\t" + instance.getHost()+"\t"+instance.getUri()+"\t" + instance.getPort() +"\t" +instance.getServiceId()
                    +"\t"+instance.getScheme()+"\t"+instance.getMetadata()+"\t"+instance.getClass());
        }
        int order = discoveryClient.getOrder();
        log.info("****order : {}",order);

        return this.discoveryClient;
    }

    @RequestMapping("/payment/lb")
    public String getPayment(){
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "I am paymentZipkin server fall back , welcome to word!";
    }
}
