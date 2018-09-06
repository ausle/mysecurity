package com.asule.security.handler;

import com.asule.security.LoginType;
import com.asule.security.entity.ServerResponse;
import com.asule.security.properties.BaseProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AsuleAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BaseProperties baseProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(LoginType.JSON==baseProperties.getBrowser().getLoginType()){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ServerResponse.createErrorByMsg(exception.getMessage())));
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
