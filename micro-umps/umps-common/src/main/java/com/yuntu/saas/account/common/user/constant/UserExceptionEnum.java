package com.yuntu.saas.account.common.user.constant;


import com.micro.saas.core.api.IErrorCode;

public enum UserExceptionEnum implements IErrorCode {
    EXIST_PHONE(6001,"手机号已注册")
    ;

    private long code;
    private String msg;

    UserExceptionEnum(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
