package com.asule.security.soical.qq;

import com.asule.security.properties.BaseProperties;
import com.asule.security.soical.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

@Configuration
//配置了该属性，才会生效
@ConditionalOnProperty(prefix = "asule.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter{

    @Autowired
    private BaseProperties baseProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectionFactory(baseProperties.getSocial().getQq().getProviderId(),
                baseProperties.getSocial().getQq().getAppId(),
                baseProperties.getSocial().getQq().getAppSecret());
    }
}
