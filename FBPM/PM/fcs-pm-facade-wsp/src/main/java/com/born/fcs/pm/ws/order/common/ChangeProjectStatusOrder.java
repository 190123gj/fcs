package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

public class ChangeProjectStatusOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 23510463852477119L;
	
	/** 项目编号 */
	private String projectCode;
	/** 项目阶段 */
	private ProjectPhasesEnum phases;
	/** 阶段状态 */
	private ProjectPhasesStatusEnum phaseStatus;
	/** 项目状态 */
	private ProjectStatusEnum status;
	
	@Override
	public void check() {
		validateHasText(projectCode, "项目编号");
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public ProjectPhasesEnum getPhases() {
		return this.phases;
	}
	
	public void setPhase(ProjectPhasesEnum phases) {
		this.phases = phases;
	}
	
	public ProjectStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(ProjectStatusEnum status) {
		this.status = status;
	}
	
	public ProjectPhasesStatusEnum getPhaseStatus() {
		return this.phaseStatus;
	}
	
	public void setPhaseStatus(ProjectPhasesStatusEnum phaseStatus) {
		this.phaseStatus = phaseStatus;
	}
	
	public void setPhases(ProjectPhasesEnum phases) {
		this.phases = phases;
	}
	
}
