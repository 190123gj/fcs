package com.born.fcs.pm.ws.order.file;

import com.born.fcs.pm.ws.enums.FileAttachTypeEnum;
import com.born.fcs.pm.ws.enums.FileTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 已解保项目导入order
 */
public class FileImportOrder implements Serializable {
    private static final long serialVersionUID = -4483128750597450610L;

    private String projectCode;

    private String customerName;

    private FileTypeEnum type;

    private String fileCode;

    private String fileType;

    private String no;

    private String archiveFileName;

    private FileAttachTypeEnum attachType;

    private String filePage;

    private String remark;

    private String handOverDept;

    private String handOverMan;

    private String handOverTime;

    private String viceManager;

    private String receiveDept;

    private String receiveMan;

    private String receiveTime;

    private String index;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public FileTypeEnum getType() {
        return type;
    }

    public void setType(FileTypeEnum type) {
        this.type = type;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getArchiveFileName() {
        return archiveFileName;
    }

    public void setArchiveFileName(String archiveFileName) {
        this.archiveFileName = archiveFileName;
    }

    public FileAttachTypeEnum getAttachType() {
        return attachType;
    }

    public void setAttachType(FileAttachTypeEnum attachType) {
        this.attachType = attachType;
    }

    public String getFilePage() {
        return filePage;
    }

    public void setFilePage(String filePage) {
        this.filePage = filePage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getHandOverTime() {
        return handOverTime;
    }

    public void setHandOverTime(String handOverTime) {
        this.handOverTime = handOverTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
}
