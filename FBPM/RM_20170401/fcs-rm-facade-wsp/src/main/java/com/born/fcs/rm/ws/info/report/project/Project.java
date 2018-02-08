package com.born.fcs.rm.ws.info.report.project;

import java.util.List;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 项目信息
 * 
 * @author lirz
 * 
 * 2016-8-10 上午10:10:59
 */
public class Project extends BaseToStringInfo {
	
	private static final long serialVersionUID = 7892877337974418761L;
	
	private ProjectBaseInfo projectBase; //项目基本信息
	
	private List<ProjectItemInfo> repays; //还款计划
	private List<ProjectItemInfo> charges; //收费计划
	private List<ProjectItemInfo> operations; //运行情况
	private List<ProjectItemInfo> incomes; //收入情况
	private List<ProjectItemInfo> risks; //风险情况
	
	public ProjectBaseInfo getProjectBase() {
		return projectBase;
	}
	
	public void setProjectBase(ProjectBaseInfo projectBase) {
		this.projectBase = projectBase;
	}
	
	public List<ProjectItemInfo> getRepays() {
		return repays;
	}
	
	public void setRepays(List<ProjectItemInfo> repays) {
		this.repays = repays;
	}
	
	public List<ProjectItemInfo> getCharges() {
		return charges;
	}
	
	public void setCharges(List<ProjectItemInfo> charges) {
		this.charges = charges;
	}
	
	public List<ProjectItemInfo> getOperations() {
		return operations;
	}
	
	public void setOperations(List<ProjectItemInfo> operations) {
		this.operations = operations;
	}
	
	public List<ProjectItemInfo> getIncomes() {
		return incomes;
	}
	
	public void setIncomes(List<ProjectItemInfo> incomes) {
		this.incomes = incomes;
	}
	
	public List<ProjectItemInfo> getRisks() {
		return risks;
	}
	
	public void setRisks(List<ProjectItemInfo> risks) {
		this.risks = risks;
	}
}
