package com.born.fcs.pm.ws.order.setup;

import java.util.List;

/**
 * 反担保/担保情况order
 *
 *
 * @author wuzj
 *
 */
public class FProjectCounterGuaranteeOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = -2472530301803185977L;
	
	//其他反担保措施
	private String otherCounterGuarntee;
	
	//保证人
	List<FProjectCounterGuaranteeGuarantorOrder> guarantorOrder;
	//抵押
	List<FProjectCounterGuaranteePledgeOrder> pledgeOrder;
	//质押 
	List<FProjectCounterGuaranteePledgeOrder> mortgageOrder;
	
	public List<FProjectCounterGuaranteeGuarantorOrder> getGuarantorOrder() {
		return this.guarantorOrder;
	}
	
	public void setGuarantorOrder(List<FProjectCounterGuaranteeGuarantorOrder> guarantorOrder) {
		this.guarantorOrder = guarantorOrder;
	}
	
	public List<FProjectCounterGuaranteePledgeOrder> getPledgeOrder() {
		return this.pledgeOrder;
	}
	
	public void setPledgeOrder(List<FProjectCounterGuaranteePledgeOrder> pledgeOrder) {
		this.pledgeOrder = pledgeOrder;
	}
	
	public List<FProjectCounterGuaranteePledgeOrder> getMortgageOrder() {
		return this.mortgageOrder;
	}
	
	public void setMortgageOrder(List<FProjectCounterGuaranteePledgeOrder> mortgageOrder) {
		this.mortgageOrder = mortgageOrder;
	}
	
	public String getOtherCounterGuarntee() {
		return this.otherCounterGuarntee;
	}
	
	public void setOtherCounterGuarntee(String otherCounterGuarntee) {
		this.otherCounterGuarntee = otherCounterGuarntee;
	}
}
