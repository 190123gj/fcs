package com.born.fcs.crm.ws.service.result;

import java.util.List;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public class ValidateCustomerResult extends FcsBaseResult {
	
	private static final long serialVersionUID = -1466403495564491448L;
	/**
	 * 客户类型：pub :公海 ，per：自己的，other:别人的
	 * */
	private String type;
	
	/** 客户经理 */
	private String customerManager;
	/** 是否有参与的项目 */
	private Boolean haveProject = false;
	/** 参与过的项目 */
	private List<ProjectInfo> list;
	/** 客户Id */
	private long userId;
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCustomerManager() {
		return this.customerManager;
	}
	
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	
	public Boolean getHaveProject() {
		return this.haveProject;
	}
	
	public void setHaveProject(Boolean haveProject) {
		this.haveProject = haveProject;
	}
	
	public List<ProjectInfo> getList() {
		return this.list;
	}
	
	public void setList(List<ProjectInfo> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValidateCustomerResult [type=");
		builder.append(type);
		builder.append(", customerManager=");
		builder.append(customerManager);
		builder.append(", haveProject=");
		builder.append(haveProject);
		builder.append(", list=");
		builder.append(list);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
	
}
