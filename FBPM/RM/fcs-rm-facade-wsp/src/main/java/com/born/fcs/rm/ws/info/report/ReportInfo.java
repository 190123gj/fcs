package com.born.fcs.rm.ws.info.report;

import java.util.Date;

import com.born.fcs.rm.ws.enums.ReportCodeEnum;
import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 报表生成记录
 * 
 * @author lirz
 *
 * 2016-8-4 下午6:22:48
 */
public class ReportInfo extends BaseToStringInfo {

	private static final long serialVersionUID = -299541447379131588L;
	
	private long reportId;
	private String reportName;
	private String reportType;
	private ReportCodeEnum reportCode;
	private int reportYear;
	private int reportMonth;
	private String version;
	private long operatorId;
	private String operatorAccount;
	private String operatorName;
	private long deptId;
	private String deptCode;
	private String deptName;
	private String deptPath;
	private String deptPathName;
	private String content;

	private Date rawAddTime;
	private Date rawUpdateTime;
	
	private Object obj;
	
	public long getReportId() {
		return reportId;
	}
	
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}
	
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public ReportCodeEnum getReportCode() {
		return reportCode;
	}
	
	public void setReportCode(ReportCodeEnum reportCode) {
		this.reportCode = reportCode;
	}

	public int getReportYear() {
		return reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}

	public int getReportMonth() {
		return reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}

	public long getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorAccount() {
		return operatorAccount;
	}
	
	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}

	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptPath() {
		return deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}

	public String getDeptPathName() {
		return deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
