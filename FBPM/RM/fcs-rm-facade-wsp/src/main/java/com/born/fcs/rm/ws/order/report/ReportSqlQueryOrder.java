package com.born.fcs.rm.ws.order.report;

import com.born.fcs.pm.ws.base.QueryPageBase;

public class ReportSqlQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 5072206819607194316L;
	
	private String reportId;
	
	private String reportName;
	
	private String role;
	
	private String note;
	
	private long userId;
	
	public String getReportId() {
		return this.reportId;
	}
	
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	public String getReportName() {
		return this.reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
