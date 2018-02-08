package com.born.fcs.pm.ws.order.file;

import com.born.fcs.pm.ws.enums.FileTypeEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.Date;
import java.util.List;


public class FileOrder extends FormOrderBase {
    private static final long serialVersionUID = 3780876176638690002L;
    private Long fileId;

    //private List<FileListOrder> fileListOrder;
    private List<FileInputListOrder> fileListOrder;

    private String fileCode;

    private FileTypeEnum type;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private long busiManagerId;

    private long busiManagerName;

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

    private Date rawUpdateTime;

    private Date firstLoanTime;

    private String busiType;

    private String deptCode;

    private String doing;

    private FileTypeEnum currType;

    private String submitStatus;

    private String fileCheckStatus;

    public String getFileCheckStatus() {
        return fileCheckStatus;
    }

    public void setFileCheckStatus(String fileCheckStatus) {
        this.fileCheckStatus = fileCheckStatus;
    }

    public String getDoing() {
        return doing;
    }

    public void setDoing(String doing) {
        this.doing = doing;
    }

    public FileTypeEnum getCurrType() {
        return currType;
    }

    public void setCurrType(FileTypeEnum currType) {
        this.currType = currType;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public FileTypeEnum getType() {
        return type;
    }

    public void setType(FileTypeEnum type) {
        this.type = type;
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

    public long getBusiManagerId() {
        return busiManagerId;
    }

    public void setBusiManagerId(long busiManagerId) {
        this.busiManagerId = busiManagerId;
    }

    public long getBusiManagerName() {
        return busiManagerName;
    }

    public void setBusiManagerName(long busiManagerName) {
        this.busiManagerName = busiManagerName;
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

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    public List<FileInputListOrder> getFileListOrder() {
        return fileListOrder;
    }

    public void setFileListOrder(List<FileInputListOrder> fileListOrder) {
        this.fileListOrder = fileListOrder;
    }

    public Date getFirstLoanTime() {
        return firstLoanTime;
    }

    public void setFirstLoanTime(Date firstLoanTime) {
        this.firstLoanTime = firstLoanTime;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }


}
