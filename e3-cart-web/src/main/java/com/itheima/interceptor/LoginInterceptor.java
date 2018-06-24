package com.itheima.interceptor;

import com.itheima.jedis.JedisClient;
import com.itheima.service.LoginService;
import com.itheima.utils.CookieUtils;
import com.itheima.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 前处理，执行handler之前执行此方法。
        //检查用户是否登录，已登录的话将用户信息设置进request域中
        String token = CookieUtils.getCookieValue(request, "token");
        if(StringUtils.isEmpty(token)) return true;
        E3Result e3Result = loginService.checkLogin(token);
        if(e3Result.getStatus()==200){
            request.setAttribute("user",e3Result.getData());
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //handler执行之后，返回ModeAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //完成处理，返回ModelAndView之后。
        //可以再此处理异常
    }
}
