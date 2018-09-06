package com.asule.security.soical.qq.connect;

import com.asule.security.soical.qq.api.QQ;
import com.asule.security.soical.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import java.io.IOException;

//适配获取的用户信息和social-connection所需的信息
public class QQAdapter implements ApiAdapter<QQ>{
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        try {
            QQUserInfo qqUserInfo = qq.getQQUserInfo();
            connectionValues.setDisplayName(qqUserInfo.getNickname());
            connectionValues.setProviderUserId(qqUserInfo.getOpenId());
            connectionValues.setProfileUrl(null);//主页url
            connectionValues.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
