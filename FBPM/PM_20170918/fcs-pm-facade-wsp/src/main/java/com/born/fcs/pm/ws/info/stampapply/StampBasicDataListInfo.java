package com.born.fcs.pm.ws.info.stampapply;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * 基础资料用印清单
 */
public class StampBasicDataListInfo extends BaseToStringInfo {
    private static final long serialVersionUID = 8475828219867792620L;

    private long id;

    private String fileName;

    private String remark;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }
}
