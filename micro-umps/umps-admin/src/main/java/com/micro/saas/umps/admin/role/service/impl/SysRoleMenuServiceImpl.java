package com.micro.saas.umps.admin.role.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.saas.umps.admin.menu.service.ISysMenuService;
import com.micro.saas.umps.admin.role.mapper.SysRoleMenuMapper;
import com.micro.saas.umps.admin.role.service.ISysRoleMenuService;
import com.yuntu.saas.account.common.menu.entity.SysMenu;
import com.yuntu.saas.account.common.role.entity.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    private ISysMenuService iSysMenuService;

    @Override
    public List<SysMenu> getByRoleId(String roleId) {
        List<String> menuIdList = this.list(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId))
                .stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        return iSysMenuService.listByIds(menuIdList);
    }

}
