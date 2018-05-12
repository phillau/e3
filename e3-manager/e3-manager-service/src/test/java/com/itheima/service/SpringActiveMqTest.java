package com.itheima.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class SpringActiveMqTest {
    @Test
    public void testSpringActiveMq() throws Exception {
        //初始化spring容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        JmsTemplate jmsTemplate = (JmsTemplate)context.getBean("jmsTemplate");
        Queue destination = (Queue )context.getBean("queueDestination");
        jmsTemplate.send(destination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("spring activemq...1");
                return textMessage;
            }
        });
    }
}
