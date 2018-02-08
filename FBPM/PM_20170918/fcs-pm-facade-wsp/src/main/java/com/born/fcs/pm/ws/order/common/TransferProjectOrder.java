package com.born.fcs.pm.ws.order.common;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 项目移交Order
 *
 *
 * @author wuzj
 *
 */
public class TransferProjectOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 7284907195954017674L;
	
	//移交类型、业务经理或者风险经理
	private ProjectRelatedUserTypeEnum userType;
	
	//项目编号
	private String projectCode;
	
	//移交人员
	private long acceptUserId;
	
	//移交人员名称
	private String acceptUserName;
	
	//移交人员账号
	private String acceptUserAccount;
	
	//移交部门(多部门的情况下)
	private long acceptDeptId;
	
	@Override
	public void check() {
		validateGreaterThan(acceptUserId, "接受人员");
		validateHasText(projectCode, "项目编号");
		Assert.isTrue(userType == ProjectRelatedUserTypeEnum.BUSI_MANAGER
						|| userType == ProjectRelatedUserTypeEnum.RISK_MANAGER
						|| userType == ProjectRelatedUserTypeEnum.LEGAL_MANAGER, "请选择移交对象");
	}
	
	public ProjectRelatedUserTypeEnum getUserType() {
		return this.userType;
	}
	
	public void setUserType(ProjectRelatedUserTypeEnum userType) {
		this.userType = userType;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getAcceptUserId() {
		return this.acceptUserId;
	}
	
	public void setAcceptUserId(long acceptUserId) {
		this.acceptUserId = acceptUserId;
	}
	
	public String getAcceptUserName() {
		return this.acceptUserName;
	}
	
	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}
	
	public String getAcceptUserAccount() {
		return this.acceptUserAccount;
	}
	
	public void setAcceptUserAccount(String acceptUserAccount) {
		this.acceptUserAccount = acceptUserAccount;
	}
	
	public long getAcceptDeptId() {
		return this.acceptDeptId;
	}
	
	public void setAcceptDeptId(long acceptDeptId) {
		this.acceptDeptId = acceptDeptId;
	}
	
}
