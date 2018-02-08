package com.born.fcs.pm.ws.info.stampapply;

import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

import java.util.Date;

/**
 *
 * 用印管理列表信息
 *
 * @author heh
 *
 */
public class StampApplyProjectResultInfo extends FormVOInfo {


    private static final long serialVersionUID = 6596615851448722751L;

    private long applyId;

    private long formId;

    private String applyCode;

    private long fileId;

    private String fileCode;

    private String projectCode;

    private String projectName;

    private String customerName;

    private String  submitStatus;

    private String fileName;

    private int legalChapterNum;

    private int cachetNum;

    private Date rawAddTime;

    private String formUserName;

    private String formDeptName;

    private FormStatusEnum formStatus;

    private String replaceFormStatus;

    private long formUserId;

    private String projectStatus;

    private StampSourceEnum source;

    private String contractCode;

    public StampSourceEnum getSource() {
        return source;
    }

    public void setSource(StampSourceEnum source) {
        this.source = source;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public FormStatusEnum getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatusEnum formStatus) {
        this.formStatus = formStatus;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public String getFormUserName() {
        return formUserName;
    }

    public void setFormUserName(String formUserName) {
        this.formUserName = formUserName;
    }

    public String getFormDeptName() {
        return formDeptName;
    }

    public void setFormDeptName(String formDeptName) {
        this.formDeptName = formDeptName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLegalChapterNum() {
        return legalChapterNum;
    }

    public void setLegalChapterNum(int legalChapterNum) {
        this.legalChapterNum = legalChapterNum;
    }

    public int getCachetNum() {
        return cachetNum;
    }

    public void setCachetNum(int cachetNum) {
        this.cachetNum = cachetNum;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getReplaceFormStatus() {
        return replaceFormStatus;
    }

    public void setReplaceFormStatus(String replaceFormStatus) {
        this.replaceFormStatus = replaceFormStatus;
    }

    public long getFormUserId() {
        return formUserId;
    }

    public void setFormUserId(long formUserId) {
        this.formUserId = formUserId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
}
