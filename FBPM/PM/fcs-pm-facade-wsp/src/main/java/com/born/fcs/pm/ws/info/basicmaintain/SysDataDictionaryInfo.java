package com.born.fcs.pm.ws.info.basicmaintain;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 数据字典
 *
 * @author wuzj
 */
public class SysDataDictionaryInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -3969519350131442084L;
	
	/** 主键 */
	private long id;
	/** 上级ID */
	private long parentId;
	/** 数据分类 */
	private DataCodeEnum dataCode;
	/** 数据值 */
	private String dataValue;
	private String dataValue1;
	private String dataValue2;
	private String dataValue3;
	/** 下级数量 */
	private long childrenNum;
	/** 下级 */
	List<SysDataDictionaryInfo> children;
	/** 顺序号 */
	private long sortOrder;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public DataCodeEnum getDataCode() {
		return this.dataCode;
	}
	
	public void setDataCode(DataCodeEnum dataCode) {
		this.dataCode = dataCode;
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
	
	public long getChildrenNum() {
		return this.childrenNum;
	}
	
	public void setChildrenNum(long childrenNum) {
		this.childrenNum = childrenNum;
	}
	
	public List<SysDataDictionaryInfo> getChildren() {
		return this.children;
	}
	
	public void setChildren(List<SysDataDictionaryInfo> children) {
		this.children = children;
	}
	
	public long getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(long sortOrder) {
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
