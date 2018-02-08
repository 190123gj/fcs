package com.born.fcs.pm.dataobject;


import com.yjf.common.lang.util.money.Money;

import java.util.Date;
import java.util.List;

public class CreditRefrerenceApplyFormDO extends FormProjectDO{
    private static final long serialVersionUID = 6525963706274562734L;

    private long id;

    private long formId;

    private long formUserId;

    private String formStatus;

    private String companyName;

    private String projectCode;

    private String afmgApprovalUrl;

    private String address;

    private String busiLicenseNo;

    private String legalPersion;

    private Date establishedTime;

    private Money registerCapital = new Money(0, 0);

    private String busiScope;

    private String busiLicenseUrl;

    private String authUrl;

    private String legalPersionCertFront;

    private String legalPersionCertBack;

    private String loanCardFront;

    private String loanCardBack;

    private long applyManId;

    private String applyManName;

    private String status;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private String applyStatus;

    private String creditReport;

    private String projectName;

    private List<Long> deptIdList;

    long loginUserId;

    private String isNeedAttach;

    private String attachment;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getIsNeedAttach() {
        return isNeedAttach;
    }

    public void setIsNeedAttach(String isNeedAttach) {
        this.isNeedAttach = isNeedAttach;
    }

    public long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getProjectCode() {
        return projectCode;
    }

    @Override
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAfmgApprovalUrl() {
        return afmgApprovalUrl;
    }

    public void setAfmgApprovalUrl(String afmgApprovalUrl) {
        this.afmgApprovalUrl = afmgApprovalUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiLicenseNo() {
        return busiLicenseNo;
    }

    public void setBusiLicenseNo(String busiLicenseNo) {
        this.busiLicenseNo = busiLicenseNo;
    }

    public String getLegalPersion() {
        return legalPersion;
    }

    public void setLegalPersion(String legalPersion) {
        this.legalPersion = legalPersion;
    }

    public Date getEstablishedTime() {
        return establishedTime;
    }

    public void setEstablishedTime(Date establishedTime) {
        this.establishedTime = establishedTime;
    }

    public Money getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Money registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getBusiScope() {
        return busiScope;
    }

    public void setBusiScope(String busiScope) {
        this.busiScope = busiScope;
    }

    public String getBusiLicenseUrl() {
        return busiLicenseUrl;
    }

    public void setBusiLicenseUrl(String busiLicenseUrl) {
        this.busiLicenseUrl = busiLicenseUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getLegalPersionCertFront() {
        return legalPersionCertFront;
    }

    public void setLegalPersionCertFront(String legalPersionCertFront) {
        this.legalPersionCertFront = legalPersionCertFront;
    }

    public String getLegalPersionCertBack() {
        return legalPersionCertBack;
    }

    public void setLegalPersionCertBack(String legalPersionCertBack) {
        this.legalPersionCertBack = legalPersionCertBack;
    }

    public String getLoanCardFront() {
        return loanCardFront;
    }

    public void setLoanCardFront(String loanCardFront) {
        this.loanCardFront = loanCardFront;
    }

    public String getLoanCardBack() {
        return loanCardBack;
    }

    public void setLoanCardBack(String loanCardBack) {
        this.loanCardBack = loanCardBack;
    }

    public long getApplyManId() {
        return applyManId;
    }

    public void setApplyManId(long applyManId) {
        this.applyManId = applyManId;
    }

    public String getApplyManName() {
        return applyManName;
    }

    public void setApplyManName(String applyManName) {
        this.applyManName = applyManName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getCreditReport() {
        return creditReport;
    }

    public void setCreditReport(String creditReport) {
        this.creditReport = creditReport;
    }


}
