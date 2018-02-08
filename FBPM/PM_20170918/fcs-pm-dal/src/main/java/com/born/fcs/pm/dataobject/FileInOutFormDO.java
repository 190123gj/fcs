package com.born.fcs.pm.dataobject;


import java.util.Date;

public class FileInOutFormDO extends SimpleFormDO{
    private static final long serialVersionUID = 3143314767139126353L;

    private String applyType;

    private Date rawAddTime;

    private Date statusUpdateTime;

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }
}
