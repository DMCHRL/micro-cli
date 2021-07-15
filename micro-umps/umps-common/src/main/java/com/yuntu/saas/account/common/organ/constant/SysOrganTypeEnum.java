package com.yuntu.saas.account.common.organ.constant;

public enum SysOrganTypeEnum {

    TYPE_B("1","B端"),
    TYPE_G("2","G端"),
    TYPE_C("3","C端"),
    TYPE_N("4","运营");
    ;

    private String code;
    private String name;

    SysOrganTypeEnum(String code, String name) {
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
