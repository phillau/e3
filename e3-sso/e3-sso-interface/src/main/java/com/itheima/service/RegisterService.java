package com.itheima.service;

import com.itheima.pojo.TbUser;
import com.itheima.utils.E3Result;

public interface RegisterService {
    E3Result checkParam(String param, Integer type);

    void addUser(TbUser tbUser);
}
