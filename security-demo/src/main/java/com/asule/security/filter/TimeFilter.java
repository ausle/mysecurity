package com.asule.security.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

//@Component
public class TimeFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init start");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = new Date().getTime();
        System.out.println("filter doFilter start");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("filter doFilter end");
        System.out.println("time filter 耗时:"+ (new Date().getTime() - start));
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy start");
    }
}
