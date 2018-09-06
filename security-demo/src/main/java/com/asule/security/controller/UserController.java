package com.asule.security.controller;


import com.asule.security.exception.UserNoExistException;
import com.asule.security.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping(value = "/user")
@RestController
public class UserController {


//    UsernamePasswordAuthenticationFilter
//    BasicAuthenticationFilter
//      ExceptionTranslationFilter
//      FilterSecurityInterceptor



    //RequestBody直接将json字符串转化为POJO对象。映射请求体到java方法参数
    //在User对象上，可以对属性添加注解进行检验。但默认是不检查的。需要添加valid注解。
    //添加Valid注解后，如果不满足校验的话。就不会再执行接下来的方法。
    //而错误的信息则被封装在BindingResult，如果在参数上引入BindingResult，参数如果不满足的话。请求还会继续。

    @PostMapping
    public User create(@Valid @RequestBody User user){
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setId(1);
        return u;
    }


    //添加了正则判断id必须为数字
    @GetMapping(value = "/{id:\\d+}")
    public User getInfo(@PathVariable(name = "id")int id){
        System.out.println("getinfo execute");
        throw new UserNoExistException("用户未找到");
//        User user = new User();
//        user.setUsername("asule");
//        user.setPassword("qwerty");
//        return user;
    }

}
