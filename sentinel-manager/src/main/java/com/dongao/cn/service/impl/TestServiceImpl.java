package com.dongao.cn.service.impl;

import com.dongao.cn.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author jiabing
 * @Package com.dongao.cn.service.impl
 * @Description: ${todo}
 * @date 2018/12/6 17:01
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String getName() {
        return "dubbo success";
    }
}