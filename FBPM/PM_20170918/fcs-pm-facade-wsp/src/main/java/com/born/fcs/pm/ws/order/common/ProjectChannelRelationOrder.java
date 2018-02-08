package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 项目渠道关系Order
 * @author wuzj
 */
public class ProjectChannelRelationOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -6856976148442583523L;
	
	/** 渠道ID */
	private Long channelId;
	/** 渠道编码 */
	private String channelCode;
	/** 渠道类型 */
	private String channelType;
	/** 渠道名称 */
	private String channelName;
	/** 二级渠道ID */
	private Long subChannelId;
	/** 二级渠道编码 */
	private String subChannelCode;
	/** 二级渠道类型 */
	private String subChannelType;
	/** 二级渠道名称 */
	private String subChannelName;
	
	public boolean isNull() {
		return isNull(channelId) && isNull(channelCode) && isNull(channelType)
				&& isNull(channelName) && isNull(subChannelId) && isNull(subChannelCode)
				&& isNull(subChannelType) && isNull(subChannelName);
	}
	
	public Long getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	public String getChannelCode() {
		return this.channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getChannelType() {
		return this.channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public Long getSubChannelId() {
		return this.subChannelId;
	}
	
	public void setSubChannelId(Long subChannelId) {
		this.subChannelId = subChannelId;
	}
	
	public String getSubChannelCode() {
		return this.subChannelCode;
	}
	
	public void setSubChannelCode(String subChannelCode) {
		this.subChannelCode = subChannelCode;
	}
	
	public String getSubChannelType() {
		return this.subChannelType;
	}
	
	public void setSubChannelType(String subChannelType) {
		this.subChannelType = subChannelType;
	}
	
	public String getSubChannelName() {
		return this.subChannelName;
	}
	
	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}
}
