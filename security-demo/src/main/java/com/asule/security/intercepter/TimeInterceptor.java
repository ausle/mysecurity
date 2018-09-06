package com.asule.security.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TimeInterceptor implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("preHandle start");

        HandlerMethod method= (HandlerMethod) o;
        System.out.println(method.getMethod().getName());
        System.out.println(method.getBean().getClass().getName());

        //该返回值表示请求是否继续。如果为true则调用controller方法。
        // 否则请求结束。postHandle和afterCompletion都不会执行
        return true;
    }

//    controller方法调用之后执行。在进行视图渲染之前执行。
//    可以在这里对controller处理后的modelAndView进行进一步处理。
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle start");
    }


//    渲染视图之后执行。
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
    }
}
