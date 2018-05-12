package com.sinosoft.search;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;

public class ActiveMqConsumerTest {
    @Test
    public void testQueueConsumer() throws IOException {
        new ClassPathXmlApplicationContext("spring/applicationContext-activemq.xml");
        System.in.read();
    }
}
