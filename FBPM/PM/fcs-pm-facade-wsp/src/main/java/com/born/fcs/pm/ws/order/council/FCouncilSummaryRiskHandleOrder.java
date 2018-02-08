package com.born.fcs.pm.ws.order.council;

import java.util.Date;

import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 风险处置会 - 项目信息
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryRiskHandleOrder extends FCouncilSummaryProjectCreditConditionOrder {
	
	private static final long serialVersionUID = -4256177090395075590L;
	/** 主键 */
	private Long handleId;
	/** 会议纪要ID */
	//private Long summaryId;
	/** 项目编号 */
	//private String projectCode;
	/** 项目名称 */
	//private String projectName;
	/** 对应客户ID */
	//private Long customerId;
	/** 对应客户名称 */
	//private String customerName;
	/** 客户类型 */
	//private String customerType;
	/** 是否展期 */
	private String isExtend;
	/** 展期本金 */
	private Money extendPrincipal = new Money(0, 0);
	/** 展期期限 */
	private Integer extendTimeLimit;
	/** 展期期限单位 */
	private String extendTimeUnit;
	/** 展期说明 */
	private String extendRemark;
	/**
	 * 展期数据 [{extendPrincipal:xx,extendTimeLimit:xx,extendTimeUnit:xx},{
	 * extendPrincipal :xx,extendTimeLimit:xx,extendTimeUnit:xx}]
	 */
	private String extendData;
	/** 是否代偿 */
	private String isComp;
	/** 代偿本金 */
	private Money compPrincipal = new Money(0, 0);
	/** 代偿利息 */
	private String compInterest;
	/** 代偿违约金 */
	private String compPenalty;
	/** 代偿罚息 */
	private String compPenaltyInterest;
	/** 代偿罚息 */
	private String compOther;
	/** 代偿截止时间 */
	private Date compEndTime;
	/** 代偿说明 */
	private String compRemark;
	
	/** 是否重新授信 */
	private String isRedo;
	/** 重新授信项目编号 */
	private String redoProjectCode;
	/** 重新授信项目名称 */
	private String redoProjectName;
	/** 重新授信金额 */
	private Money redoAmount = new Money(0, 0);
	/** 重新授信期限 */
	private int redoTimeLimit;
	/** 重新授信期限单位 */
	private String redoTimeUnit;
	/** 重新授信期限备注 */
	private String redoTimeRemark;
	/** 重新授信业务类型 */
	private String redoBusiType;
	/** 重新授信业务类型名称 */
	private String redoBusiTypeName;
	/** 重新授信描述 */
	private String redo;
	/** 重新授信备注 */
	private String redoRemark;
	
	/** 费用 */
	private Double fee;
	/** 费用类型 */
	private String feeType;
	
	/** 是否其他组合 */
	private String isOther;
	/** 其他组合 */
	private String otherComb;
	
	public void setExtendPrincipalStr(String extendPrincipalStr) {
		if (StringUtil.isNotBlank(extendPrincipalStr)) {
			this.extendPrincipal = Money.amout(extendPrincipalStr);
		}
	}
	
	public void setCompPrincipalStr(String compPrincipalStr) {
		if (StringUtil.isNotBlank(compPrincipalStr)) {
			this.compPrincipal = Money.amout(compPrincipalStr);
		}
	}
	
	public void setRedoAmountStr(String redoAmountStr) {
		if (StringUtil.isNotBlank(redoAmountStr)) {
			this.redoAmount = Money.amout(redoAmountStr);
		}
	}
	
	public void setCompEndTimeStr(String compEndTimeStr) {
		if (StringUtil.isNotBlank(compEndTimeStr)) {
			try {
				this.compEndTime = DateUtil.string2Date(compEndTimeStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Long getHandleId() {
		return this.handleId;
	}
	
	public void setHandleId(Long handleId) {
		this.handleId = handleId;
	}
	
	//	public Long getSummaryId() {
	//		return this.summaryId;
	//	}
	//	
	//	public void setSummaryId(Long summaryId) {
	//		this.summaryId = summaryId;
	//	}
	//	public String getProjectCode() {
	//		return this.projectCode;
	//	}
	//	
	//	public void setProjectCode(String projectCode) {
	//		this.projectCode = projectCode;
	//	}
	//	
	//	public String getProjectName() {
	//		return this.projectName;
	//	}
	//	
	//	public void setProjectName(String projectName) {
	//		this.projectName = projectName;
	//	}
	//	
	//	public Long getCustomerId() {
	//		return this.customerId;
	//	}
	//	
	//	public void setCustomerId(Long customerId) {
	//		this.customerId = customerId;
	//	}
	//	
	//	public String getCustomerName() {
	//		return this.customerName;
	//	}
	//	
	//	public void setCustomerName(String customerName) {
	//		this.customerName = customerName;
	//	}
	//	
	//	public String getCustomerType() {
	//		return this.customerType;
	//	}
	//	
	//	public void setCustomerType(String customerType) {
	//		this.customerType = customerType;
	//	}
	
	public String getIsExtend() {
		return this.isExtend;
	}
	
	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
	}
	
	public Money getExtendPrincipal() {
		return this.extendPrincipal;
	}
	
	public void setExtendPrincipal(Money extendPrincipal) {
		this.extendPrincipal = extendPrincipal;
	}
	
	public Integer getExtendTimeLimit() {
		return this.extendTimeLimit;
	}
	
	public void setExtendTimeLimit(Integer extendTimeLimit) {
		this.extendTimeLimit = extendTimeLimit;
	}
	
	public String getExtendTimeUnit() {
		return this.extendTimeUnit;
	}
	
	public void setExtendTimeUnit(String extendTimeUnit) {
		this.extendTimeUnit = extendTimeUnit;
	}
	
	public String getExtendRemark() {
		return this.extendRemark;
	}
	
	public void setExtendRemark(String extendRemark) {
		this.extendRemark = extendRemark;
	}
	
	public String getExtendData() {
		return this.extendData;
	}
	
	public void setExtendData(String extendData) {
		this.extendData = extendData;
	}
	
	public String getIsComp() {
		return this.isComp;
	}
	
	public void setIsComp(String isComp) {
		this.isComp = isComp;
	}
	
	public Money getCompPrincipal() {
		return this.compPrincipal;
	}
	
	public void setCompPrincipal(Money compPrincipal) {
		this.compPrincipal = compPrincipal;
	}
	
	public String getCompInterest() {
		return this.compInterest;
	}
	
	public void setCompInterest(String compInterest) {
		this.compInterest = compInterest;
	}
	
	public String getCompPenalty() {
		return this.compPenalty;
	}
	
	public void setCompPenalty(String compPenalty) {
		this.compPenalty = compPenalty;
	}
	
	public String getCompPenaltyInterest() {
		return this.compPenaltyInterest;
	}
	
	public void setCompPenaltyInterest(String compPenaltyInterest) {
		this.compPenaltyInterest = compPenaltyInterest;
	}
	
	public String getCompOther() {
		return this.compOther;
	}
	
	public void setCompOther(String compOther) {
		this.compOther = compOther;
	}
	
	public Date getCompEndTime() {
		return this.compEndTime;
	}
	
	public void setCompEndTime(Date compEndTime) {
		this.compEndTime = compEndTime;
	}
	
	public String getCompRemark() {
		return this.compRemark;
	}
	
	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}
	
	public String getIsRedo() {
		return this.isRedo;
	}
	
	public void setIsRedo(String isRedo) {
		this.isRedo = isRedo;
	}
	
	public String getRedoProjectCode() {
		return this.redoProjectCode;
	}
	
	public void setRedoProjectCode(String redoProjectCode) {
		this.redoProjectCode = redoProjectCode;
	}
	
	public String getRedoProjectName() {
		return this.redoProjectName;
	}
	
	public void setRedoProjectName(String redoProjectName) {
		this.redoProjectName = redoProjectName;
	}
	
	public Money getRedoAmount() {
		return this.redoAmount;
	}
	
	public void setRedoAmount(Money redoAmount) {
		this.redoAmount = redoAmount;
	}
	
	public int getRedoTimeLimit() {
		return this.redoTimeLimit;
	}
	
	public void setRedoTimeLimit(int redoTimeLimit) {
		this.redoTimeLimit = redoTimeLimit;
	}
	
	public String getRedoTimeUnit() {
		return this.redoTimeUnit;
	}
	
	public void setRedoTimeUnit(String redoTimeUnit) {
		this.redoTimeUnit = redoTimeUnit;
	}
	
	public String getRedoTimeRemark() {
		return this.redoTimeRemark;
	}
	
	public void setRedoTimeRemark(String redoTimeRemark) {
		this.redoTimeRemark = redoTimeRemark;
	}
	
	public String getRedoBusiType() {
		return this.redoBusiType;
	}
	
	public void setRedoBusiType(String redoBusiType) {
		this.redoBusiType = redoBusiType;
	}
	
	public String getRedoBusiTypeName() {
		return this.redoBusiTypeName;
	}
	
	public void setRedoBusiTypeName(String redoBusiTypeName) {
		this.redoBusiTypeName = redoBusiTypeName;
	}
	
	public String getRedo() {
		return this.redo;
	}
	
	public void setRedo(String redo) {
		this.redo = redo;
	}
	
	public String getRedoRemark() {
		return this.redoRemark;
	}
	
	public void setRedoRemark(String redoRemark) {
		this.redoRemark = redoRemark;
	}
	
	public Double getFee() {
		return this.fee;
	}
	
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	public String getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getIsOther() {
		return this.isOther;
	}
	
	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}
	
	public String getOtherComb() {
		return this.otherComb;
	}
	
	public void setOtherComb(String otherComb) {
		this.otherComb = otherComb;
	}
}
