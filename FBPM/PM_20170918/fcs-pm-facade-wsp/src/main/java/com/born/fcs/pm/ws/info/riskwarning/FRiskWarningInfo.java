package com.born.fcs.pm.ws.info.riskwarning;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 
 * 风险预警处置
 * 
 * @author lirz
 * 
 * 2016-4-15 下午3:53:21
 */
public class FRiskWarningInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -5573352916509071244L;
	
	private long warningId; //主键
	private String warningBillType;
	//	private long customerId; //客户ID
	//	private String customerName; //客户名称
	private List<FRiskWarningCreditInfo> credits; //客户授信业务基本情况
	private SignalLevelEnum signalLevel; //信号等级
	private String specialSignal; //特别信号(id列表)
	private List<RiskWarningSignalInfo> specialSignalInfos;
	private String specialSignalDesc;
	private String nomalSignal; //一般信号(id列表)
	private List<RiskWarningSignalInfo> nomalSignalInfos;
	private String nomalSignalDesc;
	private String riskSignalDetail; // 风险预警信号详细描述
	private String riskMeasure; //风险防范化解措施
	private String liftingReason;//解除原因风险理由
	private long srcWaningId;//关联的风险处置表
	private Date rawAddTime;
	private Date rawUpdateTime;
	
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
	
	public String getSpecialSignal() {
		return specialSignal;
	}
	
	public void setSpecialSignal(String specialSignal) {
		this.specialSignal = specialSignal;
	}
	
	public List<RiskWarningSignalInfo> getSpecialSignalInfos() {
		return specialSignalInfos;
	}
	
	public void setSpecialSignalInfos(List<RiskWarningSignalInfo> specialSignalInfos) {
		this.specialSignalInfos = specialSignalInfos;
	}
	
	public String getNomalSignal() {
		return nomalSignal;
	}
	
	public void setNomalSignal(String nomalSignal) {
		this.nomalSignal = nomalSignal;
	}
	
	public List<RiskWarningSignalInfo> getNomalSignalInfos() {
		return nomalSignalInfos;
	}
	
	public void setNomalSignalInfos(List<RiskWarningSignalInfo> nomalSignalInfos) {
		this.nomalSignalInfos = nomalSignalInfos;
	}
	
	public String getRiskSignalDetail() {
		return riskSignalDetail;
	}
	
	public void setRiskSignalDetail(String riskSignalDetail) {
		this.riskSignalDetail = riskSignalDetail;
	}
	
	public String getRiskMeasure() {
		return riskMeasure;
	}
	
	public void setRiskMeasure(String riskMeasure) {
		this.riskMeasure = riskMeasure;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<FRiskWarningCreditInfo> getCredits() {
		return credits;
	}
	
	public void setCredits(List<FRiskWarningCreditInfo> credits) {
		this.credits = credits;
	}
	
	public String getSpecialSignalDesc() {
		return specialSignalDesc;
	}
	
	public void setSpecialSignalDesc(String specialSignalDesc) {
		this.specialSignalDesc = specialSignalDesc;
	}
	
	public String getNomalSignalDesc() {
		return nomalSignalDesc;
	}
	
	public void setNomalSignalDesc(String nomalSignalDesc) {
		this.nomalSignalDesc = nomalSignalDesc;
	}
	
	public String getWarningBillType() {
		return this.warningBillType;
	}
	
	public void setWarningBillType(String warningBillType) {
		this.warningBillType = warningBillType;
	}
	
	public String getLiftingReason() {
		return this.liftingReason;
	}
	
	public void setLiftingReason(String liftingReason) {
		this.liftingReason = liftingReason;
	}
	
	public long getSrcWaningId() {
		return this.srcWaningId;
	}
	
	public void setSrcWaningId(long srcWaningId) {
		this.srcWaningId = srcWaningId;
	}
	
}
