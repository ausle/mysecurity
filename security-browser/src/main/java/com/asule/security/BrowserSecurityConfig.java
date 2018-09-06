package com.asule.security;

import com.asule.security.authenticate.mobile.SmsCodeAuthenticationSecurityConfig;
import com.asule.security.handler.AsuleAuthenticationFailureHandler;
import com.asule.security.handler.AsuleAuthenticationSuccessHandler;
import com.asule.security.properties.BaseProperties;
import com.asule.security.validate.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;


@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BaseProperties baseProperties;

    @Autowired
    private AsuleAuthenticationSuccessHandler successHandler;

    @Autowired
    private AsuleAuthenticationFailureHandler failureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;


//    配置tokenRepository
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //会自动创建一张表来记录 token和username
        return tokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //loginPage指定登录页面,loginProcessingUrl指定登录提交的表单要经过UsernamePasswordAuthenticationFilter过滤器处理
        http.formLogin().loginPage("/authentication/require").loginProcessingUrl("/authentication/form")
                //验证通过和验证失败处理的handler
                .successHandler(successHandler).failureHandler(failureHandler)
                .and()
                //配置记住我功能。
                .rememberMe()
                .tokenRepository(persistentTokenRepository())//配置tokenRepository
                .tokenValiditySeconds(baseProperties.getBrowser().getRememberMeSeconds())//记住我的时间
                .userDetailsService(userDetailsService)//根据用户名获取用户信息需要的userDetail实现
                .and()
                .addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        SecurityConstants.DEFAULT_LOGIN_URL,
                        baseProperties.getBrowser().getLoginPage()).permitAll()//指定页面不需要认证
                .anyRequest().authenticated()//任何请求都需要认证
                .and()
                .csrf().disable()//csrf失效
                .apply(smsCodeAuthenticationSecurityConfig)//往后添加一个smsCode配置
                .and()
                .apply(springSocialConfigurer);//开启SocialAuthenticationFilter过滤器
    }
}
