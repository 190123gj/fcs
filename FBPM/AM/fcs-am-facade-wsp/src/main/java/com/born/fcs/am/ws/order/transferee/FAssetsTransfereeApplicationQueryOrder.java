package com.born.fcs.am.ws.order.transferee;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryFormBase;

/**
 * 资产受让查询Order
 * 
 * @author Ji
 */
public class FAssetsTransfereeApplicationQueryOrder extends QueryFormBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2056222389553176079L;

	private long id;

	private String projectCode;

	private String projectName;

	private String transfereeTime;

	private String isTrusteeLiquidate;

	private String liquidateTimeStart;// 清收时间

	private String liquidateTimeEnd;
	/**
	 * 客户经理
	 */
	private long busiManagerId;

	private String busiManagerName;

	private Date submitTimeStart;

	private Date submitTimeEnd;

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

	public String getTransfereeTime() {
		return transfereeTime;
	}

	public void setTransferTime(String transfereeTime) {
		this.transfereeTime = transfereeTime;
	}

	public String getIsTrusteeLiquidate() {
		return isTrusteeLiquidate;
	}

	public void setIsTrusteeLiquidate(String isTrusteeLiquidate) {
		this.isTrusteeLiquidate = isTrusteeLiquidate;
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

	public long getBusiManagerId() {
		return busiManagerId;
	}

	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}

	public String getBusiManagerName() {
		return busiManagerName;
	}

	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}

	public Date getSubmitTimeStart() {
		return submitTimeStart;
	}

	public void setSubmitTimeStart(Date submitTimeStart) {
		this.submitTimeStart = submitTimeStart;
	}

	public Date getSubmitTimeEnd() {
		return submitTimeEnd;
	}

	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}

}
