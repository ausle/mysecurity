package com.asule.security.config;


import com.asule.security.intercepter.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private TimeInterceptor interceptor;


    //需要添加拦截器。
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(interceptor);
    }

    /*
            引入第三方的filter，可以用下面的方式使它成为spring的bean。
        */
//    @Bean
//    public FilterRegistrationBean register(){
//        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
//
//        //第三方的filter
////        TimeFilter timeFilter = new TimeFilter();
////        registrationBean.setFilter(timeFilter);
//
//        //添加filter的拦截规则。
////        List<String> urls=new ArrayList<>();
////        urls.add("/*");
////        registrationBean.setUrlPatterns(urls);
//
//        return registrationBean;
//    }






}
