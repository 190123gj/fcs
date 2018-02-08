package com.born.fcs.pm.ws.order.brokerbusiness;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 经纪业务查询Order
 * @author wuzj
 */
public class BrokerBusinessQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = -4333306864668409301L;
	
	/** 主键 */
	private long id;
	/** 项目编号 */
	private String projectCode;
	/** 客户名称 */
	private String customerName;
	/** 摘要 */
	private String summary;
	/** 是否需要上会 */
	private String isNeedCouncil;
	/** 是否收费选择 */
	private String isSelForCharge;
	/** 单据状态 */
	private String formStatus;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getIsNeedCouncil() {
		return isNeedCouncil;
	}
	
	public void setIsNeedCouncil(String isNeedCouncil) {
		this.isNeedCouncil = isNeedCouncil;
	}
	
	public String getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getIsSelForCharge() {
		return this.isSelForCharge;
	}
	
	public void setIsSelForCharge(String isSelForCharge) {
		this.isSelForCharge = isSelForCharge;
	}
	
}
