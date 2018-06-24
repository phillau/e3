package com.itheima.service;

import com.itheima.utils.E3Result;

public interface LoginService {
    E3Result login(String username, String password);

    E3Result checkLogin(String ticket);
}
