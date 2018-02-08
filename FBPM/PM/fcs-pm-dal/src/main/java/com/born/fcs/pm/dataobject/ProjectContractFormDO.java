package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

public class ProjectContractFormDO extends FormProjectDO {
	
	private static final long serialVersionUID = 4221205203378564785L;
	private long contractId;
	
	private long id;
	
	private long formId;
	
	private long formUserId;
	
	private String formStatus;
	
	private String contractCode;

	private String contractCode2;
	
	private String contractName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String customerName;
	
	private String projectCode;
	
	private String projectName;
	
	private String projectStatus;
	
	private String contractStatus;
	
	private Date signedTime;
	
	private Date rawAddTime;
	
	private Money loannedAmount = new Money(0, 0);
	
	private String isChargeNotification;
	
	private List<Long> deptIdList;
	
	long loginUserId;
	
	private String contractType;
	
	private String creditMeasure;
	
	private String cnt;
	
	private String likeContractCodeOrName;
	
	private String chooseProject;
	
	private String isMain;
	
	private String basisOfDecision;
	
	private String applyType;
	
	private String letterType;
	
	private String contractScanUrl;
	
	private String freeFlow;
	
	private String fileUrl;
	
	private String courtRulingUrl;

	private String exceptContractStatus;

	private String remark;

	private String projectCourtRuling;

	private long num;

	private String isNeedStamp;

	public String getIsNeedStamp() {
		return isNeedStamp;
	}

	public void setIsNeedStamp(String isNeedStamp) {
		this.isNeedStamp = isNeedStamp;
	}

	public String getContractCode2() {
		return contractCode2;
	}

	public void setContractCode2(String contractCode2) {
		this.contractCode2 = contractCode2;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getProjectCourtRuling() {
		return projectCourtRuling;
	}

	public void setProjectCourtRuling(String projectCourtRuling) {
		this.projectCourtRuling = projectCourtRuling;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExceptContractStatus() {
		return exceptContractStatus;
	}

	public void setExceptContractStatus(String exceptContractStatus) {
		this.exceptContractStatus = exceptContractStatus;
	}

	public String getCourtRulingUrl() {
		return courtRulingUrl;
	}
	
	public void setCourtRulingUrl(String courtRulingUrl) {
		this.courtRulingUrl = courtRulingUrl;
	}
	
	public String getFileUrl() {
		return fileUrl;
	}
	
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getFreeFlow() {
		return freeFlow;
	}
	
	public void setFreeFlow(String freeFlow) {
		this.freeFlow = freeFlow;
	}
	
	public String getContractScanUrl() {
		return contractScanUrl;
	}
	
	public void setContractScanUrl(String contractScanUrl) {
		this.contractScanUrl = contractScanUrl;
	}
	
	public String getLetterType() {
		return letterType;
	}
	
	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}
	
	public String getApplyType() {
		return applyType;
	}
	
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	public String getBasisOfDecision() {
		return basisOfDecision;
	}
	
	public void setBasisOfDecision(String basisOfDecision) {
		this.basisOfDecision = basisOfDecision;
	}
	
	public String getIsMain() {
		return isMain;
	}
	
	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	
	public String getLikeContractCodeOrName() {
		return likeContractCodeOrName;
	}
	
	public void setLikeContractCodeOrName(String likeContractCodeOrName) {
		this.likeContractCodeOrName = likeContractCodeOrName;
	}
	
	public String getCnt() {
		return cnt;
	}
	
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	
	public String getCreditMeasure() {
		return creditMeasure;
	}
	
	public void setCreditMeasure(String creditMeasure) {
		this.creditMeasure = creditMeasure;
	}
	
	public String getContractType() {
		return contractType;
	}
	
	public void setContractType(String contractType) {
		this.contractType = contractType;
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
	
	public String getIsChargeNotification() {
		return isChargeNotification;
	}
	
	public void setIsChargeNotification(String isChargeNotification) {
		this.isChargeNotification = isChargeNotification;
	}
	
	public long getContractId() {
		return contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	public String getContractCode() {
		return contractCode;
	}
	
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	public String getContractName() {
		return contractName;
	}
	
	public void setContractName(String contractName) {
		this.contractName = contractName;
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
	public String getCustomerName() {
		return customerName;
	}
	
	@Override
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Override
	public String getProjectCode() {
		return projectCode;
	}
	
	@Override
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getContractStatus() {
		return contractStatus;
	}
	
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	@Override
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	@Override
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	@Override
	public String getProjectName() {
		return projectName;
	}
	
	@Override
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectStatus() {
		return projectStatus;
	}
	
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	@Override
	public long getFormId() {
		return formId;
	}
	
	@Override
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public Date getSignedTime() {
		return signedTime;
	}
	
	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}
	
	public Money getLoannedAmount() {
		return loannedAmount;
	}
	
	public void setLoannedAmount(Money loannedAmount) {
		this.loannedAmount = loannedAmount;
	}
	
	public String getChooseProject() {
		return chooseProject;
	}
	
	public void setChooseProject(String chooseProject) {
		this.chooseProject = chooseProject;
	}
}
