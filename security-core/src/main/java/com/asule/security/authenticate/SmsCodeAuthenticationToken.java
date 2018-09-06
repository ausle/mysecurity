package com.asule.security.authenticate;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {


    private final Object principal;


    //接收手机号。走到这里时，已经在之前filter中验证过验证码。
    public SmsCodeAuthenticationToken(String mobile) {
        super(null);
        this.principal=mobile;
        super.setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String mobile) {
        super(authorities);
        this.principal = mobile;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
