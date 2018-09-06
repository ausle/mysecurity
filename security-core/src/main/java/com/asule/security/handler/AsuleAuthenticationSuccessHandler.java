package com.asule.security.handler;

import com.asule.security.LoginType;
import com.asule.security.properties.BaseProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//处理页面验证成功后
@Component
public class AsuleAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BaseProperties baseProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//       请求如果是JSON格式，返回JSON格式的验证信息
        if(LoginType.JSON==baseProperties.getBrowser().getLoginType()){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else{
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
