package com.dongao.cn.remoteservice.impl;

import com.dongao.cn.service.TestRemoteService;
import com.dongao.cn.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiabing
 * @Package com.dongao.cn.remoteservice.impl
 * @Description: ${todo}
 * @date 2018/12/6 17:02
 */
@Service
public class TestRemoteServiceImpl implements TestRemoteService {

    @Autowired
    private TestService testService;

    @Override
    public String getName() {
        return testService.getName();
    }
}