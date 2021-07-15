package com.yuntu.saas.account.common.organ.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @author Liang
 * @since 2021-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysOrganDTO对象")
public class SysOrganDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构类型 1B 2G ")
    private String type;

    @ApiModelProperty(value = "机构名称")
    private String company;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "0正常 1未激活 2已锁定")
    private String status;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "性别1男 2女 3未知")
    private String sex;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "备注")
    private String userSource;




}
