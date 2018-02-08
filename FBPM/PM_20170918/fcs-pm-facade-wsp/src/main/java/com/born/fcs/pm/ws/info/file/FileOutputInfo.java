package com.born.fcs.pm.ws.info.file;

import com.born.fcs.pm.ws.enums.FileFormEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;


public class FileOutputInfo extends BaseToStringInfo {

    private long id;

    private long fileId;

    private long formId;

    private String fileCode;

    private String oldFileCode;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private String applyMan;

    private Date applyTime;

    private String applyDept;

    private String outputReason;

    private String outputDetail;

    private String handOverMan;

    private Date handOverTime;

    private String receiveDept;

    private String receiveMan;

    private String handOverDept;

    private Date receiveTime;

    private String outputDetailIds;

    private FileFormEnum fileForm;

    private String attachment;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public FileFormEnum getFileForm() {
        return fileForm;
    }

    public void setFileForm(FileFormEnum fileForm) {
        this.fileForm = fileForm;
    }

    public String getOutputDetailIds() {
        return outputDetailIds;
    }

    public void setOutputDetailIds(String outputDetailIds) {
        this.outputDetailIds = outputDetailIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
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

    public String getApplyMan() {
        return applyMan;
    }

    public void setApplyMan(String applyMan) {
        this.applyMan = applyMan;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(String applyDept) {
        this.applyDept = applyDept;
    }

    public String getOutputReason() {
        return outputReason;
    }

    public void setOutputReason(String outputReason) {
        this.outputReason = outputReason;
    }

    public String getOutputDetail() {
        return outputDetail;
    }

    public void setOutputDetail(String outputDetail) {
        this.outputDetail = outputDetail;
    }

    public String getHandOverMan() {
        return handOverMan;
    }

    public void setHandOverMan(String handOverMan) {
        this.handOverMan = handOverMan;
    }

    public Date getHandOverTime() {
        return handOverTime;
    }

    public void setHandOverTime(Date handOverTime) {
        this.handOverTime = handOverTime;
    }

    public String getReceiveDept() {
        return receiveDept;
    }

    public void setReceiveDept(String receiveDept) {
        this.receiveDept = receiveDept;
    }

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    public String getHandOverDept() {
        return handOverDept;
    }

    public void setHandOverDept(String handOverDept) {
        this.handOverDept = handOverDept;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getOldFileCode() {
        return oldFileCode;
    }

    public void setOldFileCode(String oldFileCode) {
        this.oldFileCode = oldFileCode;
    }
}
