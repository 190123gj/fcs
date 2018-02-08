package com.born.fcs.fm.ws.order.incomeconfirm;


/**
 * 收入确认明细查询
 * @author wuzj
 */
public class IncomeConfirmDetailListQueryOrder extends IncomeConfirmQueryOrder {
	
	private static final long serialVersionUID = 5698796574176224427L;
	
	/** 明细ID */
	private long detailId;
	/** 收入期间 */
	private String incomePeriod;
	/** 确认状态 */
	private String confirmStatus;
	/** 是否确认 */
	private String isConfirmed;
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public String getIncomePeriod() {
		return incomePeriod;
	}
	
	public void setIncomePeriod(String incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	
	public String getConfirmStatus() {
		return confirmStatus;
	}
	
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	public String getIsConfirmed() {
		return isConfirmed;
	}
	
	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
}
