package com.born.fcs.fm.ws.order.incomeconfirm;

import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 批量收入确认
 * @author wuzj
 *
 */
public class IncomeBatchConfirmOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 8360031636375943125L;
	
	/** 收入确认明细order */
	private List<IncomeBatchConfirmDetailOrder> confirmOrder;
	
	@Override
	public void check() {
		Assert.isTrue(confirmOrder != null && confirmOrder.size() > 0, "确认明细不能为空!");
		
	}
	
	public List<IncomeBatchConfirmDetailOrder> getConfirmOrder() {
		return confirmOrder;
	}
	
	public void setConfirmOrder(List<IncomeBatchConfirmDetailOrder> confirmOrder) {
		this.confirmOrder = confirmOrder;
	}
	
}
