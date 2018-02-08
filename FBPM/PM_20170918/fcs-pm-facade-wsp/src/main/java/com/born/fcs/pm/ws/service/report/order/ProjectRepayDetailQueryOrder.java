package com.born.fcs.pm.ws.service.report.order;

/**
 * 项目还款明细
 * @author wuzj
 */
public class ProjectRepayDetailQueryOrder extends ProjectReportQueryOrder {
	
	private static final long serialVersionUID = -6113665123412325462L;
	
	/** 还款开始时间 */
	private String repayTimeStart;
	/** 还款结束时间 */
	private String repayTimeEnd;
	
	public String getRepayTimeStart() {
		return this.repayTimeStart;
	}
	
	public void setRepayTimeStart(String repayTimeStart) {
		this.repayTimeStart = repayTimeStart;
	}
	
	public String getRepayTimeEnd() {
		return this.repayTimeEnd;
	}
	
	public void setRepayTimeEnd(String repayTimeEnd) {
		this.repayTimeEnd = repayTimeEnd;
	}
	
}
