package com.wei.springcloudAlibaba.fallback;

import com.wei.springcloud.entities.CommonResult;
import com.wei.springcloud.entities.Payment;

public class FallBack {

    public static CommonResult<Payment> handlerException(Integer id,Throwable throwable){
        return new CommonResult<Payment>(id,throwable.getMessage(),new Payment(4444,"error ---- serial"));
    }
}
