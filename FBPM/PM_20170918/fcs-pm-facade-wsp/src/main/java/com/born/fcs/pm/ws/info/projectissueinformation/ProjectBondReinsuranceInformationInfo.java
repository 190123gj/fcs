package com.born.fcs.pm.ws.info.projectissueinformation;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 发债类项目-分保信息info
 *
 * @author Ji
 *
 */
public class ProjectBondReinsuranceInformationInfo extends BaseToStringInfo {
	private static final long serialVersionUID = -6288306380308901505L;
	/** 主键ID */
	private long id;
	/** 对应发债项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 分保方 */
	private String reinsuranceObject;
	/** 分保比例 */
	private double reinsuranceRatio;
	/** 分保金额 */
	private Money reinsuranceAmount = new Money(0, 0);
	/** 新增时间 */
	private Date rawAddTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getReinsuranceObject() {
		return reinsuranceObject;
	}
	
	public void setReinsuranceObject(String reinsuranceObject) {
		this.reinsuranceObject = reinsuranceObject;
	}
	
	public double getReinsuranceRatio() {
		return reinsuranceRatio;
	}
	
	public void setReinsuranceRatio(double reinsuranceRatio) {
		this.reinsuranceRatio = reinsuranceRatio;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Money getReinsuranceAmount() {
		return reinsuranceAmount;
	}
	
	public void setReinsuranceAmount(Money reinsuranceAmount) {
		this.reinsuranceAmount = reinsuranceAmount;
	}
	
}
