package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;

/**
 * 修改信息
 * */
public class ChangeDetailInfo implements Serializable {
	private static final long serialVersionUID = -8221450567407212095L;
	/** 主键 */
	private long detailId;
	/** 修改记录Id 与列表关联Id */
	private long changeId;
	/** 字段名 */
	private String lableName;
	/** 字段代码 */
	private String lableKey;
	/** 旧值 */
	private String oldValue;
	/** 新值 */
	private String newValue;
	/** 类型 */
	private String detailType;
	
	public long getDetailId() {
		return this.detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public long getChangeId() {
		return this.changeId;
	}
	
	public void setChangeId(long changeId) {
		this.changeId = changeId;
	}
	
	public String getLableName() {
		return this.lableName;
	}
	
	public void setLableName(String lableName) {
		this.lableName = lableName;
	}
	
	public String getLableKey() {
		return this.lableKey;
	}
	
	public void setLableKey(String lableKey) {
		this.lableKey = lableKey;
	}
	
	public String getOldValue() {
		return this.oldValue;
	}
	
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	public String getNewValue() {
		return this.newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getDetailType() {
		return this.detailType;
	}
	
	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeSaveInfo [detailId=");
		builder.append(detailId);
		builder.append(", changeId=");
		builder.append(changeId);
		builder.append(", lableName=");
		builder.append(lableName);
		builder.append(", lableKey=");
		builder.append(lableKey);
		builder.append(", oldValue=");
		builder.append(oldValue);
		builder.append(", newValue=");
		builder.append(newValue);
		builder.append(", detailType=");
		builder.append(detailType);
		builder.append("]");
		return builder.toString();
	}
	
}
