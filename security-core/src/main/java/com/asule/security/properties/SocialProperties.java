package com.asule.security.properties;

import com.asule.security.soical.qq.api.QQ;

public class SocialProperties {

    private QQProperties qq=new QQProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }
}
