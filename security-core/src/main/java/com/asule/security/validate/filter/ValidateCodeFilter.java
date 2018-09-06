package com.asule.security.validate.filter;
import com.asule.security.SecurityConstants;
import com.asule.security.entity.ImageCode;
import com.asule.security.handler.AsuleAuthenticationFailureHandler;
import com.asule.security.properties.BaseProperties;
import com.asule.security.validate.ValidateCodeType;
import com.asule.security.validate.controller.ValidateController;
import com.asule.security.validate.exception.ValidateCodeException;
import com.asule.security.validate.processor.ValidateCodeProcessorHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/**
 * 添加一个过滤器，把它加入到spring-security的过滤器链中。
 * 处理登录后，验证code的过滤器。
 * 把它添加到UsernamePasswordAuthenticationFilter前
 */

/**
 * 实现了InitializingBean的bean，在初始化该bean时，会调用其afterPropertiesSet
 */

@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{


    @Autowired
    private AsuleAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Autowired
    private BaseProperties baseProperties;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    //验证请求url与配置的url是否匹配的工具类
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private Map<String,ValidateCodeType> mapUrls=new HashMap<>();


    //在初始化该filter时会被调用，生成需要校验验证码的urls
    @Override
    public void afterPropertiesSet() throws ServletException {
        //将需要进行验证的url添加到mapUrls中。
        //先添加两组默认的规则。一个是图形验证码，一个是手机验证码
        mapUrls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        mapUrls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        //将配置的url添加到mapUrls规则中
        addUrlToMap(baseProperties.getValidate().getImage().getUrl(),ValidateCodeType.IMAGE);
    }

    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                mapUrls.put(url, type);
            }
        }
    }


    /**
     * 获取请求的url，判断该url是否为GET请求，判断该url是否在验证规则中。
     * 若不在返回null。存在返回对应的验证类型。
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = mapUrls.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = mapUrls.get(url);
                }
            }
        }
        return result;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType validateCodeType = getValidateCodeType(httpServletRequest);
        if (validateCodeType!=null){
            try{
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType).validate(new ServletWebRequest(httpServletRequest,httpServletResponse));
            }catch (ValidateCodeException ex){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,ex);
                return;
            }
        }
        //不验证，直接放行到下一个filter
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
