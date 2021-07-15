package com.micro.saas.core.constant;

public interface CommonConstant {

    public static final String COMMON_STATUS_YES = "0";

    String YES = "1";
    String FAIL = "-1";

     String NO = "0";

    String PARENT_ID = "0";

    String AUTHORIZATION = "Authorization";

    String TENANT_ID = "tenantId";

    /**
     * 验证码每日限制次数
     */
    Integer SMS_CODE_DAY_MAX_TIMES = 6;

    /**
     * 验证码有效时间 min
     */
    Integer SMS_CODE_VERIFY_TIMES = 10;
}
