package com.asule.security.validate.generator;

import com.asule.security.entity.SmsCode;
import com.asule.security.entity.ValidateCode;
import com.asule.security.properties.BaseProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

public class SmsValidateCodeGenerator implements ValidateCodeGenerator{

    @Autowired
    private BaseProperties baseProperties;

    //生成一个随机验证码
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code= RandomStringUtils.randomNumeric(baseProperties.getValidate().getSms().getLength());
        int expired = baseProperties.getValidate().getSms().getExpired();
        return new SmsCode(code,expired);
    }
}
