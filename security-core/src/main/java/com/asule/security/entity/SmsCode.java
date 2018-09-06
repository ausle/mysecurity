package com.asule.security.entity;

import java.time.LocalDateTime;

public class SmsCode extends ValidateCode{

    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }

    public SmsCode(String code, int expireIn) {
        super(code, LocalDateTime.now().plusSeconds(expireIn));
    }
}
