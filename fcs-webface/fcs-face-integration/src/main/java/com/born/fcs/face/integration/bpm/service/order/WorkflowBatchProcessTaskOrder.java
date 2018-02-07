package com.born.fcs.face.integration.bpm.service.order;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 流程批量审核Order
 * @author wuzj
 */
public class WorkflowBatchProcessTaskOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 758438870239184838L;
	
	/** 流程实例ID */
	private String[] actInstIds;
	/** 流程处理地址 */
	private String[] taskUrls;
	/** 任务ID */
	private String[] taskIds;
	/** 处理类型 pass/back2start */
	private String processType = "pass";
	/** 处理意见 */
	private String processContent;
	
	//手机
	private String userMobile;
	//email
	private String userEmail;
	//部门ID
	private Long deptId;
	//部门编号
	private String deptCode;
	//部门名称
	private String deptName;
	//部门路径 deptId.deptId. 
	private String deptPath;
	//部门路径名称 deptPathName/deptPathName/
	private String deptPathName;
	
	@Override
	public void check() {
		validateNotNull(actInstIds, "流程实例ID");
		validateNotNull(taskUrls, "任务地址");
		validateNotNull(taskIds, "任务地址");
		validateNotNull(processType, "处理类型");
		Assert.isTrue(
			"pass".equalsIgnoreCase(processType) || "back2start".equalsIgnoreCase(processType),
			"处理类型错误");
	}
	
	public String[] getActInstIds() {
		return this.actInstIds;
	}
	
	public void setActInstIds(String[] actInstIds) {
		this.actInstIds = actInstIds;
	}
	
	public String[] getTaskUrls() {
		return this.taskUrls;
	}
	
	public void setTaskUrls(String[] taskUrls) {
		this.taskUrls = taskUrls;
	}
	
	public String[] getTaskIds() {
		return this.taskIds;
	}
	
	public void setTaskIds(String[] taskIds) {
		this.taskIds = taskIds;
	}
	
	public String getProcessType() {
		return this.processType;
	}
	
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	public String getProcessContent() {
		return this.processContent;
	}
	
	public void setProcessContent(String processContent) {
		this.processContent = processContent;
	}
	
	public String getUserMobile() {
		return this.userMobile;
	}
	
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public Long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
}
