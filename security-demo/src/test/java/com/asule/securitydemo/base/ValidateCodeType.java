package com.asule.securitydemo.base;

import com.asule.security.SecurityConstants;

public enum ValidateCodeType {

    SMS(SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS),IMAGE(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);

    private String validateType;

    ValidateCodeType(String validateType) {
        this.validateType = validateType;
    }


    public String getValidateType() {
        return validateType;
    }

    public void setValidateType(String validateType) {
        this.validateType = validateType;
    }
}
