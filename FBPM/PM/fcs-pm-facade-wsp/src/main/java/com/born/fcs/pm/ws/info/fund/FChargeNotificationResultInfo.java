package com.born.fcs.pm.ws.info.fund;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.ChargeBasisEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmInfo;
import com.yjf.common.lang.util.money.Money;

public class FChargeNotificationResultInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 4070117446803269154L;
	private long formId;
	
	private long formUserId;
	
	private FormStatusEnum formStatus;
	
	private long notificationId;
	
	private String projectCode;
	
	private String projectName;
	
	private String customerName;
	
	private String submitManName;
	
	private Money chargeAmount = new Money(0, 0);

	private Money actualAmount = new Money(0, 0);
	
	private Date rawAddTime;
	
	private String busiType;
	
	private String busiTypeName;
	
	List<FChargeNotificationFeeInfo> feeList;

	private ProjectStatusEnum projectStatus;

	private ChargeBasisEnum chargeBasis;

	//财务确认信息
	FFinanceAffirmInfo firmInfo;

	public FFinanceAffirmInfo getFirmInfo() {
		return firmInfo;
	}

	public void setFirmInfo(FFinanceAffirmInfo firmInfo) {
		this.firmInfo = firmInfo;
	}

	public Money getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Money actualAmount) {
		this.actualAmount = actualAmount;
	}

	public ChargeBasisEnum getChargeBasis() {
		return chargeBasis;
	}

	public void setChargeBasis(ChargeBasisEnum chargeBasis) {
		this.chargeBasis = chargeBasis;
	}

	public ProjectStatusEnum getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatusEnum projectStatus) {
		this.projectStatus = projectStatus;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getFormUserId() {
		return formUserId;
	}
	
	public void setFormUserId(long formUserId) {
		this.formUserId = formUserId;
	}
	
	public FormStatusEnum getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
	}
	
	public long getNotificationId() {
		return notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
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
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	public List<FChargeNotificationFeeInfo> getFeeList() {
		return this.feeList;
	}
	
	public void setFeeList(List<FChargeNotificationFeeInfo> feeList) {
		this.feeList = feeList;
	}
	
}
