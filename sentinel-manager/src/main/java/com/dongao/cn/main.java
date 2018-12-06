package com.dongao.cn;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author jiabing
 * @Package com.dongao.cn
 * @Description: ${todo}
 * @date 2018/12/6 17:11
 */
public class main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext(
                new String[] {"applicationContext.xml"}
        );
        context.start();
        System.out.println("提供服务已经注册成功");
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}