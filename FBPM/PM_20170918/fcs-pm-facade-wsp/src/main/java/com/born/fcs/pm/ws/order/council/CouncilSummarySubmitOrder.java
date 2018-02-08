package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 董事长通过会议纪要
 * 
 * @author wuzj
 */
public class CouncilSummarySubmitOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -3298130944037422773L;
	/** 会议纪要 */
	private long summaryId;
	
	public long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(long summaryId) {
		this.summaryId = summaryId;
	}
}
