package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 修改记录查询
 * */
public class ChangeListInfo implements Serializable {
	
	private static final long serialVersionUID = 1305555205301721273L;
	/** 主键 */
	private long changeId;
	/** 客户ID */
	private long customerUserId;
	/** 修改人姓名 */
	private String operName;
	/** 修改人Id */
	private long operId;
	/** 表单Id */
	private long formId;
	/** 修改来源 */
	private String changeType;
	/** 数据类型 */
	private String dataType;
	/** 创建时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	/** 修改详情 */
	private List<ChangeDetailInfo> chageDetailList;
	
	public long getChangeId() {
		return this.changeId;
	}
	
	public void setChangeId(long changeId) {
		this.changeId = changeId;
	}
	
	public long getCustomerUserId() {
		return this.customerUserId;
	}
	
	public void setCustomerUserId(long customerUserId) {
		this.customerUserId = customerUserId;
	}
	
	public String getOperName() {
		return this.operName;
	}
	
	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	public long getOperId() {
		return this.operId;
	}
	
	public void setOperId(long operId) {
		this.operId = operId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getChangeType() {
		return this.changeType;
	}
	
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	
	public List<ChangeDetailInfo> getChageDetailList() {
		return this.chageDetailList;
	}
	
	public void setChageDetailList(List<ChangeDetailInfo> chageDetailList) {
		this.chageDetailList = chageDetailList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeListInfo [changeId=");
		builder.append(changeId);
		builder.append(", customerUserId=");
		builder.append(customerUserId);
		builder.append(", operName=");
		builder.append(operName);
		builder.append(", operId=");
		builder.append(operId);
		builder.append(", formId=");
		builder.append(formId);
		builder.append(", changeType=");
		builder.append(changeType);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", chageDetailList=");
		builder.append(chageDetailList);
		builder.append("]");
		return builder.toString();
	}
	
}
