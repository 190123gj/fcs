package com.born.fcs.pm.ws.info.stampapply;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;
import java.util.List;

/**
 * 用印申请
 *
 *
 * @author hehao
 *
 */
public class StampApplyInfo extends BaseToStringInfo {

    private static final long serialVersionUID = -205630991693231528L;
    private long applyId;

    private long formId;

    private long replaceFormId;

    private String applyCode;

    private String fileCode;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private String status;

    private List<StampAFileInfo> stampAFiles;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
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

    public List<StampAFileInfo> getStampAFiles() {
        return stampAFiles;
    }

    public void setStampAFiles(List<StampAFileInfo> stampAFiles) {
        this.stampAFiles = stampAFiles;
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

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public long getReplaceFormId() {
        return replaceFormId;
    }

    public void setReplaceFormId(long replaceFormId) {
        this.replaceFormId = replaceFormId;
    }
}
