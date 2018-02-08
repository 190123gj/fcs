package com.born.fcs.pm.ws.order.projectRiskReport;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

public class ProjectRiskReportDeleteOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -1682497010477888001L;
	private long reportId;

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
}
