package com.born.fcs.pm.ws.info.stampapply;

import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * 用印申请文件
 *
 *
 * @author hehao
 *
 */
public class StampAFileInfo extends BaseToStringInfo {

    private static final long serialVersionUID = -2466649884663413332L;

    private long id;

    private long applyId;

    private long replaceApplyId;

    private String fileName;

    private String fileConent;

    private int legalChapterNum;

    private int cachetNum;

    private String isReplaceOld;

    private int oldFileNum;

    private int sortOrder;

    private String remark;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private int oldLegalChapterNum;

    private int oldCachetNum;

    private long maxItem;

    private String oldFileContent;

    private String contractCode;

    private String cnt;

    private StampSourceEnum source;

    private String invalid;

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public StampSourceEnum getSource() {
        return source;
    }

    public void setSource(StampSourceEnum source) {
        this.source = source;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public long getReplaceApplyId() {
        return replaceApplyId;
    }

    public void setReplaceApplyId(long replaceApplyId) {
        this.replaceApplyId = replaceApplyId;
    }

    public long getMaxItem() {
        return maxItem;
    }

    public void setMaxItem(long maxItem) {
        this.maxItem = maxItem;
    }

    public int getOldLegalChapterNum() {
        return oldLegalChapterNum;
    }

    public void setOldLegalChapterNum(int oldLegalChapterNum) {
        this.oldLegalChapterNum = oldLegalChapterNum;
    }

    public int getOldCachetNum() {
        return oldCachetNum;
    }

    public void setOldCachetNum(int oldCachetNum) {
        this.oldCachetNum = oldCachetNum;
    }

    public String getOldFileContent() {
        return oldFileContent;
    }

    public void setOldFileContent(String oldFileContent) {
        this.oldFileContent = oldFileContent;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
