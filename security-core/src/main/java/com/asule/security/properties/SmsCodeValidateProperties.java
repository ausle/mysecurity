package com.asule.security.properties;

public class SmsCodeValidateProperties {


    //短信验证码的长度
    private int length=4;

    //验证时长
    private int expired=120;

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
