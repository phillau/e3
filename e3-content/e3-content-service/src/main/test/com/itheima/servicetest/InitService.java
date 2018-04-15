package com.itheima.servicetest;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitService {
    @Test
    public void initServ() throws InterruptedException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        while (true){
            Thread.sleep(1000);
        }
    }
}
