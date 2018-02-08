package com.born.fcs.pm.ws.order.basicmaintain;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 数据字典明细order
 * 
 * @author wuzj
 */
public class SysDataDictionaryDetailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1590563967513020573L;
	/** 主键 */
	private Long id;
	/** 上级ID */
	private Long parentId;
	/** 数据值 */
	private String dataValue;
	private String dataValue1;
	private String dataValue2;
	private String dataValue3;
	/** 顺序号 */
	private Long sortOrder;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getDataValue() {
		return this.dataValue;
	}
	
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	
	public String getDataValue1() {
		return this.dataValue1;
	}
	
	public void setDataValue1(String dataValue1) {
		this.dataValue1 = dataValue1;
	}
	
	public String getDataValue2() {
		return this.dataValue2;
	}
	
	public void setDataValue2(String dataValue2) {
		this.dataValue2 = dataValue2;
	}
	
	public String getDataValue3() {
		return this.dataValue3;
	}
	
	public void setDataValue3(String dataValue3) {
		this.dataValue3 = dataValue3;
	}
	
	public Long getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
