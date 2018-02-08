package com.born.fcs.crm.ws.service.order;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class TransferAllocationOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -2104280362540287222L;
	/** 保存主键Id */
	private long relationId;
	/** 客户户Id */
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
	/** 操作人ID */
	private long operId;
	/** 操作人名 */
	private String operName;
	
	/** 部门编号全称 */
	private String depPath;
	/** 操作类型：YJ:移交； FP:分配 */
	private String type;
	
	@Override
	public void check() {
		validateHasZore(userId, "客户Id");
		//		validateHasZore(depId, "部门Id");
		//		validateHasZore(directorId, "总监Id");
		validateHasZore(operId, "操作人Id");
		validateHasText(depPath, "部门编号全称");
		if ("YJ".equals(type)) {
			//移交，必须有客户经理
			validateHasZore(customerManagerId, "客户经理Id");
		}
	}
	
	public String getDepPath() {
		return this.depPath;
	}
	
	public void setDepPath(String depPath) {
		this.depPath = depPath;
	}
	
	public long getRelationId() {
		return this.relationId;
	}
	
	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}
	
	public long getOperId() {
		return this.operId;
	}
	
	public void setOperId(long operId) {
		this.operId = operId;
	}
	
	public String getOperName() {
		return this.operName;
	}
	
	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransferAllocationOrder [relationId=");
		builder.append(relationId);
		builder.append(", userId=");
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
		builder.append(", operId=");
		builder.append(operId);
		builder.append(", operName=");
		builder.append(operName);
		builder.append(", depPath=");
		builder.append(depPath);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
}
