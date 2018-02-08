package com.born.fcs.pm.ws.info.contract;

import com.born.fcs.pm.ws.enums.ProjectContractTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;
import java.util.List;

/**
 * 项目合同集
 *
 *
 * @author hehao
 *
 */
public class ProjectContrctInfo extends BaseToStringInfo {
    private static final long serialVersionUID = -4788378383318886033L;

    private long contractId;

    private long formId;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private List<ProjectContractItemInfo> projectContractItemInfos;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private String freeFlow;

    private ProjectContractTypeEnum applyType;

    public ProjectContractTypeEnum getApplyType() {
        return applyType;
    }

    public void setApplyType(ProjectContractTypeEnum applyType) {
        this.applyType = applyType;
    }

    public String getFreeFlow() {
        return freeFlow;
    }

    public void setFreeFlow(String freeFlow) {
        this.freeFlow = freeFlow;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public List<ProjectContractItemInfo> getProjectContractItemInfos() {
        return projectContractItemInfos;
    }

    public void setProjectContractItemInfos(List<ProjectContractItemInfo> projectContractItemInfos) {
        this.projectContractItemInfos = projectContractItemInfos;
    }
}
