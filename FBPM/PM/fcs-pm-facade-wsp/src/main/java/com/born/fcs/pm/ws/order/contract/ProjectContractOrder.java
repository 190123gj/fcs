package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.enums.ProjectContractTypeEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

import java.util.Date;
import java.util.List;


public class ProjectContractOrder extends FormOrderBase {
    private static final long serialVersionUID = -4105490665021794649L;

    private long contractId;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private List<ProjectContractItemOrder> item;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private String busiTypeCode;

    private String busiType;

    private String deptCode;

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

    public List<ProjectContractItemOrder> getItem() {
        return item;
    }

    public void setItem(List<ProjectContractItemOrder> item) {
        this.item = item;
    }

    public String getBusiTypeCode() {
        return busiTypeCode;
    }

    public void setBusiTypeCode(String busiTypeCode) {
        this.busiTypeCode = busiTypeCode;
    }

    @Override
    public String getDeptCode() {
        return deptCode;
    }

    @Override
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }
}
