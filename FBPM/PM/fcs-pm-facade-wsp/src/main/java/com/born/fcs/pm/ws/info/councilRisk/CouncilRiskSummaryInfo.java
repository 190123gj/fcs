package com.born.fcs.pm.ws.info.councilRisk;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * Created by wqh on 2016/9/13.
 */
public class CouncilRiskSummaryInfo extends BaseToStringInfo {
    private long summaryId;

    private long councilId;

    private String summary;

    private String needConfirm;

    private String confirmManIds;

    private String confirmManNames;

    private String isConfirm;

    private long confirmManId;

    private String confirmManName;

    private String confirmResult;

    private String confirmRemark;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(long summaryId) {
        this.summaryId = summaryId;
    }

    public long getCouncilId() {
        return councilId;
    }

    public void setCouncilId(long councilId) {
        this.councilId = councilId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(String needConfirm) {
        this.needConfirm = needConfirm;
    }

    public String getConfirmManIds() {
        return confirmManIds;
    }

    public void setConfirmManIds(String confirmManIds) {
        this.confirmManIds = confirmManIds;
    }

    public String getConfirmManNames() {
        return confirmManNames;
    }

    public void setConfirmManNames(String confirmManNames) {
        this.confirmManNames = confirmManNames;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public long getConfirmManId() {
        return confirmManId;
    }

    public void setConfirmManId(long confirmManId) {
        this.confirmManId = confirmManId;
    }

    public String getConfirmManName() {
        return confirmManName;
    }

    public void setConfirmManName(String confirmManName) {
        this.confirmManName = confirmManName;
    }

    public String getConfirmResult() {
        return confirmResult;
    }

    public void setConfirmResult(String confirmResult) {
        this.confirmResult = confirmResult;
    }

    public String getConfirmRemark() {
        return confirmRemark;
    }

    public void setConfirmRemark(String confirmRemark) {
        this.confirmRemark = confirmRemark;
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
