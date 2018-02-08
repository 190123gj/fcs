package com.born.fcs.pm.ws.order.file;

import com.born.fcs.pm.ws.enums.FileFormEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

import java.util.Date;


public class FileOutputOrder extends FormOrderBase {


    private static final long serialVersionUID = 4355802395805978018L;
    private Long id;

    private long fileId;

    private String fileCode;

    private String oldFileCode;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private long applyManId;

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

    private String submitStatus;

    private FileFormEnum fileForm;

    private String applyDeptCode;

    private String attachment;

    public String getOldFileCode() {
        return oldFileCode;
    }

    public void setOldFileCode(String oldFileCode) {
        this.oldFileCode = oldFileCode;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getApplyDeptCode() {
        return applyDeptCode;
    }

    public void setApplyDeptCode(String applyDeptCode) {
        this.applyDeptCode = applyDeptCode;
    }

    public FileFormEnum getFileForm() {
        return fileForm;
    }

    public long getApplyManId() {
        return applyManId;
    }

    public void setApplyManId(long applyManId) {
        this.applyManId = applyManId;
    }

    public void setFileForm(FileFormEnum fileForm) {
        this.fileForm = fileForm;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getOutputDetailIds() {
        return outputDetailIds;
    }

    public void setOutputDetailIds(String outputDetailIds) {
        this.outputDetailIds = outputDetailIds;
    }

    public Long getId() {
        return id;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
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

    public void setId(Long id) {
        this.id = id;
    }
}
