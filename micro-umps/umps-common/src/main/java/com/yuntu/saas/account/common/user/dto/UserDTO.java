package com.yuntu.saas.account.common.user.dto;

import lombok.Data;


@Data
public class UserDTO {

    private String userId;
    private String username;

    private String company;

    private String phone;
    private String tenantId;
    private String organId;
    private String roleCode;
    private String avatar;
    private String status;
    private String sex;
    private String remark;
    private String userSource;

    private String logo;

}
