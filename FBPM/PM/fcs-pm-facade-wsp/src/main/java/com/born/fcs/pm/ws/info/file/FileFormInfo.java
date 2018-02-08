package com.born.fcs.pm.ws.info.file;


import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FileStatusEnum;
import com.born.fcs.pm.ws.enums.FileTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

import java.util.Date;

/**
 *
 * 档案列表信息
 *
 * @author heh
 *
 */
public class FileFormInfo extends FormVOInfo {
    private static final long serialVersionUID = -735923101217835386L;

    private long fileId;

    private long formId;

    private String projectCode;

    private String fileCode;

    private String oldFileCode;

    private String customerName;

    private FileStatusEnum status;

    private FormStatusEnum formStatus;

    private Date rawAddTime;

    private String fileName;

    private String formCode;

    private String applyType;

    private String projectName;

    private long inputListId;

    private String filePath;

    private Date expectReturnTime;

    private long handOverManId;

    private String handOverMan;

    private long receiveManId;

    private String receiveMan;

    private Date receiveTime;

    private String archiveFileName;

    private BooleanEnum canView;

    private FileTypeEnum type;

    private String no;

    private long currBorrowManId;

    private String rnum;
    
	private String isBatch;

	private String fileIds;

	private Date extensionDate;

    private Date rightVoucherExtenDate;

    private String isInExtenDate;

    private Money balance;

    private Money loanned;

    public Money getLoanned() {
        return loanned;
    }

    public void setLoanned(Money loanned) {
        this.loanned = loanned;
    }

    public Money getBalance(){return  balance;}

    public void  setBalance(Money balance){this.balance=balance;}

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


    public Date getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(Date extensionDate) {
        this.extensionDate = extensionDate;
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

    public FileTypeEnum getType() {
        return type;
    }

    public void setType(FileTypeEnum type) {
        this.type = type;
    }

    public BooleanEnum getCanView() {
        return canView;
    }

    public void setCanView(BooleanEnum canView) {
        this.canView = canView;
    }

    public String getArchiveFileName() {
        return archiveFileName;
    }

    public void setArchiveFileName(String archiveFileName) {
        this.archiveFileName = archiveFileName;
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

    public Date getExpectReturnTime() {
        return expectReturnTime;
    }

    public void setExpectReturnTime(Date expectReturnTime) {
        this.expectReturnTime = expectReturnTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getProjectName() {
        return projectName;
    }

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

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
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

    public FileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(FileStatusEnum status) {
        this.status = status;
    }

    public FormStatusEnum getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatusEnum formStatus) {
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
