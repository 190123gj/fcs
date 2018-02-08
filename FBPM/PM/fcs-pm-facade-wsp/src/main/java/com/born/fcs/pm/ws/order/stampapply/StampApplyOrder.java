package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

import java.util.Date;
import java.util.List;


public class StampApplyOrder extends FormOrderBase {

    private static final long serialVersionUID = -2283133566127055280L;

    private long applyId;

    private long replaceFormId;

    private String isReplace;

    private String fileCode;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private String status;

    private String orgIds;

    private String orgNames;

    private String isPass;

    private List<StampAFileOrder> files;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }


    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StampAFileOrder> getFiles() {
        return files;
    }

    public void setFiles(List<StampAFileOrder> files) {
        this.files = files;
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

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getIsReplace() {
        return isReplace;
    }

    public void setIsReplace(String isReplace) {
        this.isReplace = isReplace;
    }

    public long getReplaceFormId() {
        return replaceFormId;
    }

    public void setReplaceFormId(long replaceFormId) {
        this.replaceFormId = replaceFormId;
    }
}
