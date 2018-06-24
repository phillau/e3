package com.itheima.controller;

import com.itheima.service.LoginService;
import com.itheima.utils.CookieUtils;
import com.itheima.utils.E3Result;
import com.itheima.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/page/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/user/login")
    @ResponseBody
    public E3Result toLogin(String username, String password, HttpServletRequest request, HttpServletResponse response){
        E3Result e3Result = loginService.login(username, password);
        if(e3Result.getStatus()==200){
            CookieUtils.setCookie(request,response,"token",e3Result.getData().toString());
            return e3Result;
        }
        return e3Result;
    }

//    @ResponseBody
//    @RequestMapping("/user/token/{ticket}")
//    public String checkLogin(@PathVariable("ticket")String ticket,String callback){
//        E3Result e3Result = loginService.checkLogin(ticket);
//        if(!StringUtils.isEmpty(callback)){
//            String result = callback + "(" + JsonUtils.objectToJson(e3Result) + ")";
//            return result;
//        }
//        return JsonUtils.objectToJson(e3Result);
//    }

    @RequestMapping(value="/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        E3Result result = loginService.checkLogin(token);
        //响应结果之前，判断是否为jsonp请求
        if (!StringUtils.isEmpty(callback)) {
            //把结果封装成一个js语句响应
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
