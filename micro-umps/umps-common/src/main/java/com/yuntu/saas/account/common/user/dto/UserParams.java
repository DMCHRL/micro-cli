package com.yuntu.saas.account.common.user.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class UserParams {

    private Long current;
    private Long size;

    @ApiModelProperty(value = "排序")
    List<OrderItem> orders;

    private String role;

    private String status;

    private String userSource;

}
