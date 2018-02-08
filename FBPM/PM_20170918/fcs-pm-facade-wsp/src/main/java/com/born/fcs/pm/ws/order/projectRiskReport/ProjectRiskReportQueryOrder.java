package com.born.fcs.pm.ws.order.projectRiskReport;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class ProjectRiskReportQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -1682497010477888001L;
	private String projectCode;
	
	private String customerName;
	
	private String reportManName;
	
	private String reportStatus;
	
	private Date startTimeBegin;
	
	private Date startTimeEnd;
	
	private String reportType;
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getReportManName() {
		return reportManName;
	}
	
	public void setReportManName(String reportManName) {
		this.reportManName = reportManName;
	}
	
	public String getReportStatus() {
		return reportStatus;
	}
	
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	
	public Date getStartTimeBegin() {
		return startTimeBegin;
	}
	
	public void setStartTimeBegin(Date startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}
	
	public Date getStartTimeEnd() {
		return startTimeEnd;
	}
	
	public void setStartTimeEnd(Date startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}
	
	public String getReportType() {
		return reportType;
	}
	
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Override
	public String toString() {
		return "ProjectRiskReportQueryOrder{" +
				"projectCode='" + projectCode + '\'' +
				", customerName='" + customerName + '\'' +
				", reportManName='" + reportManName + '\'' +
				", reportStatus='" + reportStatus + '\'' +
				", startTimeBegin=" + startTimeBegin +
				", startTimeEnd=" + startTimeEnd +
				", reportType='" + reportType + '\'' +
				'}';
	}
}
