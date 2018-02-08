package com.born.fcs.pm.ws.order.financeaffirm;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 财务确认-资金划付和收费通知Order
 *
 *
 * @author Ji
 *
 */
public class ChargeNoticeCapitalApproproationOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 7218030180515855875L;
	
	private Long id;
	
	private long detailId;
	
	private String type;
	
	private Money useAmount = new Money(0, 0);

	private Money leftAmount = new Money(0, 0);
	
	private String payId;

	public Money getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Money leftAmount) {
		this.leftAmount = leftAmount;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Money getUseAmount() {
		return useAmount;
	}
	
	public void setUseAmount(Money useAmount) {
		this.useAmount = useAmount;
	}
	
	public String getPayId() {
		return payId;
	}
	
	public void setPayId(String payId) {
		this.payId = payId;
	}
	
}
