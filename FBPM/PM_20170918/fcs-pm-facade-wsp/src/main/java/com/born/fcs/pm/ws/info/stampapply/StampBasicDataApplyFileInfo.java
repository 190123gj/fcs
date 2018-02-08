package com.born.fcs.pm.ws.info.stampapply;

import java.util.Date;

import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 基础资料用印
 *
 *
 * @author hehao
 *
 */
public class StampBasicDataApplyFileInfo extends BaseToStringInfo {

    private static final long serialVersionUID = -3639931615495581562L;
    private long id;

    private long applyId;

    private String fileName;

    private String fileConent;

    private int legalChapterNum;

    private int cachetNum;

    private int sortOrder;

    private String remark;

    private Date rawAddTime;

    private Date rawUpdateTime;

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

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }
}
