package com.born.fcs.fm.ws.order.report;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 项目保证金查询
 * @author wuzj
 */
public class ProjectDepositQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -7533842056637042746L;
	
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户名称 */
	private String customerName;
	/** 业务类型 */
	private String busiType;
	/** 客户经理 */
	private Long busiManagerId;
	private String busiManagerName;
	private String busiManagerAccount;
	
	private Long deptId;
	/** 部门 */
	private String deptName;
	/** 收取、存出时间 */
	private Date startTime;
	private Date endTime;
	
	/**
	 * 是否查询存出保证金、默认false 查询存入保证金
	 */
	private boolean isOut;
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public Long getBusiManagerId() {
		return busiManagerId;
	}
	
	public void setBusiManagerId(Long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getBusiManagerAccount() {
		return busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public Long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public boolean isOut() {
		return isOut;
	}
	
	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}
	
}
