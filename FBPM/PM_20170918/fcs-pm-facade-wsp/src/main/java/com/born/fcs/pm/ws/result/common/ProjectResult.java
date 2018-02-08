package com.born.fcs.pm.ws.result.common;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 
 * 项目操作结果
 *
 * @author wuzj
 *
 */
public class ProjectResult extends FormBaseResult {
	
	private static final long serialVersionUID = -8340386935027931145L;
	
	private ProjectInfo project;
	
	public ProjectInfo getProject() {
		return this.project;
	}
	
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
}
