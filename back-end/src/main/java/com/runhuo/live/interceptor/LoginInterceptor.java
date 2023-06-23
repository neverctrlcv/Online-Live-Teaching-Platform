package com.runhuo.live.interceptor;

import com.runhuo.live.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/27 23:27
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        System.out.println(token);
       if (JwtUtils.checkToken(token)||token==null||"".equals(token)){
           return false;
       }
       return true;
    }
}
