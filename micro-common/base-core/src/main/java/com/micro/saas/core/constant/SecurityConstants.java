package com.micro.saas.core.constant;

/**
 * @author
 */
public interface SecurityConstants {

	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";

	/**
	 * 用户ID字段
	 */
	String DETAILS_USER_ID = "user_id";

	/**
	 * 用户名字段
	 */
	String DETAILS_USERNAME = "username";

	/**
	 * 租户ID 字段
	 */
	String DETAILS_TENANT_ID = "tenant_id";
	String DETAILS_ORGAN_ID = "organ_id";
	String DETAILS_USER_TYPE = "user_type";

	/**
	 * 标识服务间调用
	 */
	String FROM = "from";
	String FROM_IN = "from_in";

	/**
	 * 资源服务器默认bean名称
	 */
	String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

}
