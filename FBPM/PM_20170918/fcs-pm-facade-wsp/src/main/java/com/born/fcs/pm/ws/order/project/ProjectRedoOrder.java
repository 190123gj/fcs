package com.born.fcs.pm.ws.order.project;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 项目重新授信
 *
 * @author wuzj
 *
 */
public class ProjectRedoOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4938315889911813533L;
	
	/** 风险处置ID */
	private long handleId;
	/** 项目编号 */
	private String projectCode;
	
	public void check() {
		validateGreaterThan(handleId, "风险处置ID");
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getHandleId() {
		return this.handleId;
	}
	
	public void setHandleId(long handleId) {
		this.handleId = handleId;
	}
}
