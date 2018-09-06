package com.asule.security.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


//处理SmsCodeAuthenticationToken的provider
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //验证过程，就是检查userDetail中
        SmsCodeAuthenticationToken smsCodeAuthenticationToken= (SmsCodeAuthenticationToken) authentication;
        String  mobile = (String) smsCodeAuthenticationToken.getPrincipal();
        UserDetails user  = userDetailsService.loadUserByUsername(mobile);

        if (user==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //验证通过后，添加上验证信息。再次封装一个SmsCodeAuthenticationToken。
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(user.getAuthorities(),mobile);
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
