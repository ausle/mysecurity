package com.asule.security.properties;

import com.asule.security.LoginType;

public class BrowserProperties {

    private String loginPage="/asule-signIn.html";

    //登录成功后返回数据的类型
    private LoginType loginType=LoginType.JSON;


    private int rememberMeSeconds=3600;


    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
