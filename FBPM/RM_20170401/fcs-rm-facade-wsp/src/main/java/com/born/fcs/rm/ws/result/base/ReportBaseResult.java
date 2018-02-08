package com.born.fcs.rm.ws.result.base;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.rm.ws.info.report.ReportInfo;

public class ReportBaseResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 5565388893810134298L;
	
	private ReportInfo report;
	
	public ReportInfo getReport() {
		return report;
	}
	
	public void setReport(ReportInfo report) {
		this.report = report;
	}
	
}
