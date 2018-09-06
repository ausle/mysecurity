package com.asule.security.soical.qq.connect;

import com.asule.security.soical.qq.api.QQ;
import com.asule.security.soical.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;


/*
    服务提供商由两部分组成：操作oauth过程的OAuth2Template。它是spring social为我们提供的。
                         该过程有：引导用户去授权页面，授权后返回令牌等。

                         另一部分就是获取的用户信息。
*/
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    //获取Authorization Code
    private static final String authorizeUrl="https://graph.qq.com/oauth2.0/authorize";

    //通过Authorization Code获取Access Token
    private static final String accessTokenUrl="https://graph.qq.com/oauth2.0/token";

    //指定谁来执行oauth2的执行流程。这里使用social提供的oauth2Template
    public QQServiceProvider(String appId,String appSecret) {
        //clientId，clientSecret即应用的id和secret。
        //authorizeUrl,将用户导向认证服务器的url。accessTokenUrl，申请令牌的调用url
        super(new OAuth2Template(appId, appSecret, authorizeUrl, accessTokenUrl));
    }

    @Override
    public QQ getApi(String s) {
        return new QQImpl(s);
    }

}
