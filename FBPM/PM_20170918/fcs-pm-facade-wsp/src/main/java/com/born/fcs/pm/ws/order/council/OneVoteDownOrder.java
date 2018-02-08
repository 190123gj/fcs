package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 
 *
 *
 * @author wuzj
 *
 */
public class OneVoteDownOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 2758127757224798883L;
	
	/**
	 * 会议纪要的ID
	 */
	private long spId;
	
	/**
	 * 原因
	 */
	private String reason;
	
	/**
	 * 一票权
	 */
	private OneVoteResultEnum oneVoteResult;
	
	@Override
	public void check() {
		validateGreaterThan(spId, "会议纪要项目ID");
		validateNotNull(oneVoteResult, "一票决定权");
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public OneVoteResultEnum getOneVoteResult() {
		return this.oneVoteResult;
	}
	
	public void setOneVoteResult(OneVoteResultEnum oneVoteResult) {
		this.oneVoteResult = oneVoteResult;
	}
	
}
