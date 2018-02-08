package com.born.fcs.pm.ws.order.managerbchange;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * B角变更查询Order
 * @author wuzj
 */
public class ManagerbChangeApplyQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 2863439230602831298L;
	/** 申请ID */
	private long applId;
	/** 变更方式 ChangeManagerbWayEnum */
	private String changeWay;
	/** 变更状态 ChangeManagerbStatusEnum */
	private String status;
	/** 表单状态 */
	private String formStatus;
	
	public long getApplId() {
		return this.applId;
	}
	
	public void setApplId(long applId) {
		this.applId = applId;
	}
	
	public String getChangeWay() {
		return this.changeWay;
	}
	
	public void setChangeWay(String changeWay) {
		this.changeWay = changeWay;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
}
