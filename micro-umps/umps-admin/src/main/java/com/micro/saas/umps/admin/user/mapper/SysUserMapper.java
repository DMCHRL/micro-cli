package com.micro.saas.umps.admin.user.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuntu.saas.account.common.user.dto.UserParams;
import com.yuntu.saas.account.common.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


}
