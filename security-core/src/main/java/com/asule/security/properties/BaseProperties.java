package com.asule.security.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "asule.security")
public class BaseProperties {

    private BrowserProperties browser=new BrowserProperties();

    private ValidateProperties validate=new ValidateProperties();

    private SocialProperties social=new SocialProperties();

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public ValidateProperties getValidate() {
        return validate;
    }

    public void setValidate(ValidateProperties validate) {
        this.validate = validate;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
