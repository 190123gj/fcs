package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

import java.util.Date;

/**
 * Created by Administrator on 2016/4/6.
 */
public class StampApplyQueryOrder extends QueryProjectBase {

    private static final long serialVersionUID = 2612958206858082391L;

    private String customerName;

    private String applyCode;

    private String fileName;

    private String approvalStatus;

    private Date applyDate;

    private String projectCode;

    private String f_status;

    private String sortOrder;

    private String sortCol;

    //是否需要查看渠道合同 (IS/NO)
    private BooleanEnum isNeedChannelContract;

    private String contractCode;

    private String formUserName;

    public String getFormUserName() {
        return formUserName;
    }

    public void setFormUserName(String formUserName) {
        this.formUserName = formUserName;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public BooleanEnum getIsNeedChannelContract() {
        return isNeedChannelContract;
    }

    public void setIsNeedChannelContract(BooleanEnum isNeedChannelContract) {
        this.isNeedChannelContract = isNeedChannelContract;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getF_status() {
        return f_status;
    }

    public void setF_status(String f_status) {
        this.f_status = f_status;
    }
}