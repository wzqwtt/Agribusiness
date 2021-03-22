package com.wtt.agribusiness.order.interceptor;

import com.wtt.common.constant.AuthServerConstant;
import com.wtt.common.vo.MemberRespVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute != null) {
            //登陆了
            loginUser.set(attribute);
            return true;
        }else {
            //没登陆就去登陆
            request.getSession().setAttribute("msg","请先进行登陆");
            response.sendRedirect("http://auth.agribusiness.com/login.html");
            return false;
        }
    }
}
