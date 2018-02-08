package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

/**
 * 常用数据库表查询Order
 *
 * @author heh
 *
 */
public class DbTableQueryOrder extends FcsQueryPageBase {
    private static final long serialVersionUID = -2303591640173420646L;

    private String tableForShort;

    private String tableName;

    private String projectPhase;

    public String getTableForShort() {
        return tableForShort;
    }

    public void setTableForShort(String tableForShort) {
        this.tableForShort = tableForShort;
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
}
