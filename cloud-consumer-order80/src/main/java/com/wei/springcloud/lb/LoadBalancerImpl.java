package com.wei.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LoadBalancerImpl implements LoadBalancer{

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //得到需要访问微服务的集合下标
    public final int getAndIncrement(){
        int current;//微服务的下标 从0开始
        int next;   //活着的微服务的集合下标
        do {
            current = this.atomicInteger.get(); //获取当前活着的微服务的下标
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;  //Integer.MAX_VALUE = 2147483647
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*****next:" + next);
        return next;
    }

    //负载均衡算法：rest接口第几次请求数 % 服务器集群总数 = 实际调用服务器位置下标。（每次服务重启后rest接口计数从1开始）
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {

        int index = getAndIncrement() % serviceInstances.size();    //获取到当前服务的下标

        return serviceInstances.get(index); //返回指定下标的服务
    }
}
