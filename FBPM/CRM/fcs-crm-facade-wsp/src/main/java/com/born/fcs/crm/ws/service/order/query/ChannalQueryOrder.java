package com.born.fcs.crm.ws.service.order.query;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 渠道查询 order
 * */
public class ChannalQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -899032654217592989L;
	/** 渠道编号 */
	private String channelCode;
	/** 渠道名称 */
	private String channelName;
	/** 渠道分类 */
	private String channelType;
	/** 金融机构属性 */
	private String institutionalAttributes;
	/** 渠道状态 */
	private String status;
	/** 按名字模糊查询 */
	private String likeChannelName;
	/** 按编号模糊查询 */
	private String likeChannelCode;
	
	/** 按渠道编号或渠道名模糊查询 */
	private String likeCodeOrName;
	
	/** 大于授信截止时间 筛选 要过期或即将过期 的 */
	private Date creditEndDate;
	
	/** 是否查历史数据 */
	private String isHistory = BooleanEnum.NO.code();
	
	public String getIsHistory() {
		return this.isHistory;
	}
	
	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
	}
	
	public Date getCreditEndDate() {
		return this.creditEndDate;
	}
	
	public void setCreditEndDate(Date creditEndDate) {
		this.creditEndDate = creditEndDate;
	}
	
	public String getLikeCodeOrName() {
		return this.likeCodeOrName;
	}
	
	public void setLikeCodeOrName(String likeCodeOrName) {
		this.likeCodeOrName = likeCodeOrName;
	}
	
	public String getLikeChannelName() {
		return this.likeChannelName;
	}
	
	public void setLikeChannelName(String likeChannelName) {
		this.likeChannelName = likeChannelName;
	}
	
	public String getLikeChannelCode() {
		return this.likeChannelCode;
	}
	
	public void setLikeChannelCode(String likeChannelCode) {
		this.likeChannelCode = likeChannelCode;
	}
	
	public String getChannelCode() {
		return this.channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getChannelType() {
		return this.channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	public String getInstitutionalAttributes() {
		return this.institutionalAttributes;
	}
	
	public void setInstitutionalAttributes(String institutionalAttributes) {
		this.institutionalAttributes = institutionalAttributes;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannalQueryOrder [channelCode=");
		builder.append(channelCode);
		builder.append(", channelName=");
		builder.append(channelName);
		builder.append(", channelType=");
		builder.append(channelType);
		builder.append(", institutionalAttributes=");
		builder.append(institutionalAttributes);
		builder.append(", status=");
		builder.append(status);
		builder.append(", likeChannelName=");
		builder.append(likeChannelName);
		builder.append(", likeChannelCode=");
		builder.append(likeChannelCode);
		builder.append(", likeCodeOrName=");
		builder.append(likeCodeOrName);
		builder.append("]");
		return builder.toString();
	}
	
}
