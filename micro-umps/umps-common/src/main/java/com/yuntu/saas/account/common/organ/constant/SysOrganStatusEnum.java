package com.yuntu.saas.account.common.organ.constant;

public enum SysOrganStatusEnum {

    NORMAL("0","正常"),
    FROZEN("9","冻结");

    private String code;
    private String name;

    SysOrganStatusEnum(String code, String name) {
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
