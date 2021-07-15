package com.micro.saas.secuity.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
@Data
public class SystemAuthority implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;
    //菜单权限标识
    private String permission;


    public SystemAuthority() {
    }

    public SystemAuthority(String permission) {
        this.permission = permission;
    }
}
