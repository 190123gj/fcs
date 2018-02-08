package com.born.fcs.pm.ws.info.riskwarning;

import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.info.common.FormProjectInfo;

/**
 * 风险预警处置
 * 
 * @author lirz
 * 
 * 2016-4-18 下午3:12:20
 */
public class RiskWarningInfo extends FormProjectInfo {
	
	private static final long serialVersionUID = -3110556206638261899L;
	
	private long warningId; //主键(单据编号)
	private SignalLevelEnum signalLevel; //信号等级
	
	private String warningBillType;
	
	public long getWarningId() {
		return warningId;
	}
	
	public void setWarningId(long warningId) {
		this.warningId = warningId;
	}
	
	public SignalLevelEnum getSignalLevel() {
		return signalLevel;
	}
	
	public void setSignalLevel(SignalLevelEnum signalLevel) {
		this.signalLevel = signalLevel;
	}
	
	public String getWarningBillType() {
		return this.warningBillType;
	}
	
	public void setWarningBillType(String warningBillType) {
		this.warningBillType = warningBillType;
	}
	
}
