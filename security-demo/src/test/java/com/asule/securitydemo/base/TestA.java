package com.asule.securitydemo.base;


import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.time.LocalDateTime;

public class TestA {


    @Test
    public void testTime(){


        String type = StringUtils.substringBefore(getClass().getSimpleName(), "A");
        System.out.println(type);


        ValidateCodeType image = ValidateCodeType.valueOf("SMS");

        System.out.println(image.getValidateType());


        String string = ValidateCodeType.SMS.toString();
        System.out.println(string);


    }



}
