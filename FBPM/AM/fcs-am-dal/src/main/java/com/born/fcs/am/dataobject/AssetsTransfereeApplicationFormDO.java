/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.dataobject;

// auto generated imports
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

public class AssetsTransfereeApplicationFormDO extends SimpleFormDO {
	private static final long serialVersionUID = -4282603875229233564L;

	// ========== properties ==========

	private long id;

	private long formId;

	private String projectCode;

	private String projectName;

	private Money transfereePrice = new Money(0, 0);

	private Date transfereeTime;

	private String transferCompany;

	private String isTrusteeLiquidate;

	private Date liquidateTime;

	private String liquidateTimeStart;// 清收时间

	private String liquidateTimeEnd;

	private Money liquidaterPrice = new Money(0, 0);

	private String isCloseMessage;

	private String remark;

	private String attach;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

	// 查询条件部分

	List<Long> deptIdList;

	List<String> statusList;

	long loginUserId;

	long pageSize;

	long limitStart;

	String sortCol;

	// ========== getters and setters ==========

	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
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

	public Money getTransfereePrice() {
		return transfereePrice;
	}

	public void setTransfereePrice(Money transfereePrice) {
		this.transfereePrice = transfereePrice;
	}

	public Date getTransfereeTime() {
		return transfereeTime;
	}

	public void setTransfereeTime(Date transfereeTime) {
		this.transfereeTime = transfereeTime;
	}

	public String getTransferCompany() {
		return transferCompany;
	}

	public void setTransferCompany(String transferCompany) {
		this.transferCompany = transferCompany;
	}

	public String getIsTrusteeLiquidate() {
		return isTrusteeLiquidate;
	}

	public void setIsTrusteeLiquidate(String isTrusteeLiquidate) {
		this.isTrusteeLiquidate = isTrusteeLiquidate;
	}

	public Date getLiquidateTime() {
		return liquidateTime;
	}

	public void setLiquidateTime(Date liquidateTime) {
		this.liquidateTime = liquidateTime;
	}

	public String getLiquidateTimeStart() {
		return liquidateTimeStart;
	}

	public void setLiquidateTimeStart(String liquidateTimeStart) {
		this.liquidateTimeStart = liquidateTimeStart;
	}

	public String getLiquidateTimeEnd() {
		return liquidateTimeEnd;
	}

	public void setLiquidateTimeEnd(String liquidateTimeEnd) {
		this.liquidateTimeEnd = liquidateTimeEnd;
	}

	public Money getLiquidaterPrice() {
		return liquidaterPrice;
	}

	public void setLiquidaterPrice(Money liquidaterPrice) {
		this.liquidaterPrice = liquidaterPrice;
	}

	public String getIsCloseMessage() {
		return isCloseMessage;
	}

	public void setIsCloseMessage(String isCloseMessage) {
		this.isCloseMessage = isCloseMessage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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

	public List<Long> getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}

	public String getSortCol() {
		return sortCol;
	}

	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}

}
