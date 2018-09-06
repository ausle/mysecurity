package com.asule.security.soical.qq.connect;

import com.asule.security.soical.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/*
    构建QQConnectionFactory需要包含serviceProvider以及一个ApiAdapter。
    它用于创建connection对象。
*/
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ>{

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
