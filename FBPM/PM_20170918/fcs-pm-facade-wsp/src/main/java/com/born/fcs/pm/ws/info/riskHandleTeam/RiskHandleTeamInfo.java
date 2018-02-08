package com.born.fcs.pm.ws.info.riskHandleTeam;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;

/**
 * Created by wqh on 2016/9/8.
 */
public class RiskHandleTeamInfo  extends BaseToStringInfo {
    private long teamId;

    private long customerId;

    private String customerName;

    private String projectCode;

    private String projectName;

    private long busiManagerId;

    private String busiManagerName;

    private long chiefLeaderId;

    private String chiefLeaderName;

    private long viceLeaderId;

    private String viceLeaderName;

    private String memberIds;

    private String memberNames;

    private long createManId;

    private String createManName;

    private Date createTime;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
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

    public long getBusiManagerId() {
        return busiManagerId;
    }

    public void setBusiManagerId(long busiManagerId) {
        this.busiManagerId = busiManagerId;
    }

    public String getBusiManagerName() {
        return busiManagerName;
    }

    public void setBusiManagerName(String busiManagerName) {
        this.busiManagerName = busiManagerName;
    }

    public long getChiefLeaderId() {
        return chiefLeaderId;
    }

    public void setChiefLeaderId(long chiefLeaderId) {
        this.chiefLeaderId = chiefLeaderId;
    }

    public String getChiefLeaderName() {
        return chiefLeaderName;
    }

    public void setChiefLeaderName(String chiefLeaderName) {
        this.chiefLeaderName = chiefLeaderName;
    }

    public long getViceLeaderId() {
        return viceLeaderId;
    }

    public void setViceLeaderId(long viceLeaderId) {
        this.viceLeaderId = viceLeaderId;
    }

    public String getViceLeaderName() {
        return viceLeaderName;
    }

    public void setViceLeaderName(String viceLeaderName) {
        this.viceLeaderName = viceLeaderName;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }

    public String getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(String memberNames) {
        this.memberNames = memberNames;
    }

    public long getCreateManId() {
        return createManId;
    }

    public void setCreateManId(long createManId) {
        this.createManId = createManId;
    }

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        if(rawUpdateTime == null){
            return rawAddTime;
        }
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }
}
