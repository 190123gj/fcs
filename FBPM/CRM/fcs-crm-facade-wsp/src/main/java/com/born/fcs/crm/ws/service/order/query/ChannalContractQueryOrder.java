package com.born.fcs.crm.ws.service.order.query;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 渠道合同查询
 * */
public class ChannalContractQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = -3263531757206711966L;
	/** 渠道号 */
	private String channalCode;
	/** 渠道名 */
	private String channalName;
	/** 渠道类型 */
	private String channalType;
	/** 查询起始时间 */
	private Date startDate;
	/** 查询截止时间 */
	private Date endDate;
	/** 合同状态 */
	private String status;
	/** 按渠道名模糊搜索 */
	private String likeChannalName;
	/** 按渠道编号模糊搜索 */
	private String likeChannalCode;
	/** 新建渠道排除已存在渠道的合同 仅IS生效 */
	private String info1;
	
	public String getInfo1() {
		return this.info1;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public String getChannalCode() {
		return this.channalCode;
	}
	
	public void setChannalCode(String channalCode) {
		this.channalCode = channalCode;
	}
	
	public String getChannalName() {
		return this.channalName;
	}
	
	public void setChannalName(String channalName) {
		this.channalName = channalName;
	}
	
	public String getChannalType() {
		return this.channalType;
	}
	
	public void setChannalType(String channalType) {
		this.channalType = channalType;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getLikeChannalName() {
		return this.likeChannalName;
	}
	
	public void setLikeChannalName(String likeChannalName) {
		this.likeChannalName = likeChannalName;
	}
	
	public String getLikeChannalCode() {
		return this.likeChannalCode;
	}
	
	public void setLikeChannalCode(String likeChannalCode) {
		this.likeChannalCode = likeChannalCode;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannalContractQueryOrder [channalCode=");
		builder.append(channalCode);
		builder.append(", channalName=");
		builder.append(channalName);
		builder.append(", channalType=");
		builder.append(channalType);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
