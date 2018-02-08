package com.born.fcs.pm.ws.info.file;

import com.born.fcs.pm.ws.enums.FileAttachTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;


public class FileInputListInfo extends BaseToStringInfo {
    private static final long serialVersionUID = 6128622131951752468L;

    private long inputListId;

    private long fileId;

    private long id;

    private String fileType;

    private String fileName;

    private String archiveFileName;

    private int filePage;

    private String filePath;

    private String inputRemark;

    private int sortOrder;

    private Date rawAddTime;

    private String no;

    private FileAttachTypeEnum attachType;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public FileAttachTypeEnum getAttachType() {
        return attachType;
    }

    public void setAttachType(FileAttachTypeEnum attachType) {
        this.attachType = attachType;
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

    public int getFilePage() {
        return filePage;
    }

    public void setFilePage(int filePage) {
        this.filePage = filePage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getInputRemark() {
        return inputRemark;
    }

    public void setInputRemark(String inputRemark) {
        this.inputRemark = inputRemark;
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
