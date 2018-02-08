package com.born.fcs.pm.ws.order.formchange;

import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 检查是否可签报Order
 * 
 * @author wuzj
 */
public class FormCheckCanChangeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 4899531675433533684L;
	/** 项目编号 */
	private String projectCode;
	/** 客户ID */
	private long customerId;
	/** 签报表单 */
	private FormChangeApplyEnum changeForm;
	/** 签报表单ID */
	private long changeFormId;
	/** 相关项目 */
	private ProjectInfo project;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public FormChangeApplyEnum getChangeForm() {
		return this.changeForm;
	}
	
	public void setChangeForm(FormChangeApplyEnum changeForm) {
		this.changeForm = changeForm;
	}
	
	public long getChangeFormId() {
		return this.changeFormId;
	}
	
	public void setChangeFormId(long changeFormId) {
		this.changeFormId = changeFormId;
	}
	
	public ProjectInfo getProject() {
		return this.project;
	}
	
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
}
