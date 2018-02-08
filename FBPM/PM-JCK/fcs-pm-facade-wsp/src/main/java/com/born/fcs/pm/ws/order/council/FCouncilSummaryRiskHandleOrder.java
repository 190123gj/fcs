package com.born.fcs.pm.ws.order.council;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 风险处置会 - 项目信息
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryRiskHandleOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4256177090395075590L;
	/** 主键 */
	private Long handleId;
	/** 会议纪要ID */
	private Long summaryId;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 对应客户ID */
	private Long customerId;
	/** 对应客户名称 */
	private String customerName;
	/** 客户类型 */
	private String customerType;
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
	/** 是否代偿 */
	private String isComp;
	/** 代偿本金 */
	private Money compPrincipal = new Money(0, 0);
	/** 代偿利息 */
	private Money compInterest = new Money(0, 0);
	/** 代偿违约金 */
	private Money compPenalty = new Money(0, 0);
	/** 代偿罚息 */
	private Money compPenaltyInterest = new Money(0, 0);
	/** 代偿罚息 */
	private Money compOther = new Money(0, 0);
	/** 代偿截止时间 */
	private Date compEndTime;
	/** 代偿说明 */
	private String compRemark;
	/** 是否其他组合 */
	private String isOther;
	/** 其他组合 */
	private String other;
	
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
	
	public void setCompInterestStr(String compInterestStr) {
		if (StringUtil.isNotBlank(compInterestStr)) {
			this.compInterest = Money.amout(compInterestStr);
		}
	}
	
	public void setCompPenaltyStr(String compPenaltyStr) {
		if (StringUtil.isNotBlank(compPenaltyStr)) {
			this.compPenalty = Money.amout(compPenaltyStr);
		}
	}
	
	public void setCompPenaltyInterestStr(String compPenaltyInterestStr) {
		if (StringUtil.isNotBlank(compPenaltyInterestStr)) {
			this.compPenaltyInterest = Money.amout(compPenaltyInterestStr);
		}
	}
	
	public void setCompOtherStr(String compOtherStr) {
		if (StringUtil.isNotBlank(compOtherStr)) {
			this.compOther = Money.amout(compOtherStr);
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
	
	public Long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(Long summaryId) {
		this.summaryId = summaryId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
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
	
	public Money getCompInterest() {
		return this.compInterest;
	}
	
	public void setCompInterest(Money compInterest) {
		this.compInterest = compInterest;
	}
	
	public Money getCompPenalty() {
		return this.compPenalty;
	}
	
	public void setCompPenalty(Money compPenalty) {
		this.compPenalty = compPenalty;
	}
	
	public Money getCompPenaltyInterest() {
		return this.compPenaltyInterest;
	}
	
	public void setCompPenaltyInterest(Money compPenaltyInterest) {
		this.compPenaltyInterest = compPenaltyInterest;
	}
	
	public Money getCompOther() {
		return this.compOther;
	}
	
	public void setCompOther(Money compOther) {
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
	
	public String getIsOther() {
		return this.isOther;
	}
	
	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}
	
	public String getOther() {
		return this.other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
}
