package com.wei.springcloud.alibaba.service.impl;

import com.wei.springcloud.alibaba.dao.AccountDao;
import com.wei.springcloud.alibaba.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao storageDao;

    @Override
    public void upData(Integer userId, int used) {

//        模拟超时异常
        try { TimeUnit.SECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
        storageDao.upData(userId,used);
    }
}
