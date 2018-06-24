package com.itheima.service;

import com.itheima.jedis.JedisClient;
import com.itheima.mapper.TbUserMapper;
import com.itheima.pojo.TbUser;
import com.itheima.utils.E3Result;
import com.itheima.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public E3Result login(String username, String password) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        TbUser user = tbUserMapper.findTbUserByUsernameAndPassword(username, password);
        if(user!=null){
            // 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
            String token = UUID.randomUUID().toString();
            jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
            jedisClient.expire("SESSION:"+token,1800);
            return E3Result.ok(token);
        }
        return E3Result.build(201,"用户名或密码错误");
    }

    @Override
    public E3Result checkLogin(String ticket) {
        String s = jedisClient.get("SESSION:"+ticket);
        if(!StringUtils.isEmpty(s)){
            TbUser user = JsonUtils.jsonToPojo(s,TbUser.class);
            return E3Result.ok(user);
        }
        return E3Result.build(201,"未登录");
    }
}
