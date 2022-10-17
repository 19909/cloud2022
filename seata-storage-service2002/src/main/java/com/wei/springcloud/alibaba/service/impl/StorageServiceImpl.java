package com.wei.springcloud.alibaba.service.impl;

import com.wei.springcloud.alibaba.dao.StorageDao;
import com.wei.springcloud.alibaba.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    @Override
    public void upData(Integer productId, int used) {
        storageDao.upData(productId,used);
    }
}
