package com.asule.security.validate.generator;


import com.asule.security.entity.ImageCode;
import com.asule.security.entity.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

//验证码制造器接口
public interface ValidateCodeGenerator {


    //生成验证码
    ValidateCode generate(ServletWebRequest request);

}
