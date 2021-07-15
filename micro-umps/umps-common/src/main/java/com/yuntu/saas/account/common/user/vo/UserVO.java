package com.yuntu.saas.account.common.user.vo;

import com.yuntu.saas.account.common.menu.dto.MenuTree;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private String username;

    private String company;

    private String userType;

    private String logo;

    private String roleName;

    private String roleCode;

    private List<MenuTree> menuTreeList;
}
