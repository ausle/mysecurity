package com.asule.security.controller;


import com.asule.security.entity.ServerResponse;
import com.asule.security.SecurityCorePropertiesConfig;
import com.asule.security.properties.BaseProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private RequestCache requestCache=new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();


    @Autowired
    private BaseProperties baseProperties;


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping("/authentication/require")
    public ServerResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //将请求缓存到session中
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest!=null){
            String redirectUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(redirectUrl,".html")){
                //重定向的路径可配置。
                redirectStrategy.sendRedirect(request,response, baseProperties.getBrowser().getLoginPage());
            }
        }
        return ServerResponse.createErrorByMsg("非html页面，需要进行身份验证");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping("/authentication/form")
    public void form(HttpServletRequest request, HttpServletResponse response){

    }
}
