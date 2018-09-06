package com.asule.security.soical.qq.api;

import com.asule.security.properties.BaseProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;
/*
    AbstractOAuth2ApiBinding是spring social处理用户信息用的。
    它有两个属性，分别是accessToken，restTemplate。
    前者是获取到的token令牌，后者提供给我们发送请求使用。

    获取用户信息，需要使用的数据：
    token，oauth2流程走完后，就会得到一个token。
    appid，注册为开发者后，创建应用就会为应用生成一个appid。
    openid，利用token要可以获取openid
*/
//不是单例。不能该类添加到spring容器中。对于每一个用户而言，都有对应的一个用户信息。
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{

    @Autowired
    private BaseProperties baseProperties;

    private String openId;
    private String appId;

    private ObjectMapper objectMapper=new ObjectMapper();

    private final String GET_OPENID_URL="https://graph.qq.com/oauth2.0/me?access_token=%s";
    private final String GET_USERINFO_URL="https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    public QQImpl(String accessToken) {
        //token作为请求参数
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        String getOpenUrl=String.format(GET_OPENID_URL,accessToken);

        //发出请求
        String getOpenResult = getRestTemplate().getForObject(getOpenUrl, String.class);
        //从获取的json中筛选出openid的值
        this.openId = StringUtils.substringBetween(getOpenResult, "\"openid\"", "}");
    }

    @Override
    public QQUserInfo getQQUserInfo() throws IOException {
        appId=baseProperties.getSocial().getQq().getAppId();

        String getUserInfoUrl = String.format(GET_USERINFO_URL, appId, openId);

        String getUserInfoResult = getRestTemplate().getForObject(getUserInfoUrl, String.class);

        QQUserInfo qqUserInfo = objectMapper.readValue(getUserInfoResult, QQUserInfo.class);

        return qqUserInfo;
    }
}
