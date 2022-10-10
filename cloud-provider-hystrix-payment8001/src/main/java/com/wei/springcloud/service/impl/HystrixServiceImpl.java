package com.wei.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wei.springcloud.service.HystrixService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class HystrixServiceImpl implements HystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池：  " +Thread.currentThread().getName()+"paymentInfo_OK, id:"+id+"\t";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            //设置调用该方法的时间峰值（超时时间）
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    @Override
    public String paymentInfo_Timeout(Integer id) {

        /**
         * 这里故意制造两个异常情况，一个是超时，一个是数学计算异常
         *  可以看到只要当前方法不可用了，就会调用我们兜底的方法来处理。
         *    也就是fallbackMethod = "paymentInfo_TimeoutHandler"中的方法名的方法来处理。从而实现服务的降级。
         *
         *  异常处理；推荐在客户端使用，服务的提供者只需要提供服务就可以了。
         */
//        int age = 10/0;
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：  " +Thread.currentThread().getName()+"   paymentInfo_Timeout, id:"+id+"\t";
    }
    public String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池：  " +Thread.currentThread().getName()+"   paymentInfo_Timeout, id:"+id+"\t" + "当前服务出错了，请稍后重试";
    }


    //-----------------服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallBack",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),  //请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),    //时间窗口期
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")     //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id<0){
            throw new RuntimeException("*******id  不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功,流水号 : " + serialNumber;
    }
    public String paymentCircuitBreaker_fallBack(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后重试，---- --id : " + id;
    }
}
