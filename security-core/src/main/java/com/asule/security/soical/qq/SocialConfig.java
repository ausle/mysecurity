package com.asule.security.soical.qq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@EnableSocial
@Configuration
public class SocialConfig extends SocialConfigurerAdapter{

    @Autowired
    private DataSource dataSource;

    /*
        该Repository操作userconnection数据库表。该表的名字不能修改。可以修改前缀。
        userId
        providerId
	    providerUserId  openId
    */
//    @Bean
//    public UsersConnectionRepository getQQConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator){
//        JdbcUsersConnectionRepository repository =
//                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        return repository;
//    }

    //作用添加一个过滤器到过滤器链中。会拦截某些特定的请求。
    @Bean
    public SpringSocialConfigurer getSpringSocialConfigurer(){
        return new SpringSocialConfigurer();
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }
}

/*

https://graph.qq.com/oauth2.0/authorize?client_id=1107825732&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8090%2Fauth%2Fqq&state=28f2460c-0995-4765-87d6-0a821ac66809


*/



