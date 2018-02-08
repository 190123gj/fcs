package com.born.fcs.am.ws.info.pledgeasset;

import java.util.Date;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class AssetRelationProjectInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4793393213347472054L;

	private long id;

	private long assetsId;

	private AssetStatusEnum assetsStatus;

	private String projectCode;

	private String projectName;

	private long customerId;

	private String customerName;

	private String busiType;

	private String busiTypeName;

	private Date rawUpdateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}

	public AssetStatusEnum getAssetsStatus() {
		return assetsStatus;
	}

	public void setAssetsStatus(AssetStatusEnum assetsStatus) {
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

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public String getBusiTypeName() {
		return busiTypeName;
	}

	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}

}
