package com.born.fcs.pm.dataobject;


import java.util.Date;
import java.util.List;

public class FileFormDO extends FormProjectDO {
    private static final long serialVersionUID = -8461540060523258969L;

    private long fileId;

    private long formId;

    private String formCode;

    private String oldFileCode;

    private String projectCode;

    private String projectName;

    private long inputListId;

    private String fileCode;

    private String fileName;

    private String customerName;

    private String status;

    private String formStatus;

    private String applyType;

    private Date rawAddTime;

    private String filePath;

    private Date expectReturnTime;

    private long handOverManId;

    private String handOverMan;

    private long receiveManId;

    private String receiveMan;

    private Date receiveTime;

    private List<Long> deptIdList;

    long loginUserId;

    private String archiveFileName;

    private String fileType;

    private Date startTime;

    private Date endTime;
    
    private Date fileStartTime;
    
    private Date fileEndTime;

    private long applyManId;

    private long detailId;

    private String type;

    private long draftUserId;

    private String no;

    private long currBorrowManId;

    private String isFileAdmin;

    private String xinhuiDeptCode;

    private String isXinhui;

    private String rnum;
    
	private String isBatch;

	private String fileIds;

	private Date rightVoucherExtenDate;

	private String isInExtenDate;

    public String getIsInExtenDate() {
        return isInExtenDate;
    }

    public void setIsInExtenDate(String isInExtenDate) {
        this.isInExtenDate = isInExtenDate;
    }

    public Date getRightVoucherExtenDate() {
        return rightVoucherExtenDate;
    }

    public void setRightVoucherExtenDate(Date rightVoucherExtenDate) {
        this.rightVoucherExtenDate = rightVoucherExtenDate;
    }

    public String getOldFileCode() {
        return oldFileCode;
    }

    public void setOldFileCode(String oldFileCode) {
        this.oldFileCode = oldFileCode;
    }

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getIsXinhui() {
        return isXinhui;
    }

    public void setIsXinhui(String isXinhui) {
        this.isXinhui = isXinhui;
    }

    public String getXinhuiDeptCode() {
        return xinhuiDeptCode;
    }

    public void setXinhuiDeptCode(String xinhuiDeptCode) {
        this.xinhuiDeptCode = xinhuiDeptCode;
    }

    public String getIsFileAdmin() {
        return isFileAdmin;
    }

    public void setIsFileAdmin(String isFileAdmin) {
        this.isFileAdmin = isFileAdmin;
    }

    public long getCurrBorrowManId() {
        return currBorrowManId;
    }

    public void setCurrBorrowManId(long currBorrowManId) {
        this.currBorrowManId = currBorrowManId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public long getDraftUserId() {
        return draftUserId;
    }

    public void setDraftUserId(long draftUserId) {
        this.draftUserId = draftUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
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

    public Date getFileStartTime() {
		return fileStartTime;
	}

	public void setFileStartTime(Date fileStartTime) {
		this.fileStartTime = fileStartTime;
	}

	public Date getFileEndTime() {
		return fileEndTime;
	}

	public void setFileEndTime(Date fileEndTime) {
		this.fileEndTime = fileEndTime;
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

    public Date getExpectReturnTime() {
        return expectReturnTime;
    }

    public void setExpectReturnTime(Date expectReturnTime) {
        this.expectReturnTime = expectReturnTime;
    }

    public long getHandOverManId() {
        return handOverManId;
    }

    public void setHandOverManId(long handOverManId) {
        this.handOverManId = handOverManId;
    }

    public String getHandOverMan() {
        return handOverMan;
    }

    public void setHandOverMan(String handOverMan) {
        this.handOverMan = handOverMan;
    }

    public long getReceiveManId() {
        return receiveManId;
    }

    public void setReceiveManId(long receiveManId) {
        this.receiveManId = receiveManId;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getInputListId() {
        return inputListId;
    }

    public void setInputListId(long inputListId) {
        this.inputListId = inputListId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    @Override
    public long getFormId() {
        return formId;
    }

    @Override
    public void setFormId(long formId) {
        this.formId = formId;
    }

    @Override
    public String getProjectCode() {
        return projectCode;
    }

    @Override
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getFormStatus() {
        return formStatus;
    }

    @Override
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

    @Override
    public String getFormCode() {
        return formCode;
    }

    @Override
    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

	public String getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(String isBatch) {
		this.isBatch = isBatch;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
}
