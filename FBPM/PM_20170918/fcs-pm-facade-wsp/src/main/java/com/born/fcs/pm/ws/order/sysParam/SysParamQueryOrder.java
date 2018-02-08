package com.born.fcs.pm.ws.order.sysParam;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * Created by wqh on 2014/5/21.
 */
public class SysParamQueryOrder extends QueryPageBase {
	private static final long serialVersionUID = -4239124486323136636L;
	/** 参数名称 */
	private String paramName;
	/** 参数描述 */
	private String description;
	
	private String extendAttributeOne;
	
	private String extendAttributeTwo;
	
	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public String getExtendAttributeOne() {
		return this.extendAttributeOne;
	}
	
	public void setExtendAttributeOne(String extendAttributeOne) {
		this.extendAttributeOne = extendAttributeOne;
	}
	
	public String getExtendAttributeTwo() {
		return this.extendAttributeTwo;
	}
	
	public void setExtendAttributeTwo(String extendAttributeTwo) {
		this.extendAttributeTwo = extendAttributeTwo;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
