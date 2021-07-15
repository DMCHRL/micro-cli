package com.micro.saas.data.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 *
 * @author
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "base.tenant")
public class TenantConfigProperties {

	/**
	 * 维护租户列名称
	 */
	private String column = "tenant_id";

	/**
	 * 多租户的数据表集合
	 */
	private List<String> tables = new ArrayList<>();

	/**
	 * 忽略租户约束的mapper,要去全路径
	 */
	private List<String> ignoreMapper = new ArrayList<>();
}
