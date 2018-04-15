package com.itheima.servicetest;

import com.itheima.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class InitService {
    @Test
    public void initServ() throws InterruptedException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        while (true){
            Thread.sleep(1000);
        }
    }

    @Test
    public void jedisTest(){
        Jedis jedis = new Jedis("192.168.203.153",6379);
        jedis.set("address1","beijing1");
        String address = jedis.get("address1");
        System.out.println(address);
        jedis.close();
    }

    @Test
    public void jedisPoolTest(){
        JedisPool jedisPool = new JedisPool("192.168.203.153",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("newaddress","shanghai");
        String newaddress = jedis.get("newaddress");
        System.out.println(newaddress);
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void jedisClusterTest(){
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.203.153",7001));
        nodes.add(new HostAndPort("192.168.203.153",7002));
        nodes.add(new HostAndPort("192.168.203.153",7003));
        nodes.add(new HostAndPort("192.168.203.153",7004));
        nodes.add(new HostAndPort("192.168.203.153",7005));
        nodes.add(new HostAndPort("192.168.203.153",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("company","sinosoft");
        String company = jedisCluster.get("company");
        System.out.println(company);
        jedisCluster.close();
    }

    @Test
    public void springJedisTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = classPathXmlApplicationContext.getBean(JedisClient.class);
        jedisClient.set("abcd","123");
        String adc = jedisClient.get("abcd");
        System.out.println(adc);
    }
}
