package com.born.fcs.pm.ws.order.projectissueinformation;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 发债类项目-分保信息Order
 *
 *
 * @author Ji
 *
 */
public class ProjectBondReinsuranceInformationOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -8716146442179183006L;
	/** 主键ID */
	private Long id;
	/** 对应发债项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 分保方 */
	private String reinsuranceObject;
	/** 分保比例 */
	private Double reinsuranceRatio;
	/** 分保金额 */
	private Money reinsuranceAmount = new Money(0, 0);
	
	/** 是否进出口公司 */
	private String isJin;//用于页面判断,其它地方使用可不用管
	/** 新增时间 */
	private Date rawAddTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
	
	public Double getReinsuranceRatio() {
		return reinsuranceRatio;
	}
	
	public void setReinsuranceRatio(Double reinsuranceRatio) {
		this.reinsuranceRatio = reinsuranceRatio;
	}
	
	public Money getReinsuranceAmount() {
		return reinsuranceAmount;
	}
	
	public void setReinsuranceAmount(Money reinsuranceAmount) {
		this.reinsuranceAmount = reinsuranceAmount;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getIsJin() {
		return isJin;
	}
	
	public void setIsJin(String isJin) {
		this.isJin = isJin;
	}
	
}
