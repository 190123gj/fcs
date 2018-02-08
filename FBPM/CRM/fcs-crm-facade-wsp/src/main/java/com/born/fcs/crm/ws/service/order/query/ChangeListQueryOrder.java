package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.base.QueryPageBase;

public class ChangeListQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -9004440693951578486L;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeListQueryOrder [customerUserId=");
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
		builder.append("]");
		return builder.toString();
	}
	
}
