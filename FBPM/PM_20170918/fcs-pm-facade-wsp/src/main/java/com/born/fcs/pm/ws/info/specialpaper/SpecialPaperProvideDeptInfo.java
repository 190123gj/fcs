package com.born.fcs.pm.ws.info.specialpaper;


import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

public class SpecialPaperProvideDeptInfo extends BaseToStringInfo {
    private static final long serialVersionUID = -223182854966553234L;

    private long id;

    private long deptId;

    private long deptName;

    private long receiveManId;

    private String receiveManName;

    private long provideManId;

    private String provideManName;

    private long pieces;

    private String remark;

    private Date rawAddTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getDeptName() {
        return deptName;
    }

    public void setDeptName(long deptName) {
        this.deptName = deptName;
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

    public long getProvideManId() {
        return provideManId;
    }

    public void setProvideManId(long provideManId) {
        this.provideManId = provideManId;
    }

    public String getProvideManName() {
        return provideManName;
    }

    public void setProvideManName(String provideManName) {
        this.provideManName = provideManName;
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
