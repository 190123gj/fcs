package com.born.fcs.pm.ws.info.file;

import com.born.fcs.pm.ws.info.common.FormVOInfo;

import java.util.Date;

/**
 *
 * 档案出入库历史
 *
 * @author heh
 *
 */
public class FileInOutInfo extends FormVOInfo {
    private static final long serialVersionUID = -5767833999695467644L;

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
