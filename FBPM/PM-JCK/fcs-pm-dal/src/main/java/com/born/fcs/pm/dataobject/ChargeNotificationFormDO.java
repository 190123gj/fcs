package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

public class ChargeNotificationFormDO extends SimpleFormProjectDO {
	private static final long serialVersionUID = 8947560622434619756L;
	
	private long formId;
	
	private long formUserId;
	
	private String formStatus;
	
	private long notificationId;
	
	private Money chargeAmount = new Money(0, 0);
	
	private Date rawAddTime;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String submitManName;

	private List<Long> deptIdList;

	long loginUserId;

	private String projectStatus;

	private String chargeBasis;

	public String getChargeBasis() {
		return chargeBasis;
	}

	public void setChargeBasis(String chargeBasis) {
		this.chargeBasis = chargeBasis;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public List<Long> getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	@Override
	public long getFormId() {
		return formId;
	}
	
	@Override
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	@Override
	public long getFormUserId() {
		return formUserId;
	}
	
	@Override
	public void setFormUserId(long formUserId) {
		this.formUserId = formUserId;
	}
	
	@Override
	public String getFormStatus() {
		return formStatus;
	}
	
	@Override
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public long getNotificationId() {
		return notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	
	public String getSubmitManName() {
		return submitManName;
	}
	
	public void setSubmitManName(String submitManName) {
		this.submitManName = submitManName;
	}
	
	public Money getChargeAmount() {
		return chargeAmount;
	}
	
	public void setChargeAmount(Money chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	@Override
	public String getBusiType() {
		return busiType;
	}
	
	@Override
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	@Override
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	@Override
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
}
