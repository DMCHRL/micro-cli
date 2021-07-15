package com.micro.saas.umps.admin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuntu.saas.account.common.menu.entity.SysMenu;
import com.yuntu.saas.account.common.user.dto.UserDTO;
import com.yuntu.saas.account.common.user.entity.SysUser;
import com.micro.saas.secuity.entity.SystemUser;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysMenu> getUserMenuByUserId(String userId);

    void sendVerifyCode(String phone);

    SystemUser infoByPhone(String phone);


}
