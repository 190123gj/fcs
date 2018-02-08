package com.born.fcs.pm.ws.info.contract;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;


public class DbTableInfo extends BaseToStringInfo {

    private static final long serialVersionUID = -8024325975123944843L;
    private long tableId;

    private String tableName;

    private String projectPhase;

    private String tableForShort;

    private String className;

    private String remark;

    private String isDelete;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(String projectPhase) {
        this.projectPhase = projectPhase;
    }

    public String getTableForShort() {
        return tableForShort;
    }

    public void setTableForShort(String tableForShort) {
        this.tableForShort = tableForShort;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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
