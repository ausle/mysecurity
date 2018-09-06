package com.asule.security.validate;

import com.asule.security.entity.ImageCode;
import com.asule.security.validate.generator.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("imageValidateCodeGenerator")
public class IValidateCodeGenerator implements ValidateCodeGenerator{

    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("my manual image generator");
        return null;
    }
}
