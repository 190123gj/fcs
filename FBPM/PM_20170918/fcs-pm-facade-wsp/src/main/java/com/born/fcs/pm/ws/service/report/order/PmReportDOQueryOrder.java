package com.born.fcs.pm.ws.service.report.order;

import java.util.HashMap;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.service.report.field.FcsField;

public class PmReportDOQueryOrder  extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5860193199945093046L;
	private String sql;
	private long limitStart;
	private long pageSize;
	private HashMap<String, FcsField> fieldMap;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public long getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public HashMap<String, FcsField> getFieldMap() {
		return fieldMap;
	}
	public void setFieldMap(HashMap<String, FcsField> fieldMap) {
		this.fieldMap = fieldMap;
	}
	
	
}
