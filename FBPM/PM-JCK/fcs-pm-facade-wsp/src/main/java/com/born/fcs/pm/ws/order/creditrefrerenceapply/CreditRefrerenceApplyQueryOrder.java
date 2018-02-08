package com.born.fcs.pm.ws.order.creditrefrerenceapply;

import com.born.fcs.pm.ws.base.QueryProjectBase;

import java.util.Date;

/**
 * 征信管理查询Order
 *
 * @author heh
 *
 */
public class CreditRefrerenceApplyQueryOrder extends QueryProjectBase {

    private static final long serialVersionUID = 8301310151282370203L;
    private String companyName;

    private String busiLicenseNo;

    private String applyManName;

    private String projectName;

    private String projectCode;

    private String status;

    private Date operateDate;

    private String sortOrder;

    private String sortCol;

    private String formId;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusiLicenseNo() {
        return busiLicenseNo;
    }

    public void setBusiLicenseNo(String busiLicenseNo) {
        this.busiLicenseNo = busiLicenseNo;
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

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
