package com.born.fcs.pm.ws.info.council;

import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

/**
 * 项目上会列表
 * 
 * @author lirz
 * 
 * 2016-5-10 下午3:46:24
 */
public class CouncilApplyRiskHandleInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 4597059689642136896L;
	
	private long handleId; //主键
	private String isRepay; //是否代偿 申报类别
	private String projectCode;
	private String projectName;
	private long customerId;
	private String customerName;
	private ProjectStatusEnum projectStatus;
	private ProjectPhasesEnum phases;
	private long busiManagerId;
	private String busiManagerAccount;
	private String busiManagerName;
	
	public long getHandleId() {
		return handleId;
	}
	
	public void setHandleId(long handleId) {
		this.handleId = handleId;
	}
	
	public String getIsRepay() {
		return isRepay;
	}
	
	public void setIsRepay(String isRepay) {
		this.isRepay = isRepay;
	}
	
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
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public ProjectStatusEnum getProjectStatus() {
		return projectStatus;
	}
	
	public void setProjectStatus(ProjectStatusEnum projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public ProjectPhasesEnum getPhases() {
		return phases;
	}
	
	public void setPhases(ProjectPhasesEnum phases) {
		this.phases = phases;
	}

	public long getBusiManagerId() {
		return busiManagerId;
	}

	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}

	public String getBusiManagerAccount() {
		return busiManagerAccount;
	}

	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}

	public String getBusiManagerName() {
		return busiManagerName;
	}

	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
}
