package com.itheima.mapper;

import com.itheima.pojo.TbUser;

public interface TbUserMapper {
    TbUser findTbUserByUsername(String username);
    TbUser findTbUserByPhone(String phone);
    void addUser(TbUser tbUser);
    TbUser findTbUserByUsernameAndPassword(String username, String password);
}
