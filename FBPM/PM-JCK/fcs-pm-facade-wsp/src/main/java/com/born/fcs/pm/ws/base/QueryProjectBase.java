package com.born.fcs.pm.ws.base;

import java.util.List;

import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;

/**
 * 
 * 项目查询
 * 
 * @author wuzj 2016年4月29日
 * 
 */
public class QueryProjectBase extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 2868624194392098629L;
	
	private String projectCode; //项目编号
	private String projectName; //项目名称
	private ProjectStatusEnum projectStatus; //项目状态
	private long customerId; //客户ID
	private String customerName; //客户名称
	private long busiManagerId; //客户经理ID
	private String busiManagerAccount; //客户经理账号
	private String busiManagerName; //客户经理名称
	private String busiType;//业务品种
	private String projectCodeOrName;
	
	/** 相关人员类型 必须配合 loginUserId 使用 */
	private List<ProjectRelatedUserTypeEnum> relatedRoleList;
	
	public String getProjectCode() {
		return (null == projectCode) ? projectCode : projectCode.trim();
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return (null == projectName) ? projectName : projectName.trim();
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public ProjectStatusEnum getProjectStatus() {
		return projectStatus;
	}
	
	public void setProjectStatus(ProjectStatusEnum projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return (null == customerName) ? customerName : customerName.trim();
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public long getBusiManagerId() {
		return busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return (null == busiManagerAccount) ? busiManagerAccount : busiManagerAccount.trim();
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return (null == busiManagerName) ? busiManagerName : busiManagerName.trim();
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getBusiType() {
		return (null == busiType) ? busiType : busiType.trim();
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getProjectCodeOrName() {
		return this.projectCodeOrName;
	}
	
	public void setProjectCodeOrName(String projectCodeOrName) {
		this.projectCodeOrName = projectCodeOrName;
	}
	
	public List<ProjectRelatedUserTypeEnum> getRelatedRoleList() {
		return this.relatedRoleList;
	}
	
	public void setRelatedRoleList(List<ProjectRelatedUserTypeEnum> relatedRoleList) {
		this.relatedRoleList = relatedRoleList;
	}
	
}
