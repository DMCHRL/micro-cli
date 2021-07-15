package com.yuntu.saas.account.common.user.constant;

public enum SysUserTypeEnum {
//0 主账号,1 子账号,2 G端
    USER_TYPE_ADMIN("1","主账号"),
    USER_TYPE_SUB_USER("2","子账号"),
    ;

    private String code;
    private String name;

    SysUserTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
