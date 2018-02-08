package com.born.fcs.pm.ws.order.stampapply;

import java.util.Date;

import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;


public class StampBasicDataApplyFileOrder extends ValidateOrderBase {

    private static final long serialVersionUID = 5262700997911596123L;
    private long id;

    private long applyId;

    private String fileName;

    private String fileConent;

    private int legalChapterNum;

    private int cachetNum;

    private int sortOrder;

    private String remark;

    private Date rawAddTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileConent() {
        return fileConent;
    }

    public void setFileConent(String fileConent) {
        this.fileConent = fileConent;
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

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }
}
