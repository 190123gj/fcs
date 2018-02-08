package com.born.fcs.pm.ws.info.councilRisk;

import com.born.fcs.pm.ws.enums.CouncilRiskTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by wqh on 2016/9/9.
 */
public class CouncilRiskInfo extends BaseToStringInfo {
    private long councilId;

    private String councilCode;

    private CouncilRiskTypeEnum councilType;

    private String councilPlace;

    private String councilSubject;

    private Date beginTime;

    private long customerId;

    private String customerName;

    private String projectCode;

    private String projectName;

    private long applyManId;

    private String applyManName;

    private CouncilStatusEnum councilStatus;

    private String participantIds;

    private String participantNames;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private String isSummary;
    private String isConfirm;

    private String isNeedConfirm;

    private String hasConfirm;

    private List<CouncilRiskSummaryInfo> summaryInfoList;

    public long getCouncilId() {
        return councilId;
    }

    public void setCouncilId(long councilId) {
        this.councilId = councilId;
    }

    public String getCouncilCode() {
        return councilCode;
    }

    public void setCouncilCode(String councilCode) {
        this.councilCode = councilCode;
    }

    public CouncilRiskTypeEnum getCouncilType() {
        return councilType;
    }

    public void setCouncilType(CouncilRiskTypeEnum councilType) {
        this.councilType = councilType;
    }

    public String getCouncilPlace() {
        return councilPlace;
    }

    public void setCouncilPlace(String councilPlace) {
        this.councilPlace = councilPlace;
    }

    public String getCouncilSubject() {
        return councilSubject;
    }

    public void setCouncilSubject(String councilSubject) {
        this.councilSubject = councilSubject;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getApplyManId() {
        return applyManId;
    }

    public void setApplyManId(long applyManId) {
        this.applyManId = applyManId;
    }

    public String getApplyManName() {
        return applyManName;
    }

    public void setApplyManName(String applyManName) {
        this.applyManName = applyManName;
    }

    public CouncilStatusEnum getCouncilStatus() {
        return councilStatus;
    }

    public void setCouncilStatus(CouncilStatusEnum councilStatus) {
        this.councilStatus = councilStatus;
    }

    public String getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(String participantIds) {
        this.participantIds = participantIds;
    }

    public String getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(String participantNames) {
        this.participantNames = participantNames;
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

    public List<CouncilRiskSummaryInfo> getSummaryInfoList() {
        return summaryInfoList;
    }

    public void setSummaryInfoList(List<CouncilRiskSummaryInfo> summaryInfoList) {
        this.summaryInfoList = summaryInfoList;
    }

    public String getIsSummary() {
        return isSummary;
    }

    public void setIsSummary(String isSummary) {
        this.isSummary = isSummary;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getIsNeedConfirm() {
        return isNeedConfirm;
    }

    public void setIsNeedConfirm(String isNeedConfirm) {
        this.isNeedConfirm = isNeedConfirm;
    }

    public String getHasConfirm() {
        return hasConfirm;
    }

    public void setHasConfirm(String hasConfirm) {
        this.hasConfirm = hasConfirm;
    }
}
