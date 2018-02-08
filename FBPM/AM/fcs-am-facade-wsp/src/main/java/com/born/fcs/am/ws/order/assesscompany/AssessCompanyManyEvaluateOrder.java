package com.born.fcs.am.ws.order.assesscompany;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 评估公司Order
 *
 * @author jil
 *
 */
public class AssessCompanyManyEvaluateOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5996074505958140397L;
	
	public List<AssessCompanyEvaluateOrder> getListManyOrders() {
		return listManyOrders;
	}
	
	public void setListManyOrders(List<AssessCompanyEvaluateOrder> listManyOrders) {
		this.listManyOrders = listManyOrders;
	}
	
	List<AssessCompanyEvaluateOrder> listManyOrders;
	
}
