package com.asule.security.validate;

import com.asule.security.SecurityConstants;

public enum ValidateCodeType {

    SMS(SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS),
    IMAGE(SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE);

    //
    private String formValidateParam;


    ValidateCodeType(String formValidateParam) {
        this.formValidateParam = formValidateParam;
    }

    public String getFormValidateParam() {
        return formValidateParam;
    }
}
