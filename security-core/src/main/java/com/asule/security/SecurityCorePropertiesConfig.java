package com.asule.security;


import com.asule.security.properties.BaseProperties;
import com.asule.security.properties.BrowserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BaseProperties.class)
public class SecurityCorePropertiesConfig {

}
