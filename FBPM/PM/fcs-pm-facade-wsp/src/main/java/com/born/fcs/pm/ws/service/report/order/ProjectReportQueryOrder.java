package com.born.fcs.pm.ws.service.report.order;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;

/**
 * 项目报表查询
 * 
 * @author wuzj
 */
public class ProjectReportQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -2104069539475607864L;
	
	/** 当前登陆用户 */
	private long loginUserId;
	/** 用户所属部门列表 */
	private List<Long> deptIdList;
	/** 用户身份 */
	private List<ProjectRelatedUserTypeEnum> relatedRoleList;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 项目编号或者项目名称 */
	private String projectCodeOrName;
	/** 客户经理名称 */
	private String busiManagerName;
	/** 业务品种 */
	private String busiType;
	/** 客户名称 */
	private String customerName;
	/** 是否在保 YES/NO */
	private BooleanEnum inGuarantee;
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<ProjectRelatedUserTypeEnum> getRelatedRoleList() {
		return this.relatedRoleList;
	}
	
	public void setRelatedRoleList(List<ProjectRelatedUserTypeEnum> relatedRoleList) {
		this.relatedRoleList = relatedRoleList;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectCodeOrName() {
		return this.projectCodeOrName;
	}
	
	public void setProjectCodeOrName(String projectCodeOrName) {
		this.projectCodeOrName = projectCodeOrName;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public BooleanEnum getInGuarantee() {
		return this.inGuarantee;
	}
	
	public void setInGuarantee(BooleanEnum inGuarantee) {
		this.inGuarantee = inGuarantee;
	}
	
}
