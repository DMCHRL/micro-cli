package com.micro.saas.umps.admin.organ.controller;


import com.micro.saas.umps.admin.organ.service.ISysOrganService;
import com.yuntu.saas.account.common.organ.dao.SysOrganDTO;
import com.yuntu.saas.account.common.organ.entity.SysOrgan;
import com.micro.saas.core.api.R;
import com.micro.saas.secuity.annotation.Inside;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 机构管理 前端控制器
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
@RestController
@RequestMapping("/organ")
@Api(value="租户管理",tags = "租户管理")
public class SysOrganController {



}
