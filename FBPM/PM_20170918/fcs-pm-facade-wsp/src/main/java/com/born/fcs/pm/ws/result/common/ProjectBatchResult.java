/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:13:59 创建
 */
package com.born.fcs.pm.ws.result.common;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectBatchResult extends FormBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	public List<ProjectInfo> getProjectInfos() {
		return this.projectInfos;
	}
	
	public void setProjectInfos(List<ProjectInfo> projectInfos) {
		this.projectInfos = projectInfos;
	}
	
	private List<ProjectInfo> projectInfos = new ArrayList<ProjectInfo>();
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectBatchResult [projectInfos=");
		builder.append(projectInfos);
		builder.append(", fcsResultEnum=");
		builder.append(getFcsResultEnum());
		builder.append(", isExecuted()=");
		builder.append(isExecuted());
		builder.append(", isSuccess()=");
		builder.append(isSuccess());
		builder.append("]");
		return builder.toString();
	}
	
}
