package com.born.fcs.crm.ws.service.order;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 客户移交分配
 * */
public class DistributionOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -9094343097754419322L;
	
	/** 用户Id */
	private long userId;
	/** 客户经理ID */
	private long customerManagerId;
	/** 客户经理 */
	private String customerManager;
	/** 业务总监ID */
	private long directorId;
	/** 业务总监 */
	private String director;
	/** 部门Id */
	private long depId;
	/** 部门名 */
	private String depName;
	/** 全部门 */
	private String depPath;
	
	@Override
	public void check() {
		validateHasZore(userId, "用户Id不能为空");
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getCustomerManager() {
		return this.customerManager;
	}
	
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	
	public String getDirector() {
		return this.director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public long getCustomerManagerId() {
		return this.customerManagerId;
	}
	
	public void setCustomerManagerId(long customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	
	public long getDirectorId() {
		return this.directorId;
	}
	
	public void setDirectorId(long directorId) {
		this.directorId = directorId;
	}
	
	public long getDepId() {
		return this.depId;
	}
	
	public void setDepId(long depId) {
		this.depId = depId;
	}
	
	public String getDepName() {
		return this.depName;
	}
	
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	public String getDepPath() {
		return this.depPath;
	}
	
	public void setDepPath(String depPath) {
		this.depPath = depPath;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DistributionOrder [userId=");
		builder.append(userId);
		builder.append(", customerManagerId=");
		builder.append(customerManagerId);
		builder.append(", customerManager=");
		builder.append(customerManager);
		builder.append(", directorId=");
		builder.append(directorId);
		builder.append(", director=");
		builder.append(director);
		builder.append(", depId=");
		builder.append(depId);
		builder.append(", depName=");
		builder.append(depName);
		builder.append(", depPath=");
		builder.append(depPath);
		builder.append("]");
		return builder.toString();
	}
	
}
