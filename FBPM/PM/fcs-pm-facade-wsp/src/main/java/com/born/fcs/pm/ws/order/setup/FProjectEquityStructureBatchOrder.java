package com.born.fcs.pm.ws.order.setup;

import java.util.List;

/**
 * 项目立项 - 股权结构Order
 *
 * @author wuzj
 *
 */
public class FProjectEquityStructureBatchOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = 3870106064322755899L;
	
	List<FProjectEquityStructureOrder> equityStructureOrder;
	
	private boolean fromCustomer;
	
	public List<FProjectEquityStructureOrder> getEquityStructureOrder() {
		return this.equityStructureOrder;
	}
	
	public void setEquityStructureOrder(List<FProjectEquityStructureOrder> equityStructureOrder) {
		this.equityStructureOrder = equityStructureOrder;
	}
	
	public boolean isFromCustomer() {
		return this.fromCustomer;
	}
	
	public void setFromCustomer(boolean fromCustomer) {
		this.fromCustomer = fromCustomer;
	}
	
}
