package com.dongao.cn.controller;

import com.dongao.cn.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jiabing
 * @Package com.dongao.cn.controller
 * @Description: ${todo}
 * @date 2018/12/6 16:28
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;



    @RequestMapping(value = {"/test"})
    @ResponseBody
    public String index(){
        return testService.getName();
    }

}