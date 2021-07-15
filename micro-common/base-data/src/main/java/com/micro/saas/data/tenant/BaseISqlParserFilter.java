package com.micro.saas.data.tenant;

import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Nero
 * 忽略租户约束
 */
@Slf4j
public class BaseISqlParserFilter implements ISqlParserFilter {

    @Autowired
    private TenantConfigProperties tenantConfigProperties;

    @Override
    public boolean doFilter(MetaObject metaObject) {
        MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
        List<String> ignoreMapper = tenantConfigProperties.getIgnoreMapper();
        return ignoreMapper.contains(ms.getId());
    }
}
