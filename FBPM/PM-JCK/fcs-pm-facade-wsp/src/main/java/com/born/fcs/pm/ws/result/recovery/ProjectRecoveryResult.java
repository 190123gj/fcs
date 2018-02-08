/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:35:58 创建
 */
package com.born.fcs.pm.ws.result.recovery;

import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private ProjectRecoveryInfo projectRecoveryInfo;
	
	public ProjectRecoveryInfo getProjectRecoveryInfo() {
		return this.projectRecoveryInfo;
	}
	
	public void setProjectRecoveryInfo(ProjectRecoveryInfo projectRecoveryInfo) {
		this.projectRecoveryInfo = projectRecoveryInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectRecoveryResult [ProjectRecoveryInfo=");
		builder.append(projectRecoveryInfo);
		builder.append("]");
		return builder.toString();
	}
	
}
