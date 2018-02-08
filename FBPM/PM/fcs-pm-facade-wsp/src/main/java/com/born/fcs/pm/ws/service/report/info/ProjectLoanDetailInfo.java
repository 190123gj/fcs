package com.born.fcs.pm.ws.service.report.info;

import java.util.Date;

import com.yjf.common.lang.util.money.Money;

/**
 * 项目放款明细
 *
 * @author wuzj
 */
public class ProjectLoanDetailInfo extends ProjectReportInfo {
	
	private static final long serialVersionUID = -9119598764764226312L;
	
	/** 放款金额 */
	private Money loanAmount;
	/** 放款时间 */
	private Date loanTime;
	
	public Money getLoanAmount() {
		return this.loanAmount;
	}
	
	public void setLoanAmount(Money loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public Date getLoanTime() {
		return this.loanTime;
	}
	
	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}
	
}
