/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:24:24 创建
 */
package com.born.fcs.pm.ws.order.recovery;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = -5062288103928823290L;
	
	/** 项目编号 */
	private String projectCode;
	
	/** 客户名称 */
	private String customerName;
	
	private String recoveryName;
	
	/** 客户经理 */
	private String busiManagerName;
	
	/** 法务经理名 */
	private String legalManagerName;
	
	//董事长 总经理 看全部,法务经理 风险处置会成员 看自己的,添加2参数解决
	/** 是否查看全部 */
	private BooleanEnum isAllFind;
	
	/** 查询者用户id */
	private Long userId;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public BooleanEnum getIsAllFind() {
		return this.isAllFind;
	}
	
	public void setIsAllFind(BooleanEnum isAllFind) {
		this.isAllFind = isAllFind;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getLegalManagerName() {
		return this.legalManagerName;
	}
	
	public void setLegalManagerName(String legalManagerName) {
		this.legalManagerName = legalManagerName;
	}
	
	public String getRecoveryName() {
		return this.recoveryName;
	}
	
	public void setRecoveryName(String recoveryName) {
		this.recoveryName = recoveryName;
	}
	
}
