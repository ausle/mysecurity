package com.asule.security.validate.sender;

public interface SmsCodeSender {


    /**
     * 向手机号发送验证码
     * @param mobile 手机
     * @param code  验证码
     */
    void sendSmsCode(String mobile,String code);

}
