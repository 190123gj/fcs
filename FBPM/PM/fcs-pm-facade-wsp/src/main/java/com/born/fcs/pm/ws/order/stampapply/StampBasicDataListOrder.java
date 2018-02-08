package com.born.fcs.pm.ws.order.stampapply;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

import java.util.Date;


public class StampBasicDataListOrder extends ValidateOrderBase {
    private static final long serialVersionUID = 6240797685732774997L;

    private Long id;

    private String fileName;

    private String remark;

    private Date rawAddTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
