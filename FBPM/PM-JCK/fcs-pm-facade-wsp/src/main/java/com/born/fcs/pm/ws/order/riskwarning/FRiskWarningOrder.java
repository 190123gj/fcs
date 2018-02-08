package com.born.fcs.pm.ws.order.riskwarning;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 
 * 风险预警处置
 * 
 * @author lirz
 * 
 * 2016-4-15 下午3:53:21
 */
public class FRiskWarningOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 4883375774862731255L;
	
	//========== properties ==========
	
	private long warningId; //主键
	private long customerId; //客户ID
	private String customerName; //客户名称
	/**
	 * 单据类型(风险预警处理表,解除风险预警)
	 */
	private String warningBillType;
	private List<FRiskWarningCreditOrder> warningCredits; //授信业务基本情况
	private SignalLevelEnum signalLevel; //信号等级
	private String specialSignal; //特别信号(id列表)
	private String specialSignalDesc;
	private String nomalSignal; //一般信号(id列表)
	private String nomalSignalDesc;
	private String riskSignalDetail; // 风险预警信号详细描述
	private String riskMeasure; //风险防范化解措施
	private String liftingReason;//解除原因风险理由
	private long srcWaningId;//关联风险预警处理表
	private String state; //save or submit 暂存或提交
	
	//========== getters and setters ==========
	
	public long getWarningId() {
		return warningId;
	}
	
	@Override
	public void check() {
		super.check();
		if (this.warningId == 0) {
			validateHasText(warningBillType, "预警单据类型");
		}
	}
	
	public void setWarningId(long warningId) {
		this.warningId = warningId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public SignalLevelEnum getSignalLevel() {
		return signalLevel;
	}
	
	public void setSignalLevel(SignalLevelEnum signalLevel) {
		this.signalLevel = signalLevel;
	}
	
	public String getSignalLevelStr() {
		if (!isNull(this.specialSignal)) {
			return SignalLevelEnum.SPECIAL.code();
		} else if (!isNull(this.nomalSignal)) {
			return SignalLevelEnum.NOMAL.code();
		} else {
			return "";
		}
	}
	
	public void setSignalLevelStr(String code) {
		this.signalLevel = SignalLevelEnum.getByCode(code);
	}
	
	public String getSpecialSignal() {
		return specialSignal;
	}
	
	public void setSpecialSignal(String specialSignal) {
		this.specialSignal = specialSignal;
	}
	
	public String getNomalSignal() {
		return nomalSignal;
	}
	
	public void setNomalSignal(String nomalSignal) {
		this.nomalSignal = nomalSignal;
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
	
	public List<FRiskWarningCreditOrder> getWarningCredits() {
		return warningCredits;
	}
	
	public void setWarningCredits(List<FRiskWarningCreditOrder> warningCredits) {
		this.warningCredits = warningCredits;
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
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
		if ("SAVE".equals(state)) {
			super.setCheckStatus(0);
		} else if ("SUBMIT".equals(state)) {
			super.setCheckStatus(1);
		}
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
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
