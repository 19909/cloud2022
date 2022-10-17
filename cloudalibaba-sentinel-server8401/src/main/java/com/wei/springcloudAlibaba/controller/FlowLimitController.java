package com.wei.springcloudAlibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wei.springcloud.entities.CommonResult;
import com.wei.springcloud.entities.Payment;
import com.wei.springcloudAlibaba.back.ProviderFallBack;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {

    /**
     * SentinelResource注解：按照自定义的限流方式
     * blockHandlerClass属性：表示限流后处理的类，
     * blockHandler属性：表示限流后处理的方法，
     *
     *
     * blockHandler和fallback的区别：
     *  fallback：当前方法出现异常的回调处理方法
     *  blockHandler：当前限流模式出现异常的回调方法
     * @return
     */
    //@SentinelResource(value = "testA",blockHandlerClass = ConsumerBlockHandler.class,blockHandler = "handlerException",fallback = "handlerException")
    @GetMapping("/testA")
    public String testA(){
        return "-----testA";
    }

    @SentinelResource(value = "testB",fallbackClass = ProviderFallBack.class,fallback = "handlerException")
    @GetMapping("/testB")
    public String testB(){
        int i = 10/0;
        return "-----testB";
    }

    //将我们的sentinel中的限流规则持久化到nacos中
    @GetMapping("/rateLimit/byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按url限流测试OK",new Payment(2022,"serial2022"));
    }
}
