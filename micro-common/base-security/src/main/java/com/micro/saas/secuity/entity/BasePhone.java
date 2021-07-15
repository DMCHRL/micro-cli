package com.micro.saas.secuity.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 手机验证码缓存
 * @author
 */
@Data
public class BasePhone implements Serializable {

    private static final long serialVersionUID = -1L;

    //验证码
    private String code;

    //当日获取次数
    private Integer dayNum;

    //最后发送时间
    private LocalDateTime lastSendTime;

    //是否已经验证
    private Boolean verify;


}
