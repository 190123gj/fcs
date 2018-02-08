package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

public class StampApplyFormProjeactDO extends FormProjectDO {
	
	private static final long serialVersionUID = 4471211930874166654L;
	
	private long applyId;

    private long formId;

    private String formCode;

    private String applyCode;

    private long fileId;

	private String fileCode;

	private String projectCode;

	private String projectName;

	private String customerName;

	private String submitStatus;

	private String fileName;

	private int legalChapterNum;

	private int cachetNum;

    private Date rawAddTime;

    private String formUserName;

    private String formDeptName;

    private String formStatus;

    private String replaceFileName;

    private long formUserId;

    private List<Long> deptIdList;

    long loginUserId;

    private String projectStatus;

    private String replaceFormStatus;

    private String source;

    private String isNeedChannelContract;

    private long draftUserId;

    private String contractCode;

    public long getDraftUserId() {
        return draftUserId;
    }

    public void setDraftUserId(long draftUserId) {
        this.draftUserId = draftUserId;
    }

    public String getIsNeedChannelContract() {
        return isNeedChannelContract;
    }

    public void setIsNeedChannelContract(String isNeedChannelContract) {
        this.isNeedChannelContract = isNeedChannelContract;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReplaceFormStatus() {
        return replaceFormStatus;
    }

    public void setReplaceFormStatus(String replaceFormStatus) {
        this.replaceFormStatus = replaceFormStatus;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(long loginUserId) {
        this.loginUserId = loginUserId;
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

    @Override
    public String getProjectCode() {
        return projectCode;
    }

    @Override
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
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

    @Override
    public String getFormUserName() {
        return formUserName;
    }

    @Override
    public void setFormUserName(String formUserName) {
        this.formUserName = formUserName;
    }

    @Override
    public String getFormDeptName() {
        return formDeptName;
    }

    @Override
    public void setFormDeptName(String formDeptName) {
        this.formDeptName = formDeptName;
    }

    @Override
    public String getFormStatus() {
        return formStatus;
    }

    @Override
    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    @Override
    public long getFormId() {
        return formId;
    }

    @Override
    public void setFormId(long formId) {
        this.formId = formId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }


    @Override
    public String getFormCode() {
        return formCode;
    }

    @Override
    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getReplaceFileName() {
        return replaceFileName;
    }

    public void setReplaceFileName(String replaceFileName) {
        this.replaceFileName = replaceFileName;
    }

    @Override
    public long getFormUserId() {
        return formUserId;
    }

    @Override
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
