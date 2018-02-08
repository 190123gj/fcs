package com.born.fcs.pm.ws.order.council;

import java.util.List;

/**
 * 会议纪要 - 风险处置会
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryRiskHandleBatchOrder extends FCouncilSummaryOrder {
	
	private static final long serialVersionUID = 5621371010786439853L;
	
	private List<FCouncilSummaryRiskHandleOrder> riskHandleOrder;
	
	public List<FCouncilSummaryRiskHandleOrder> getRiskHandleOrder() {
		return this.riskHandleOrder;
	}
	
	public void setRiskHandleOrder(List<FCouncilSummaryRiskHandleOrder> riskHandleOrder) {
		this.riskHandleOrder = riskHandleOrder;
	}
}
