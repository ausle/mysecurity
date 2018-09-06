package com.asule.security.properties;

public class ValidateProperties {

    private ImageValidateProperties image=new ImageValidateProperties();

    private SmsCodeValidateProperties sms=new SmsCodeValidateProperties();


    public ImageValidateProperties getImage() {
        return image;
    }

    public void setImage(ImageValidateProperties image) {
        this.image = image;
    }

    public SmsCodeValidateProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeValidateProperties sms) {
        this.sms = sms;
    }
}
