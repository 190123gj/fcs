package com.born.fcs.pm.ws.order.setup;

import java.util.List;

/**
 * 项目立项 - 对外担保情况Order
 *
 * @author wuzj
 *
 */
public class FProjectExternalGuaranteeBatchOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = 3980398848555837737L;
	
	List<FProjectExternalGuaranteeOrder> externalGuaranteeOrder;
	
	@Override
	public Integer getCheckIndex() {
		return 3;
	}	
	
	public List<FProjectExternalGuaranteeOrder> getExternalGuaranteeOrder() {
		return this.externalGuaranteeOrder;
	}
	
	public void setExternalGuaranteeOrder(	List<FProjectExternalGuaranteeOrder> externalGuaranteeOrder) {
		this.externalGuaranteeOrder = externalGuaranteeOrder;
	}
}
