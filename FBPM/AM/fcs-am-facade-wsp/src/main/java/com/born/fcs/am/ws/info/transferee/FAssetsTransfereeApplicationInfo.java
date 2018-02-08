package com.born.fcs.am.ws.info.transferee;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

public class FAssetsTransfereeApplicationInfo extends FormVOInfo {

	/**
	 * 资产受让
	 */
	private static final long serialVersionUID = -6991489383740384259L;

	private long id;

	private long formId;

	private String projectCode;

	private String projectName;

	private Money transfereePrice = new Money(0, 0);

	private Date transfereeTime;

	private String transferCompany;

	private BooleanEnum isTrusteeLiquidate;

	private Date liquidateTime;

	private Money liquidaterPrice = new Money(0, 0);

	private String remark;

	private String attach;

	private int sortOrder;

	private BooleanEnum isCloseMessage;

	private Date rawAddTime;

	private Date rawUpdateTime;

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

	public BooleanEnum getIsTrusteeLiquidate() {
		return isTrusteeLiquidate;
	}

	public void setIsTrusteeLiquidate(BooleanEnum isTrusteeLiquidate) {
		this.isTrusteeLiquidate = isTrusteeLiquidate;
	}

	public Date getLiquidateTime() {
		return liquidateTime;
	}

	public void setLiquidateTime(Date liquidateTime) {
		this.liquidateTime = liquidateTime;
	}

	public Money getLiquidaterPrice() {
		return liquidaterPrice;
	}

	public void setLiquidaterPrice(Money liquidaterPrice) {
		this.liquidaterPrice = liquidaterPrice;
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

	public BooleanEnum getIsCloseMessage() {
		return isCloseMessage;
	}

	public void setIsCloseMessage(BooleanEnum isCloseMessage) {
		this.isCloseMessage = isCloseMessage;
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

}
