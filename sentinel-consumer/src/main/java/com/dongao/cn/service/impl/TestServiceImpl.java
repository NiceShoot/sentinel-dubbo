package com.dongao.cn.service.impl;

import com.dongao.cn.service.TestRemoteService;
import com.dongao.cn.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiabing
 * @Package com.dongao.cn.service.impl
 * @Description: ${todo}
 * @date 2018/12/6 16:29
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRemoteService testRemoteService;

    @Override
    public String getName() {
        return testRemoteService.getName();
    }
}