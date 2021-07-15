package com.micro.saas.umps.admin.user.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micro.saas.umps.admin.organ.service.ISysOrganService;
import com.micro.saas.umps.admin.user.service.ISysUserService;
import com.yuntu.saas.account.common.menu.entity.SysMenu;
import com.yuntu.saas.account.common.menu.utils.TreeUtil;
import com.yuntu.saas.account.common.organ.entity.SysOrgan;
import com.yuntu.saas.account.common.user.dto.SubUserDTO;
import com.yuntu.saas.account.common.user.dto.UserDTO;
import com.yuntu.saas.account.common.user.dto.UserParams;
import com.yuntu.saas.account.common.user.entity.SysUser;
import com.yuntu.saas.account.common.user.vo.UserVO;
import com.micro.saas.core.api.R;
import com.micro.saas.core.constant.CommonConstant;
import com.micro.saas.data.tenant.TenantContextHolder;
import com.micro.saas.secuity.annotation.Inside;
import com.micro.saas.secuity.entity.SystemUser;
import com.micro.saas.secuity.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
@RestController
@RequestMapping("/user")
@Api(tags="用户管理")
public class SysUserController {

    @Autowired
    private ISysUserService iSysUserService;


    @Autowired
    private ISysOrganService iSysOrganService;


    @GetMapping(value = "/info",headers = "version=1.0.0")
    @ApiOperation(value="用户信息")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public R<UserVO> info(){
        UserVO userVO = new UserVO();
        SystemUser user = SecurityUtils.getUser();
        List<SysMenu> sysMenus = iSysUserService.getUserMenuByUserId(user.getUserId());
        userVO.setMenuTreeList(TreeUtil.buildTree(sysMenus, CommonConstant.PARENT_ID));
        SysOrgan sysOrgan = iSysOrganService.getById(user.getOrganId());
        userVO.setUsername(user.getUsername());
        userVO.setUserType(user.getUserType());
        userVO.setCompany(sysOrgan.getName());
        userVO.setLogo(sysOrgan.getLogo());
        return R.ok(userVO);
    }

    @GetMapping(value = "/sendVerifyCode/{phone}",headers = "version=1.0.0")
    @ApiOperation(value="发送登录验证码")
    public R<Void> sendVerifyCode(@PathVariable(value = "phone") String phone){
        iSysUserService.sendVerifyCode(phone);
        return R.ok(null);
    }

    @GetMapping(value = "/info/{phone}")
    @Inside
    public R<SystemUser> infoByPhone(@PathVariable(value = "phone") String phone ){
        return R.ok( iSysUserService.infoByPhone(phone));
    }


}
