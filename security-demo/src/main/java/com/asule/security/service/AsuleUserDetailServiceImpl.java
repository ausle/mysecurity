package com.asule.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AsuleUserDetailServiceImpl implements UserDetailsService,SocialUserDetailsService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder encoder;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> admin = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        String encryptPassword=encoder.encode("qwerty");
        return new User(s,encryptPassword,admin);
    }


    /**
     * 社交登录的userId
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        List<GrantedAuthority> admin = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        String encryptPassword=encoder.encode("qwerty");
        return new SocialUser(userId,encryptPassword,admin);
    }
}
