package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 风险处置会 - 处置方案
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryRiskHandleInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -7150819324879461183L;
	
	/** 主键 */
	private long handleId;
	/** 会议纪要ID */
	private long summaryId;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 对应客户ID */
	private long customerId;
	/** 对应客户名称 */
	private String customerName;
	/** 客户类型 */
	private CustomerTypeEnum customerType;
	/** 是否展期 */
	private BooleanEnum isExtend;
	/** 展期本金 */
	private Money extendPrincipal = new Money(0, 0);
	/** 展期期限 */
	private int extendTimeLimit;
	/** 展期期限单位 */
	private TimeUnitEnum extendTimeUnit;
	/** 展期说明 */
	private String extendRemark;
	/** 是否代偿 */
	private BooleanEnum isComp;
	/** 代偿本金 */
	private Money compPrincipal = new Money(0, 0);
	/** 代偿利息 */
	private Money compInterest = new Money(0, 0);
	/** 代偿违约金 */
	private Money compPenalty = new Money(0, 0);
	/** 代偿罚息 */
	private Money compPenaltyInterest = new Money(0, 0);
	/** 代偿代偿其他 */
	private Money compOther = new Money(0, 0);
	/** 代偿截止时间 */
	private Date compEndTime;
	/** 代偿说明 */
	private String compRemark;
	/** 是否其他组合 */
	private BooleanEnum isOther;
	/** 其他组合 */
	private String other;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	/** 在保月余额 */
	private Money inGuaranteeAmount;
	
	public long getHandleId() {
		return this.handleId;
	}
	
	public void setHandleId(long handleId) {
		this.handleId = handleId;
	}
	
	public long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(long summaryId) {
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
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public CustomerTypeEnum getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}
	
	public BooleanEnum getIsExtend() {
		return this.isExtend;
	}
	
	public void setIsExtend(BooleanEnum isExtend) {
		this.isExtend = isExtend;
	}
	
	public Money getExtendPrincipal() {
		return this.extendPrincipal;
	}
	
	public void setExtendPrincipal(Money extendPrincipal) {
		this.extendPrincipal = extendPrincipal;
	}
	
	public int getExtendTimeLimit() {
		return this.extendTimeLimit;
	}
	
	public void setExtendTimeLimit(int extendTimeLimit) {
		this.extendTimeLimit = extendTimeLimit;
	}
	
	public TimeUnitEnum getExtendTimeUnit() {
		return this.extendTimeUnit;
	}
	
	public void setExtendTimeUnit(TimeUnitEnum extendTimeUnit) {
		this.extendTimeUnit = extendTimeUnit;
	}
	
	public String getExtendRemark() {
		return this.extendRemark;
	}
	
	public void setExtendRemark(String extendRemark) {
		this.extendRemark = extendRemark;
	}
	
	public BooleanEnum getIsComp() {
		return this.isComp;
	}
	
	public void setIsComp(BooleanEnum isComp) {
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
	
	public BooleanEnum getIsOther() {
		return this.isOther;
	}
	
	public void setIsOther(BooleanEnum isOther) {
		this.isOther = isOther;
	}
	
	public String getOther() {
		return this.other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public Money getInGuaranteeAmount() {
		return this.inGuaranteeAmount;
	}
	
	public void setInGuaranteeAmount(Money inGuaranteeAmount) {
		this.inGuaranteeAmount = inGuaranteeAmount;
	}
	
}
