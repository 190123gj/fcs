package com.born.fcs.fm.ws.order.incomeconfirm;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 收入确认
 * @author jil
 *
 */
public class IncomeConfirmQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 5187406344217788711L;
	
	private String busiTypeName;//业务品种
	/** 收入确认ID */
	private long incomeId;
	/** 收入确认状态(未完成收入确认，已完成收入确认) */
	private String incomeConfirmStatus;
	/** 收入期间（确认期间） */
	private String incomePeriod;
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}
	
	public String getIncomeConfirmStatus() {
		return incomeConfirmStatus;
	}
	
	public void setIncomeConfirmStatus(String incomeConfirmStatus) {
		this.incomeConfirmStatus = incomeConfirmStatus;
	}
	
	public String getIncomePeriod() {
		return incomePeriod;
	}
	
	public void setIncomePeriod(String incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	
}
