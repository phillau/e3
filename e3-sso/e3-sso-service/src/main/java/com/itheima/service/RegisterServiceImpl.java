package com.itheima.service;

import com.itheima.mapper.TbUserMapper;
import com.itheima.pojo.TbUser;
import com.itheima.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public E3Result checkParam(String param, Integer type) {
        TbUser tbUser = null;
        switch (type){
            case 1:
                tbUser = tbUserMapper.findTbUserByUsername(param);
                if(tbUser!=null) return E3Result.build(100,"用户名已存在");
            case 2:
                tbUser = tbUserMapper.findTbUserByPhone(param);
                if(tbUser!=null) return E3Result.build(100,"手机号已注册");
        }
        return E3Result.ok(true);
    }

    @Override
    public void addUser(TbUser tbUser) {
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        tbUserMapper.addUser(tbUser);
    }
}
