package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class DbFieldQueryOrder extends FcsQueryPageBase {
	
	private static final long serialVersionUID = 1L;
	
	private String tableName;
	
	private String fieldName;
	
	private String projectPhase;
	
	private Long tableId;
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public Long getTableId() {
		return this.tableId;
	}
	
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getProjectPhase() {
		return projectPhase;
	}
	
	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}
}
