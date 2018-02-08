package com.born.fcs.pm.ws.info.specialpaper;


import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

public class SpecialPaperResultInfo extends BaseToStringInfo {

    private static final long serialVersionUID = 4902283775320368242L;
    private long id;

    private long startNo;

    private long endNo;

    private String receiveManName;

    private String keepingManName;

    private String projectCode;

    private String projectName;

    private Date receiveDate;

    private String invalidReason;

    private String profiles;

    private String remark;

    private Date rawAddTime;

    private String provideManName;

    private String receiptPlace;

    private String receiptMan;

    private String deptName;

    private long pieces;

    private String specialNo;

    private Date recevieDate;

    public Date getRecevieDate() {
        return recevieDate;
    }

    public void setRecevieDate(Date recevieDate) {
        this.recevieDate = recevieDate;
    }

    public String getSpecialNo() {
        return specialNo;
    }

    public void setSpecialNo(String specialNo) {
        this.specialNo = specialNo;
    }

    public long getPieces() {
        return pieces;
    }

    public void setPieces(long pieces) {
        this.pieces = pieces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartNo() {
        return startNo;
    }

    public long getEndNo() {
        return endNo;
    }

    public void setStartNo(long startNo) {
        this.startNo = startNo;
    }

    public void setEndNo(long endNo) {
        this.endNo = endNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getReceiptPlace() {
        return receiptPlace;
    }

    public void setReceiptPlace(String receiptPlace) {
        this.receiptPlace = receiptPlace;
    }

    public String getReceiptMan() {
        return receiptMan;
    }

    public void setReceiptMan(String receiptMan) {
        this.receiptMan = receiptMan;
    }

    public String getProvideManName() {
        return provideManName;
    }

    public void setProvideManName(String provideManName) {
        this.provideManName = provideManName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getProfiles() {
        return profiles;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }


    public String getReceiveManName() {
        return receiveManName;
    }

    public void setReceiveManName(String receiveManName) {
        this.receiveManName = receiveManName;
    }

    public String getKeepingManName() {
        return keepingManName;
    }

    public void setKeepingManName(String keepingManName) {
        this.keepingManName = keepingManName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}
