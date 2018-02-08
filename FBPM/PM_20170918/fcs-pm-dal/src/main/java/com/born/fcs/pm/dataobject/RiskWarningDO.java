package com.born.fcs.pm.dataobject;

/**
 * 
 * 风险预警列表DO
 * 
 * @author lirz
 * 
 * 2016-4-18 下午3:43:04
 */
public class RiskWarningDO extends FormProjectDO {
	
	private static final long serialVersionUID = 3973072192887622956L;
	
	private long warningId; //主键(单据编号)
	private String signalLevel; //信号等级
	
	private String warningBillType;
	
	public long getWarningId() {
		return warningId;
	}
	
	public void setWarningId(long warningId) {
		this.warningId = warningId;
	}
	
	public String getSignalLevel() {
		return signalLevel;
	}
	
	public void setSignalLevel(String signalLevel) {
		this.signalLevel = signalLevel;
	}
	
	public String getWarningBillType() {
		return this.warningBillType;
	}
	
	public void setWarningBillType(String warningBillType) {
		this.warningBillType = warningBillType;
	}
	
}
