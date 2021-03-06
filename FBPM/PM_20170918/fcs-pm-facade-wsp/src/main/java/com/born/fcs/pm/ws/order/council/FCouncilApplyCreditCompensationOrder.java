package com.born.fcs.pm.ws.order.council;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;

public class FCouncilApplyCreditCompensationOrder extends ValidateOrderBase {
	private static final long serialVersionUID = -843077972898204002L;
	
	private long id;
	
	private long handleId;
	
	private String projectCode;
	
	private String compensatoryPrincipal;

	private String compensatoryInterest;

	private String compensatoryInterestOther;

	private String otherRemark;
	
	private Date expireDate;
	
	private Date quasiCompensatoryTime;
	
	private JSONObject jsonData;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public boolean isNull() {
		return null == projectCode && null == compensatoryInterestOther && null == expireDate
				&& null == quasiCompensatoryTime;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getHandleId() {
		return this.handleId;
	}
	
	public void setHandleId(long handleId) {
		this.handleId = handleId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCompensatoryPrincipal() {
		return this.compensatoryPrincipal;
	}
	
	public void setCompensatoryPrincipal(String compensatoryPrincipal) {
		this.compensatoryPrincipal = compensatoryPrincipal;
	}
	
	public String getCompensatoryInterestOther() {
		return this.compensatoryInterestOther;
	}
	
	public void setCompensatoryInterestOther(String compensatoryInterestOther) {
		this.compensatoryInterestOther = compensatoryInterestOther;
	}
	
	public Date getExpireDate() {
		return this.expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public void setExpireDateStr(String expireDateStr) {
		this.expireDate = DateUtil.strToDtSimpleFormat(expireDateStr);
	}
	
	public Date getQuasiCompensatoryTime() {
		return this.quasiCompensatoryTime;
	}
	
	public void setQuasiCompensatoryTime(Date quasiCompensatoryTime) {
		this.quasiCompensatoryTime = quasiCompensatoryTime;
	}
	
	public void setQuasiCompensatoryTimeStr(String quasiCompensatoryTimeStr) {
		this.quasiCompensatoryTime = DateUtil.strToDtSimpleFormat(quasiCompensatoryTimeStr);
	}
	
	public JSONObject getJsonData() {
		if (this.jsonData == null)
			this.jsonData = new JSONObject();
		return this.jsonData;
	}
	
	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public String getCompensatoryInterest() {
		return this.compensatoryInterest;
	}

	public void setCompensatoryInterest(String compensatoryInterest) {
		this.compensatoryInterest = compensatoryInterest;
	}
	
	public String getOtherRemark() {
		return this.otherRemark;
	}

	public void setOtherRemark(String otherRemark) {
		this.otherRemark = otherRemark;
	}
	
}
