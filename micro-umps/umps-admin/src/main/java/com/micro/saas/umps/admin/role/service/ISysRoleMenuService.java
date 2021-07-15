package com.micro.saas.umps.admin.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuntu.saas.account.common.menu.entity.SysMenu;
import com.yuntu.saas.account.common.role.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    List<SysMenu> getByRoleId(String roleId);


}
