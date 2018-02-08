package com.bornsoft.facade.info;

import com.bornsoft.pub.enums.ApixServiceEnum;
import com.bornsoft.utils.base.BornInfoBase;

public class OutApiRequestLogInfo extends BornInfoBase {

	private long logId;
	private String orderNo;
	/***/
	private ApixServiceEnum service;
	/***/
	private String rowAddTime;
	/***/
	private String resultCode;
	/***/
	private String resultMessage;
	/***/
	private String reqParam;

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public ApixServiceEnum getService() {
		return service;
	}

	public void setService(ApixServiceEnum service) {
		this.service = service;
	}

	public String getRowAddTime() {
		return rowAddTime;
	}

	public void setRowAddTime(String rowAddTime) {
		this.rowAddTime = rowAddTime;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getReqParam() {
		return reqParam;
	}

	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
