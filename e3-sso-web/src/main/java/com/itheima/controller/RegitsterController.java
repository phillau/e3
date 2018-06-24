package com.itheima.controller;

import com.itheima.pojo.TbUser;
import com.itheima.service.RegisterService;
import com.itheima.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册功能Controller
 */
@Controller
public class RegitsterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkParam(@PathVariable("param") String param,@PathVariable("type") Integer type){
        E3Result e3Result = registerService.checkParam(param, type);
        return e3Result;
    }

    @RequestMapping("/user/register")
    @ResponseBody
    public E3Result toRegister(TbUser tbUser){
        registerService.addUser(tbUser);
        return E3Result.build(200,"ok");
    }
}
