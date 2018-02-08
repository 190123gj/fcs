package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

import java.util.Date;

/**
 * 常用数据库字段Order
 *
 * @author heh
 *
 */
public class DbFieldOrder extends ProcessOrder {
    private static final long serialVersionUID = 38131118805836689L;

    private Long fieldId;

    private long tableId;

    private String tableName;

    private String fieldName;

    private String fieldForShort;

    private String projectPhase;

    private String remark;

    private String isDelete;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

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

    public String getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(String projectPhase) {
        this.projectPhase = projectPhase;
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
