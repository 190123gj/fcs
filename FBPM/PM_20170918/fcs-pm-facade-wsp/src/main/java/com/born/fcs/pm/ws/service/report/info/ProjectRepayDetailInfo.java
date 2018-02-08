package com.born.fcs.pm.ws.service.report.info;

import com.yjf.common.lang.util.money.Money;

/**
 * 项目还款明细
 * 
 * @author wuzj
 */
public class ProjectRepayDetailInfo extends ProjectReportInfo {
	
	private static final long serialVersionUID = -8481668601602397865L;
	/** 还款金额 */
	private Money repayAmount = new Money(0, 0);
	/** 还款日期 */
	private String repayTime;
	/** 金额类型 */
	private String repayType;
	
	public Money getRepayAmount() {
		return this.repayAmount;
	}
	
	public void setRepayAmount(Money repayAmount) {
		this.repayAmount = repayAmount;
	}
	
	public String getRepayTime() {
		return this.repayTime;
	}
	
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}
	
	public String getRepayType() {
		return this.repayType;
	}
	
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	
}
