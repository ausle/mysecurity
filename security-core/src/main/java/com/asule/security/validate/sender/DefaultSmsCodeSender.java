package com.asule.security.validate.sender;

import org.springframework.stereotype.Component;

@Component
public class DefaultSmsCodeSender implements SmsCodeSender{

    @Override
    public void sendSmsCode(String mobile, String code) {
        System.out.println("向"+mobile+"发送验证码，验证码为"+code);
    }
}
