package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

import java.util.Date;


public class StampAFileOrder extends ValidateOrderBase {


    private long id;

    private long applyId;

    private long replaceApplyId;

    private String fileName;

    private String fileConent;

    private String legalChapter;

    private String cachet;

    private int legalChapterNum;

    private int cachetNum;

    private String isReplaceOld;

    private int oldFileNum;

    private int sortOrder;

    private String remark;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private String contractCode;

    private StampSourceEnum source;

    public StampSourceEnum getSource() {
        return source;
    }

    public void setSource(StampSourceEnum source) {
        this.source = source;
    }

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

    public String getIsReplaceOld() {
        return isReplaceOld;
    }

    public void setIsReplaceOld(String isReplaceOld) {
        this.isReplaceOld = isReplaceOld;
    }

    public int getOldFileNum() {
        return oldFileNum;
    }

    public void setOldFileNum(int oldFileNum) {
        this.oldFileNum = oldFileNum;
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

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    public void setLegalChapter(String legalChapter) {
        this.legalChapter = legalChapter;
    }

    public void setCachet(String cachet) {
        this.cachet = cachet;
    }

    public String getLegalChapter() {
        return legalChapter;
    }

    public String getCachet() {
        return cachet;
    }

    public long getReplaceApplyId() {
        return replaceApplyId;
    }

    public void setReplaceApplyId(long replaceApplyId) {
        this.replaceApplyId = replaceApplyId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
}
