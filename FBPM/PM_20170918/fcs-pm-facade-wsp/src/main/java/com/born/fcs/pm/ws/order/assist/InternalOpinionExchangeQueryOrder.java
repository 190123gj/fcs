package com.born.fcs.pm.ws.order.assist;

import com.born.fcs.pm.ws.base.QueryFormBase;

/**
 * 内审意见交换列表查询Order
 *
 *
 * @author wuzj
 *
 */
public class InternalOpinionExchangeQueryOrder extends QueryFormBase {
	
	private static final long serialVersionUID = 4197367796487129470L;
	
	private long id;
	
	/** 类型 */
	private String exType;
	
	/** 审计部门ID */
	private long deptId;
	
	/** 审计部门名称 */
	private String deptNames;
	
	/** 审计人员 */
	private String users;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getExType() {
		return this.exType;
	}
	
	public void setExType(String exType) {
		this.exType = exType;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptNames() {
		return this.deptNames;
	}
	
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
	public String getUsers() {
		return this.users;
	}
	
	public void setUsers(String users) {
		this.users = users;
	}
	
}
