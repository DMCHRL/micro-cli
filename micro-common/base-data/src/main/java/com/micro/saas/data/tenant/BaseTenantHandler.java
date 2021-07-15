package com.micro.saas.data.tenant;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author
 * <p>
 * 租户维护处理器
 */
@Slf4j
public class BaseTenantHandler implements TenantLineHandler {
	@Autowired
	private TenantConfigProperties tenantConfigProperties;




	@Override
	public Expression getTenantId() {
		String tenantId = TenantContextHolder.getTenantId();
		log.debug("当前租户为 >> {}", tenantId);
		if (StrUtil.isBlank(tenantId)) {
			return new NullValue();
		}
		return new StringValue(tenantId);
	}

	/**
	 * 获取租户字段名
	 *
	 * @return 租户字段名
	 */
	@Override
	public String getTenantIdColumn() {
		return tenantConfigProperties.getColumn();
	}

	//这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
	@Override
	public boolean ignoreTable(String tableName) {
		return !tenantConfigProperties.getTables().contains(tableName);
	}

}
