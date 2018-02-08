package com.born.fcs.am.ws.order.pledgeasset;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 资产关联项目Order
 *
 * @author jil
 *
 */
public class AssetRelationProjectOrder extends ProcessOrder {

	private static final long serialVersionUID = 7909326623014702381L;

	private Long id;
	// 资产id
	private long assetsId;
	// 资产状态
	private String assetsStatus;
	// 项目编号
	private String projectCode;
	// 项目名称
	private String projectName;
	// 客户id
	private long customerId;
	// 客户名称
	private String customerName;
	// 业务类型
	private String busiType;
	// 业务类型名称
	private String busiTypeName;

	private Date rawAddTime;

	private Date rawUpdateTime;
	// 是否取消关联
	private Boolean isCancelRelation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}

	public String getAssetsStatus() {
		return assetsStatus;
	}

	public void setAssetsStatus(String assetsStatus) {
		this.assetsStatus = assetsStatus;
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

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeName() {
		return busiTypeName;
	}

	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public Boolean getIsCancelRelation() {
		return isCancelRelation;
	}

	public void setIsCancelRelation(Boolean isCancelRelation) {
		this.isCancelRelation = isCancelRelation;
	}

}
