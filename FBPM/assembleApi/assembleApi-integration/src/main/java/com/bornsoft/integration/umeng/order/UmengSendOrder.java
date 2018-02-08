package com.bornsoft.integration.umeng.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 调用友盟接口的请求数据处理Order
 * */
public class UmengSendOrder {
	
	/** 默认列播 */
	String type = "customizedcast";
	/** 设备唯一表示 */
	String device_tokens;
	String appkey;
	String timestamp;
	/** 消息内容 */
	Payload payload;
	/** 发送策略 */
	Policy policy;
	/** 发送消息描述 */
	String description;
	/** true/false :正式/测试模式 */
	String production_mode;
	/**别名类型:自定义用户类型**/
	private String alias_type;
	/**别名**/
	private String alias;

	public String getDevice_tokens() {
		return device_tokens;
	}

	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}

	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAppkey() {
		return this.appkey;
	}
	
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Payload getPayload() {
		return this.payload;
	}
	
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	public Policy getPolicy() {
		return this.policy;
	}
	
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String isProduction_mode() {
		return this.production_mode;
	}
	
	public void setProduction_mode(String production_mode) {
		this.production_mode = production_mode;
	}
	

	public String getAlias_type() {
		return alias_type;
	}

	public void setAlias_type(String alias_type) {
		this.alias_type = alias_type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
