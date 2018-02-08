package com.born.fcs.pm.ws.service.report.order;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 报表系统 - 基层定义报表 - 项目基本情况表 查询 order
 * 
 * @author lirz
 * 
 * 2017-2-9 下午4:19:32
 * 
 */
public class ProjectBaseInfoQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -4878795833684848953L;
	
	private String projectCode;
	private String projectName;
	private int reportYear;
	private int reportMonth;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public int getReportYear() {
		return this.reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return this.reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectBaseInfoQueryOrder [projectCode=");
		builder.append(projectCode);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", reportYear=");
		builder.append(reportYear);
		builder.append(", reportMonth=");
		builder.append(reportMonth);
		builder.append("]");
		return builder.toString();
	}
	
}
