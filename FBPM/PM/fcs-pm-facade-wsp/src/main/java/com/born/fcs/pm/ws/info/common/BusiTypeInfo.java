package com.born.fcs.pm.ws.info.common;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;

public class BusiTypeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 4302592904137308848L;
	
	private int id;
	
	private String code;
	
	private String name;
	
	private int parentId;
	
	private CustomerTypeEnum customerType;
	
	private BooleanEnum hasChildren;
	
	private String setupFormCode;
	
	private BooleanEnum isDel;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getParentId() {
		return this.parentId;
	}
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public CustomerTypeEnum getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}
	
	public BooleanEnum getHasChildren() {
		return this.hasChildren;
	}
	
	public void setHasChildren(BooleanEnum hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	public String getSetupFormCode() {
		return this.setupFormCode;
	}
	
	public void setSetupFormCode(String setupFormCode) {
		this.setupFormCode = setupFormCode;
	}
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
