package com.micro.saas.umps.admin.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.saas.umps.admin.menu.service.ISysMenuService;
import com.micro.saas.umps.admin.sms.service.SmsService;
import com.micro.saas.umps.admin.role.service.ISysRoleMenuService;
import com.micro.saas.umps.admin.role.service.ISysRoleService;
import com.micro.saas.umps.admin.user.mapper.SysUserMapper;
import com.micro.saas.umps.admin.user.service.ISysUserRoleService;
import com.micro.saas.umps.admin.user.service.ISysUserService;
import com.yuntu.saas.account.common.menu.entity.SysMenu;
import com.yuntu.saas.account.common.role.entity.SysRole;
import com.yuntu.saas.account.common.role.entity.SysRoleMenu;
import com.yuntu.saas.account.common.user.constant.SysUserTypeEnum;
import com.yuntu.saas.account.common.user.constant.UserExceptionEnum;
import com.yuntu.saas.account.common.user.dto.SubUserDTO;
import com.yuntu.saas.account.common.user.dto.UserDTO;
import com.yuntu.saas.account.common.user.dto.UserParams;
import com.yuntu.saas.account.common.user.entity.SysUser;
import com.yuntu.saas.account.common.user.entity.SysUserRole;
import com.micro.saas.core.api.ApiAssert;
import com.micro.saas.core.constant.CommonConstant;
import com.micro.saas.core.constant.CommonExceptionEnum;
import com.micro.saas.core.constant.RedisKeyPrefix;
import com.micro.saas.data.tenant.TenantContextHolder;
import com.micro.saas.secuity.entity.BasePhone;
import com.micro.saas.secuity.entity.SystemAuthority;
import com.micro.saas.secuity.entity.SystemUser;
import com.micro.saas.secuity.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService iSysUserRoleService;
    @Autowired
    private ISysRoleMenuService iSysRoleMenuService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SmsService smsService;


    @Override
    public List<SysMenu> getUserMenuByUserId(String userId) {
        SysUserRole sysUserRole = iSysUserRoleService.getOne(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        return iSysRoleMenuService.getByRoleId(sysUserRole.getRoleId());
    }

    @Override
    public void sendVerifyCode(String phone){
        String code = RandomUtil.randomNumbers(6);
        BasePhone basePhone =(BasePhone)redisTemplate.opsForValue().get(RedisKeyPrefix.SMS_CODE_PREFIX+phone);
        if(basePhone == null){
            basePhone = new BasePhone();
            basePhone.setDayNum(1);
        }else{
            Integer dayNum = basePhone.getDayNum();
            ApiAssert.ge(CommonConstant.SMS_CODE_DAY_MAX_TIMES,dayNum, CommonExceptionEnum.SMS_VERIFY_COUNT_MAX);
            basePhone.setDayNum(dayNum+1);
        }
        basePhone.setCode(code);
        basePhone.setLastSendTime(LocalDateTime.now());
        basePhone.setVerify(false);
        //今天剩余时间
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(),midnight);
        redisTemplate.opsForValue().set(RedisKeyPrefix.SMS_CODE_PREFIX + phone, basePhone, millSeconds, TimeUnit.MILLISECONDS);
        smsService.sendVerificationCode(phone,code);
    }



    @Override
    public SystemUser infoByPhone(String phone) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone,phone),false);
        sysUser.setLastLogin(LocalDateTime.now());
        this.updateById(sysUser);
        TenantContextHolder.setTenantId(sysUser.getTenantId());
        List<SysMenu> sysMenuList = this.getUserMenuByUserId(sysUser.getId());
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(sysUser.getUsername());
        systemUser.setUserId(sysUser.getId());
        systemUser.setOrganId(sysUser.getOrganId());
        systemUser.setTenantId(sysUser.getTenantId());
        systemUser.setStatus(sysUser.getStatus());
        List<SystemAuthority> grantedAuthorityList = sysMenuList.stream().filter(t -> StringUtils.isNotBlank(t.getPermission())).map(t -> new SystemAuthority(t.getPermission())).collect(Collectors.toList());
        systemUser.setSystemAuthorityList(grantedAuthorityList);
        return systemUser;
    }



}
