package com.born.fcs.fm.ws.order.incomeconfirm;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 批量收入确认-明细order
 * @author wuzj
 */
public class IncomeBatchConfirmDetailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 4701564884199452499L;
	
	/** 主键ID */
	private long detailId;
	/** 收入确认ID */
	private long incomeId;
	/** 是否确认 */
	private String isConfirmed;
	/** 收入确认金额 */
	private Money incomeConfirmedAmount = new Money(0, 0);
	
	public void setIncomeConfirmedAmountStr(String incomeConfirmedAmountStr) {
		if (incomeConfirmedAmountStr != null && !"".equals(incomeConfirmedAmountStr)) {
			incomeConfirmedAmount = Money.amout(incomeConfirmedAmountStr);
		}
	}
	
	@Override
	public void check() {
		validateGreaterThan(incomeId, "收入确认ID");
		validateGreaterThan(detailId, "收入确认明细ID");
	}
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}
	
	public String getIsConfirmed() {
		return isConfirmed;
	}
	
	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public Money getIncomeConfirmedAmount() {
		return incomeConfirmedAmount;
	}
	
	public void setIncomeConfirmedAmount(Money incomeConfirmedAmount) {
		this.incomeConfirmedAmount = incomeConfirmedAmount;
	}
	
}
