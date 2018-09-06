package com.asule.security.validate;

import com.asule.security.validate.generator.ImageValidateCodeGenerator;
import com.asule.security.validate.generator.SmsValidateCodeGenerator;
import com.asule.security.validate.generator.ValidateCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    //为了让使用者可以配置图形验证码生成器。当有名为imageValidateCodeGenerator的bean时，不创建对象。
    //而使用使用者的生成器。
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ValidateCodeGenerator codeGenerator = new ImageValidateCodeGenerator();
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    public ValidateCodeGenerator smsValidateCodeGenerator() {
        ValidateCodeGenerator codeGenerator = new SmsValidateCodeGenerator();
        return codeGenerator;
    }
}
