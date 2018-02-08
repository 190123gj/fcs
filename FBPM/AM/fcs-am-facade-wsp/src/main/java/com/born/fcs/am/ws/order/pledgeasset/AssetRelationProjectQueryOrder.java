/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.pledgeasset;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 抵质押品分类Order
 *
 * @author jil
 *
 */
public class AssetRelationProjectQueryOrder extends QueryPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8233757401359846573L;

	private Long id;

	private long assetsId;

	private String assetsStatus;

	private String projectCode;

	private String projectName;

	private long customerId;

	private String customerName;

	private String busiType;

	private String busiTypeName;

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

}
