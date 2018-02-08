package com.born.fcs.fm.dal.queryCondition;

import java.util.List;

/**
 * 通用查询条件
 * @author wuzj
 */
public class QueryPermissionConditionBaseDO extends QueryConditionBaseDO {
	
	private static final long serialVersionUID = -259193142155234992L;
	
	//当前登陆用户
	private long loginUserId;
	
	//用户所属部门列表
	private List<Long> deptIdList;
	
	//表单提交人
	private long formSubmitManId;
	
	private long draftUserId;
	
	private long auditor;
	
	public long getLoginUserId() {
		return loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public long getFormSubmitManId() {
		return formSubmitManId;
	}
	
	public void setFormSubmitManId(long formSubmitManId) {
		this.formSubmitManId = formSubmitManId;
	}
	
	public long getDraftUserId() {
		return draftUserId;
	}
	
	public void setDraftUserId(long draftUserId) {
		this.draftUserId = draftUserId;
	}
	
	public long getAuditor() {
		return auditor;
	}
	
	public void setAuditor(long auditor) {
		this.auditor = auditor;
	}
	
}
