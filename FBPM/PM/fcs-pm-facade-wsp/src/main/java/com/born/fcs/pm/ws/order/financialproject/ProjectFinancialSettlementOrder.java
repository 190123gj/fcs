package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/** 结息 */
public class ProjectFinancialSettlementOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -8804747305768260343L;
	
	/** 项目编号 */
	private String projectCode;
	/** 结息时间 */
	private Date settlementTime;
	/** 结息金额 */
	private Money settlementAmount = Money.zero();
	
	@Override
	public void check() {
		validateNotNull(projectCode, "项目编号");
		validateNotNull(settlementTime, "结息时间");
		Assert.isTrue(settlementAmount.getCent() > 0, "结息金额必须大于0");
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Date getSettlementTime() {
		return this.settlementTime;
	}
	
	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	
	public Money getSettlementAmount() {
		return this.settlementAmount;
	}
	
	public void setSettlementAmount(Money settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	
}
