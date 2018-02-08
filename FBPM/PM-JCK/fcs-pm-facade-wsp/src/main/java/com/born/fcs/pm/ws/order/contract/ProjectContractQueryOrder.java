package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.LetterTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectContractTypeEnum;

import java.util.Date;


public class ProjectContractQueryOrder extends QueryProjectBase {

    private static final long serialVersionUID = 8025694766408302452L;
    private String contractCode;

    private String contractName;

    private String busiType;

    private String projectCode;

    private String customerName;

    private String contractStatus;

    private Date operateDate;

    private String isChargeNotification;

    private String sortOrder;

    private String sortCol;

    private String busiTypeName;

    private String likeContractCodeOrName;

    private String chooseProject;

    private String projectName;

    private String isMain;

    private ProjectContractTypeEnum applyType;

    private LetterTypeEnum letterType;

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

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getChooseProject() {
        return chooseProject;
    }

    public void setChooseProject(String chooseProject) {
        this.chooseProject = chooseProject;
    }

    public String getBusiTypeName() {
        return busiTypeName;
    }

    public void setBusiTypeName(String busiTypeName) {
        this.busiTypeName = busiTypeName;
    }

    @Override
    public String getSortOrder() {
        return sortOrder;
    }

    @Override
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String getSortCol() {
        return sortCol;
    }

    @Override
    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }

    public String getIsChargeNotification() {
        return isChargeNotification;
    }

    public void setIsChargeNotification(String isChargeNotification) {
        this.isChargeNotification = isChargeNotification;
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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getLikeContractCodeOrName() {
        return likeContractCodeOrName;
    }

    public void setLikeContractCodeOrName(String likeContractCodeOrName) {
        this.likeContractCodeOrName = likeContractCodeOrName;
    }
}
