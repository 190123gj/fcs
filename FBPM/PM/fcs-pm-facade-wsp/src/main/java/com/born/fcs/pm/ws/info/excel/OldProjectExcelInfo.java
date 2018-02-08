/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:28:24 创建
 */
package com.born.fcs.pm.ws.info.excel;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 
 * 直接录入的老项目Info合集
 * @author hjiajie
 * 
 */
public class OldProjectExcelInfo extends ProcessOrder {
	
	private static final long serialVersionUID = -6473528501141841601L;
	
	/** 组织机构代码 */
	private String orgCode;
	
	/** 客户名称 */
	private String customerName;
	
	/** 省（自治区、直辖市） 云南省 */
	private String provinceName;
	
	/** 市（区、地、州、盟） 大理白族自治州 */
	private String countyName;
	
	/*** 县（区、市、旗 ） 大理市 */
	private String cityName;
	
	/** 行业类别 代码（大类） 建筑业 */
	private String typeCode;
	/** 行业类别 代码（小类） 建筑安装业 */
	private String childTypeCode;
	/** 企业类型 代码 20 民营企业 */
	private String enterpriseType;
	/** 企业类型 是否融资平台（1是 0否） */
	private String isLocalGovPlatform;
	
	/** 企业规模 代码 2 中型 */
	private String scale;
	
	/** 客户首次入库时间 （年\月\日） 2015-11-2 */
	private String firstInsertTime;
	
	/** 是否外向型经济客户（1是 0否） 0否 */
	private String isExportOrientedEconomy;
	
	/*** 项目名称 */
	private String projectName;
	
	/** 立项时间 2013-10-21 */
	private String projectAddTime;
	
	/** 归属部门 代码 22 云南工作组 */
	private String deptName;
	
	/*** 渠道 项目渠道代码 重庆金融交易所 */
	private String projectChannelName;
	
	/*** 渠道 资金渠道代码 重庆金融交易所 */
	private String capitalChannelName;
	
	/** 产品类型 代码 债券融资担保 */
	private String busiTypeName;
	
	/** 产品类型 代码 121私募债担保 */
	private String busiSubTypeName;
	
	/*** 授信情况 授信合同金额（万元，两位小数） 80000000 */
	private String amount;
	/*** 授信情况 授信开始时间（年\月\日） 2014-1-15 */
	private String startTime;
	/*** 授信情况 授信结束时间（年\月\日） 2014-1-15 */
	private String endTime;
	
	/*** 授信情况 授信期限（以月为单位）24 */
	private String timeLimit;
	
	/** 项目费率（百分数，两位小数） */
	private String rate;
	
	/** 项目责任人员 业务经理 */
	private String busiManagerName;
	
	/** 项目责任人员 风险经理 */
	private String riskManagerName;
	
	/** 项目责任人员 法务经理 */
	private String legalManagerName;
	
	/** 项目变动情况 放款信息 */
	private List<OldProjectExcelDetailInfo> outInfo = new ArrayList<OldProjectExcelDetailInfo>();
	/** 项目变动情况 还款信息 */
	private List<OldProjectExcelDetailInfo> backInfo = new ArrayList<OldProjectExcelDetailInfo>();
	
	@Override
	public void check() {
		validateHasText(projectName, "项目名称");
		validateHasText(customerName, "客户名称");
		validateHasText(deptName, "归属部门");
		validateHasText(busiTypeName, "产品类型");
		validateHasText(busiSubTypeName, "产品类型");
		validateHasText(busiManagerName, "客户经理");
		validateHasText(amount, "授信合同金额");
		validateHasText(startTime, "授信开始时间");
		validateHasText(endTime, "授信结束时间");
		validateHasText(projectAddTime, "立项时间");
	}
	
