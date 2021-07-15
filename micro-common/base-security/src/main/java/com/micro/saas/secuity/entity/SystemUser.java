package com.micro.saas.secuity.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.micro.saas.secuity.component.SimpleAuthorityDeserializer;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SystemUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    //用户Id
    private String userId;

    private String username;

    //租户Id
    private String tenantId;

    //0 主账号,1 子账号,2 G端
    private String userType;

    private List<SystemAuthority> systemAuthorityList;

    private String organId;

    private String status;

    @Override
    @JsonDeserialize(using = SimpleAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return systemAuthorityList.stream().filter(t -> StringUtils.isNotBlank(t.getPermission())).map(t -> new SimpleGrantedAuthority(t.getPermission())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //用户是否可用
    @Override
    public boolean isEnabled() {
        return "0".equals(status);
    }

    public List<SystemAuthority> getSystemAuthorityList() {
        return systemAuthorityList;
    }

    public SystemUser(String userId, String username, String tenantId, String userType, List<SystemAuthority> systemAuthorityList, String organId) {
        this.userId = userId;
        this.username = username;
        this.tenantId = tenantId;
        this.userType = userType;
        this.systemAuthorityList = systemAuthorityList;
        this.organId = organId;
    }

    public SystemUser() {
    }
}
