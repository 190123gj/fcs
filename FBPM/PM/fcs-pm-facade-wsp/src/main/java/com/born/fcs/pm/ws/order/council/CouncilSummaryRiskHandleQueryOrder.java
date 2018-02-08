package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 风险处置会会议纪要查询
 */
public class CouncilSummaryRiskHandleQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -8890836295970553294L;
	
	private long councilId;
	
	private long summaryId;
	
	private String summaryCode;
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(long summaryId) {
		this.summaryId = summaryId;
	}
	
	public String getSummaryCode() {
		return this.summaryCode;
	}
	
	public void setSummaryCode(String summaryCode) {
		this.summaryCode = summaryCode;
	}
	
}
