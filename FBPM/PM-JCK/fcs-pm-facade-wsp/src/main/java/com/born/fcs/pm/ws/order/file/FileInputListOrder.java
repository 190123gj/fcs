package com.born.fcs.pm.ws.order.file;


import com.born.fcs.pm.ws.enums.FileAttachTypeEnum;
import com.born.fcs.pm.ws.enums.FileStatusEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

import java.util.Date;

public class FileInputListOrder extends ProcessOrder {
    private static final long serialVersionUID = 3615663620607901695L;

    private long inputListId;

    private long fileId;

    private long id;

    private String fileType;

    private String fileName;

    private String archiveFileName;

    private Integer filePage;

    private String filePath;

    private String InputRemark;

    private int sortOrder;

    private Date rawAddTime;

    private FileStatusEnum status;

    private String no;

    private String attachType;

    private long currBorrowManId;

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

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public FileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(FileStatusEnum status) {
        this.status = status;
    }

    public long getInputListId() {
        return inputListId;
    }

    public void setInputListId(long inputListId) {
        this.inputListId = inputListId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getArchiveFileName() {
        return archiveFileName;
    }

    public void setArchiveFileName(String archiveFileName) {
        this.archiveFileName = archiveFileName;
    }

    public Integer getFilePage() {
        return filePage;
    }

    public void setFilePage(Integer filePage) {
        this.filePage = filePage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getInputRemark() {
        return InputRemark;
    }

    public void setInputRemark(String inputRemark) {
        InputRemark = inputRemark;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}
