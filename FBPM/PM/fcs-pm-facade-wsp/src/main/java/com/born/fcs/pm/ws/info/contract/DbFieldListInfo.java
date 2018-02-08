package com.born.fcs.pm.ws.info.contract;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

import java.util.Date;


public class DbFieldListInfo extends BaseToStringInfo {

    private static final long serialVersionUID = -9074284806455383648L;
    private String tableName;

    private String projectPhase;

    private String tableForShort;

    private long fieldId;

    private long tableId;

    private String fieldName;

    private String fieldForShort;

    private String remark;

    private Date rawAddTime;

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

    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldForShort() {
        return fieldForShort;
    }

    public void setFieldForShort(String fieldForShort) {
        this.fieldForShort = fieldForShort;
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
