package com.born.fcs.pm.ws.info.specialpaper;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;


public class SpecialPaperInvalidInfo extends BaseToStringInfo {

    private static final long serialVersionUID = -7635942744553894700L;
    private long id;

    private long receiveManId;

    private String receiveManName;

    private long keepingManId;

    private String keepingManName;

    private long pieces;

    private String remark;

    private Date rawAddTime;

    private Date receiveDate;

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReceiveManId() {
        return receiveManId;
    }

    public void setReceiveManId(long receiveManId) {
        this.receiveManId = receiveManId;
    }

    public String getReceiveManName() {
        return receiveManName;
    }

    public void setReceiveManName(String receiveManName) {
        this.receiveManName = receiveManName;
    }

    public long getKeepingManId() {
        return keepingManId;
    }

    public void setKeepingManId(long keepingManId) {
        this.keepingManId = keepingManId;
    }

    public String getKeepingManName() {
        return keepingManName;
    }

    public void setKeepingManName(String keepingManName) {
        this.keepingManName = keepingManName;
    }

    public long getPieces() {
        return pieces;
    }

    public void setPieces(long pieces) {
        this.pieces = pieces;
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
