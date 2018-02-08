package com.born.fcs.pm.ws.info.contract;

import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

import java.util.Date;

/**
 *
 * 授信前管理-合同管理-合同列表
 *
 * @author heh
 *
 */
public class ProjectContractResultInfo extends FormVOInfo {

    private static final long serialVersionUID = -7626817039818459453L;
    private long contractId;

    private long id;

    private long formId;

    private long formUserId;

    private String formStatus;

    /**本系统合同编号，合同申请单页面显示合同ID*/
    private String contractCode;
    /**手输入的合同编号，合同申请单显示合同编号*/
    private String contractCode2;

    private String contractName;

    private String busiType;

    private String customerName;

    private String projectCode;

    private String projectName;

    private ContractStatusEnum contractStatus;//合同状态

    private String busiTypeName;

    private String projectStatus;

    private Date signedTime;//签订时间

    private Date rawAddTime;

    private Money loannedAmount=new Money(0,0);

    private ContractTypeEnum contractType;

    private String creditMeasure;

    private String cnt;

    private String isCanStampApply;

    private String isMain;//是否主合同

    private String basisOfDecision;//决策依据(存的签报的formId,null为项目批复)

    private ProjectContractTypeEnum applyType;//申请类别

    private LetterTypeEnum letterType;

    private String contractScanUrl;

    private String freeFlow;

    private String fileUrl;

    private String courtRulingUrl;

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

    public LetterTypeEnum getLetterType() {
        return letterType;
    }

    public void setLetterType(LetterTypeEnum letterType) {
        this.letterType = letterType;
    }

    public ProjectContractTypeEnum getApplyType() {
        return applyType;
    }

    public void setApplyType(ProjectContractTypeEnum applyType) {
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

    public String getIsCanStampApply() {
        return isCanStampApply;
    }

    public void setIsCanStampApply(String isCanStampApply) {
        this.isCanStampApply = isCanStampApply;
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

    public ContractTypeEnum getContractType() {
        return contractType;
    }

    public void setContractType(ContractTypeEnum contractType) {
        this.contractType = contractType;
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

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public ContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public String getBusiTypeName() {
        return busiTypeName;
    }

    public void setBusiTypeName(String busiTypeName) {
        this.busiTypeName = busiTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFormUserId() {
        return formUserId;
    }

    public void setFormUserId(long formUserId) {
        this.formUserId = formUserId;
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
}
