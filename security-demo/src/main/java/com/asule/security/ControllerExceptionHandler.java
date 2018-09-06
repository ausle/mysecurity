package com.asule.security;


import com.asule.security.exception.UserNoExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNoExistException.class)
    public Map<String,Object> handleException(UserNoExistException ex){

        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("message",ex.getMessage());

        return map;
    }
}
