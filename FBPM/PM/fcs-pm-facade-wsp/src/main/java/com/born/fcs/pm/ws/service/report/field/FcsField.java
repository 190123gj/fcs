package com.born.fcs.pm.ws.service.report.field;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class FcsField implements Serializable {
	private static final long serialVersionUID = 2884796603051609022L;
	/**
	 * 数据库字段名
	 */
	String colName;
	/**
	 * 字段中文名
	 */
	String name;
	/**
	 * 字段别名
	 */
	String aliasName;
	/**
	 * 字段使用函数
	 */
	FcsFunctionEnum function;
	
	DataTypeEnum dataTypeEnum = DataTypeEnum.STRING;
	
	public FcsField() {
		super();
	}
	
	public FcsField(String colName, String name, FcsFunctionEnum function) {
		super();
		this.colName = colName;
		this.name = name;
		this.function = function;
		if (function == FcsFunctionEnum.COUNT || function == FcsFunctionEnum.COUNT_DISTINCT) {
			dataTypeEnum = DataTypeEnum.BIGDECIMAL;
		}
	}
	
	public FcsField(String colName, String name, FcsFunctionEnum function, DataTypeEnum dataTypeEnum) {
		super();
		this.colName = colName;
		this.name = name;
		this.function = function;
		this.dataTypeEnum = dataTypeEnum;
	}
	
	public String getColName() {
		return this.colName;
	}
	
	public void setColName(String colName) {
		this.colName = colName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public FcsFunctionEnum getFunction() {
		return this.function;
	}
	
	public void setFunction(FcsFunctionEnum function) {
		this.function = function;
	}
	
	public String getAliasName() {
		return this.aliasName;
	}
	
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	
	public DataTypeEnum getDataTypeEnum() {
		return this.dataTypeEnum;
	}
	
	public void setDataTypeEnum(DataTypeEnum dataTypeEnum) {
		this.dataTypeEnum = dataTypeEnum;
	}
	
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
