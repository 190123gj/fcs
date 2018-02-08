package com.born.fcs.pm.ws.info.counterguarantee;

import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

/**
 * 
 * 解保申请
 * 
 * @author lirz
 * 
 * 2016-5-17 下午5:41:26
 */
public class ReleaseApplyInfo extends FormVOInfo {
	
	private static final long serialVersionUID = -5718256873896187472L;
	
	private long releaseId; //主键
	private String projectCode;
	private String projectName;
	private long customerId;
	private String customerName;
	private ProjectStatusEnum projectStatus;
	private ProjectPhasesEnum phases;
	private long busiManagerId;
	private String busiManagerAccount;
	private String busiManagerName;
	private String busiType;
	private String busiTypeName;
	
	public long getReleaseId() {
		return releaseId;
	}
	
	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
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

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeName() {
		return busiTypeName;
	}

	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
}
