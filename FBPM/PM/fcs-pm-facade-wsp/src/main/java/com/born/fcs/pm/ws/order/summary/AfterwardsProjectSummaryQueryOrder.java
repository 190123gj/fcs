/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.summary;

import com.born.fcs.pm.ws.base.QueryFormBase;

/**
 * 保后汇总QueryOrder
 *
 * @author jil
 *
 */
public class AfterwardsProjectSummaryQueryOrder extends QueryFormBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6585846223242686345L;
	
	private Long summaryId;
	
	private String deptCode;
	
	private String deptName;
	
	private String reportPeriod;
	
	private String submitManName;
	
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
	
	public String getReportPeriod() {
		return reportPeriod;
	}
	
	public void setReportPeriod(String reportPeriod) {
		this.reportPeriod = reportPeriod;
	}
	
	public String getSubmitManName() {
		return submitManName;
	}
	
	public void setSubmitManName(String submitManName) {
		this.submitManName = submitManName;
	}
	
	public Long getSummaryId() {
		return summaryId;
	}
	
	public void setSummaryId(Long summaryId) {
		this.summaryId = summaryId;
	}
	
}