	public String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getProvinceName() {
		return this.provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCountyName() {
		return this.countyName;
	}
	
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getTypeCode() {
		return this.typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getChildTypeCode() {
		return this.childTypeCode;
	}
	
	public void setChildTypeCode(String childTypeCode) {
		this.childTypeCode = childTypeCode;
	}
	
	public String getEnterpriseType() {
		return this.enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public String getIsLocalGovPlatform() {
		return this.isLocalGovPlatform;
	}
	
	public void setIsLocalGovPlatform(String isLocalGovPlatform) {
		this.isLocalGovPlatform = isLocalGovPlatform;
	}
	
	public String getScale() {
		return this.scale;
	}
	
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	public String getFirstInsertTime() {
		return this.firstInsertTime;
	}
	
	public void setFirstInsertTime(String firstInsertTime) {
		this.firstInsertTime = firstInsertTime;
	}
	
	public String getIsExportOrientedEconomy() {
		return this.isExportOrientedEconomy;
	}
	
	public void setIsExportOrientedEconomy(String isExportOrientedEconomy) {
		this.isExportOrientedEconomy = isExportOrientedEconomy;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectAddTime() {
		return this.projectAddTime;
	}
	
	public void setProjectAddTime(String projectAddTime) {
		this.projectAddTime = projectAddTime;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getProjectChannelName() {
		return this.projectChannelName;
	}
	
	public void setProjectChannelName(String projectChannelName) {
		this.projectChannelName = projectChannelName;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getBusiSubTypeName() {
		return this.busiSubTypeName;
	}
	
	public void setBusiSubTypeName(String busiSubTypeName) {
		this.busiSubTypeName = busiSubTypeName;
	}
	
	public String getAmount() {
		return this.amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getRate() {
		return this.rate;
	}
	
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getRiskManagerName() {
		return this.riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public String getLegalManagerName() {
		return this.legalManagerName;
	}
	
	public void setLegalManagerName(String legalManagerName) {
		this.legalManagerName = legalManagerName;
	}
	
	public List<OldProjectExcelDetailInfo> getOutInfo() {
		return this.outInfo;
	}
	
	public void setOutInfo(List<OldProjectExcelDetailInfo> outInfo) {
		this.outInfo = outInfo;
	}
	
	public List<OldProjectExcelDetailInfo> getBackInfo() {
		return this.backInfo;
	}
	
	public void setBackInfo(List<OldProjectExcelDetailInfo> backInfo) {
		this.backInfo = backInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OldProjectExcelInfo [orgCode=");
		builder.append(orgCode);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", provinceName=");
		builder.append(provinceName);
		builder.append(", countyName=");
		builder.append(countyName);
		builder.append(", cityName=");
		builder.append(cityName);
		builder.append(", typeCode=");
		builder.append(typeCode);
		builder.append(", childTypeCode=");
		builder.append(childTypeCode);
		builder.append(", enterpriseType=");
		builder.append(enterpriseType);
		builder.append(", isLocalGovPlatform=");
		builder.append(isLocalGovPlatform);
		builder.append(", scale=");
		builder.append(scale);
		builder.append(", firstInsertTime=");
		builder.append(firstInsertTime);
		builder.append(", isExportOrientedEconomy=");
		builder.append(isExportOrientedEconomy);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", projectAddTime=");
		builder.append(projectAddTime);
		builder.append(", deptName=");
		builder.append(deptName);
		builder.append(", projectChannelName=");
		builder.append(projectChannelName);
		builder.append(", capitalChannelName=");
		builder.append(capitalChannelName);
		builder.append(", busiTypeName=");
		builder.append(busiTypeName);
		builder.append(", busiSubTypeName=");
		builder.append(busiSubTypeName);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", timeLimit=");
		builder.append(timeLimit);
		builder.append(", rate=");
		builder.append(rate);
		builder.append(", busiManagerName=");
		builder.append(busiManagerName);
		builder.append(", riskManagerName=");
		builder.append(riskManagerName);
		builder.append(", legalManagerName=");
		builder.append(legalManagerName);
		builder.append(", outInfo=");
		builder.append(outInfo);
		builder.append(", backInfo=");
		builder.append(backInfo);
		builder.append("]");
		return builder.toString();
	}
	
}
