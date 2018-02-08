package com.born.fcs.pm.ws.order.file;


import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class FileQueryOrder extends ProjectQueryOrder{


    private String projectCode;

    private String fileCode;

    private String oldFileCode;

    private String customerName;

    private String fileStatus;

    private String formStatus;

    private Date rawAddTime;

    private String fileName;

    private String applyType;

    private String archiveFileName;

    private String fileType;

    private Date startTime;

    private Date endTime;

    private long applyManId;

    private String type;

    private BooleanEnum isHasLoanAmount;

    private BooleanEnum isFileAdmin;

    private String xinhuiDeptCode;

    private BooleanEnum isXinHui;

    public String getOldFileCode() {
        return oldFileCode;
    }

    public void setOldFileCode(String oldFileCode) {
        this.oldFileCode = oldFileCode;
    }

    public BooleanEnum getIsXinHui() {
        return isXinHui;
    }

    public void setIsXinHui(BooleanEnum isXinHui) {
        this.isXinHui = isXinHui;
    }

    public String getXinhuiDeptCode() {
        return xinhuiDeptCode;
    }

    public void setXinhuiDeptCode(String xinhuiDeptCode) {
        this.xinhuiDeptCode = xinhuiDeptCode;
    }

    public BooleanEnum getIsFileAdmin() {
        return isFileAdmin;
    }

    public void setIsFileAdmin(BooleanEnum isFileAdmin) {
        this.isFileAdmin = isFileAdmin;
    }

    public BooleanEnum getIsHasLoanAmount() {
        return isHasLoanAmount;
    }

    public void setIsHasLoanAmount(BooleanEnum isHasLoanAmount) {
        this.isHasLoanAmount = isHasLoanAmount;
    }

    public long getApplyManId() {
        return applyManId;
    }

    public void setApplyManId(long applyManId) {
        this.applyManId = applyManId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getArchiveFileName() {
        return archiveFileName;
    }

    public void setArchiveFileName(String archiveFileName) {
        this.archiveFileName = archiveFileName;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
}
