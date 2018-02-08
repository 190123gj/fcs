package com.born.fcs.fm.ws.order.incomeconfirm;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 收入确认-明细
 * @author jil
 *
 */
public class IncomeConfirmDetailQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 3479918900668622680L;
	/** 主键ID */
	private Long id;
	/** 收入确认ID */
	private long incomeId;
	/** 收入期间 */
	private String incomePeriod;
	/** 确认状态 */
	private String confirmStatus;
	/** 系统预估分摊金额 */
	private Money systemEstimatedAmount = new Money(0, 0);
	/** 收入确认金额 */
	private Money incomeConfirmedAmount = new Money(0, 0);
	
	private double incomeConfirmeDouble;
	/** 是否确认 */
	private String isConfirmed;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}
	
	public String getIncomePeriod() {
		return incomePeriod;
	}
	
	public void setIncomePeriod(String incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	
	public String getConfirmStatus() {
		return confirmStatus;
	}
	
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	public Money getSystemEstimatedAmount() {
		return systemEstimatedAmount;
	}
	
	public void setSystemEstimatedAmount(Money systemEstimatedAmount) {
		this.systemEstimatedAmount = systemEstimatedAmount;
	}
	
	public Money getIncomeConfirmedAmount() {
		return incomeConfirmedAmount;
	}
	
	public void setIncomeConfirmedAmount(Money incomeConfirmedAmount) {
		this.incomeConfirmedAmount = incomeConfirmedAmount;
	}
	
	public double getIncomeConfirmeDouble() {
		return incomeConfirmeDouble;
	}
	
	public void setIncomeConfirmeDouble(double incomeConfirmeDouble) {
		this.incomeConfirmeDouble = incomeConfirmeDouble;
	}
	
	public String getIsConfirmed() {
		return isConfirmed;
	}
	
	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
}
