package com.born.fcs.rm.ws.order.report;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 查询报表
 * 
 * @author lirz
 * 
 * 2016-8-5 上午11:49:00
 */
public class ReportBaseQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 5598259660463184659L;
	
	private String reportCode;
	private int reportYear;
	private int reportMonth;
	
	public String getReportCode() {
		return reportCode;
	}
	
	public void setReportCode(String reportCode) {
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
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
