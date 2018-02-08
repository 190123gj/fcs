package com.born.fcs.pm.ws.info.projectRiskReport;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * Created by wqh on 2016/9/20.
 */
public class ProjectRiskReportCompDetailInfo extends BaseToStringInfo {
    private long detailId;

    private long reportId;

    private String projectCode;

    private String compAmount;

    private Date compDate;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCompAmount() {
        return compAmount;
    }

    public void setCompAmount(String compAmount) {
        this.compAmount = compAmount;
    }

    public Date getCompDate() {
        return compDate;
    }

    public void setCompDate(Date compDate) {
        this.compDate = compDate;
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
