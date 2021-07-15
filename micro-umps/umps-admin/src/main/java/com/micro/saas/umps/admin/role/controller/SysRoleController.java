package com.micro.saas.umps.admin.role.controller;


import com.micro.saas.umps.admin.role.service.ISysRoleService;
import com.micro.saas.core.api.R;
import com.yuntu.saas.account.common.role.entity.SysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
@RestController
@RequestMapping("/role")
@Api(tags="角色管理")
public class SysRoleController {


}
