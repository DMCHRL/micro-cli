package com.micro.saas.core.constant;


import com.micro.saas.core.api.IErrorCode;

public enum CommonExceptionEnum implements IErrorCode {
    INVALID_TOKEN(4001,"invalidToken"),
    NO_AUTHENTICATION(4002,"noAuthentication"),
    INSUFFICIENT_AUTHORITY(4003,"insufficientAuthority"),
    SMS_VERIFY_COUNT_MAX(5002,"今日已达6次上限，不能再次获取验证码，若有需要，请联系客服020-888888"),
    SMS_VERIFY_CODE_ERROR(5003,"验证码错误"),
    SMS_VERIFY_CODE_INVALID(5004,"验证码失效")
    ;

    private long code;
    private String msg;

    CommonExceptionEnum(long code, String msg) {
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
