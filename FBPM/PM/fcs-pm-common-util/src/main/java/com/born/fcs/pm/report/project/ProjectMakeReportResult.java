package com.born.fcs.pm.report.project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.google.common.collect.Lists;

public class ProjectMakeReportResult implements Serializable {
	private static final long serialVersionUID = 4766668256730458616L;
	List<FcsField> listField = Lists.newArrayList();
	String sql;
	List<String> sqlArray;
	HashMap<String, FcsField> fieldMap;
	
	public List<FcsField> getListField() {
		return this.listField;
	}
	
	public void setListField(List<FcsField> listField) {
		this.listField = listField;
	}
	
	public String getSql() {
		return this.sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public List<String> getSqlArray() {
		return this.sqlArray;
	}
	
	public void setSqlArray(List<String> sqlArray) {
		this.sqlArray = sqlArray;
	}
	
	public HashMap<String, FcsField> getFieldMap() {
		return this.fieldMap;
	}
	
	public void setFieldMap(HashMap<String, FcsField> fieldMap) {
		this.fieldMap = fieldMap;
	}
	
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
