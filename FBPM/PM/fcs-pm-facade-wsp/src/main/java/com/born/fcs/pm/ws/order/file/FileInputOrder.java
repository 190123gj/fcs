package com.born.fcs.pm.ws.order.file;


import com.born.fcs.pm.ws.order.base.FormOrderBase;

import java.util.Date;
import java.util.List;

public class FileInputOrder extends FormOrderBase {
    private static final long serialVersionUID = -1037109759562926341L;

    private long inputId;

    private long fileId;

    private String fileCode;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String type;

    private String customerName;

    private Date firstLoanTime;

    private Date filingTime;

    private String handOverDept;

    private String handOverMan;

    private Date handOverTime;

    private String principalMan;

    private String viceManager;

    private String receiveDept;

    private String receiveMan;

    private Date receiveTime;

    private String status;

    private Date rawAddTime;

    private List<FileInputListOrder> inputListOrder;

    public long getInputId() {
        return inputId;
    }

    public void setInputId(long inputId) {
        this.inputId = inputId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getFirstLoanTime() {
        return firstLoanTime;
    }

    public void setFirstLoanTime(Date firstLoanTime) {
        this.firstLoanTime = firstLoanTime;
    }

    public Date getFilingTime() {
        return filingTime;
    }

    public void setFilingTime(Date filingTime) {
        this.filingTime = filingTime;
    }

    public String getHandOverDept() {
        return handOverDept;
    }

    public void setHandOverDept(String handOverDept) {
        this.handOverDept = handOverDept;
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

    public String getPrincipalMan() {
        return principalMan;
    }

    public void setPrincipalMan(String principalMan) {
        this.principalMan = principalMan;
    }

    public String getViceManager() {
        return viceManager;
    }

    public void setViceManager(String viceManager) {
        this.viceManager = viceManager;
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

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public List<FileInputListOrder> getInputListOrder() {
        return inputListOrder;
    }

    public void setInputListOrder(List<FileInputListOrder> inputListOrder) {
        this.inputListOrder = inputListOrder;
    }
}
