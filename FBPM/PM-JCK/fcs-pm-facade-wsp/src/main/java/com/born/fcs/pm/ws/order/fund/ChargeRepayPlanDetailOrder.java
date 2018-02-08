package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 收费及 还款计划 *
 *
 * @author wuzj
 *
 */
public class ChargeRepayPlanDetailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 6878306721169299715L;
	
	/** 计划详细ID */
	private long planDetailId;
	/** 计划ID */
	private long planId;
	/** 对应项目编号 */
	private String projectCode;
	/** 收费计划/还款计划 */
	private String planType;
	/** 计划开始时间 */
	private Date startTime;
	private String startTimeStr;
	/** 计划截止时间 */
	private Date endTime;
	private String endTimeStr;
	/** 收费/还款金额 */
	private Money amount = Money.zero();
	/** 收费/还款金额 */
	private String amountStr;
	/** 备注 */
	private String remark;
	
	@Override
	public void check() {
		validateNotNull(startTime, "计划开始时间");
		validateNotNull(endTime, "计划截止时间");
		validateMoneyGreaterThanZero(amount, "计划金额");
		Assert.isTrue(endTime.compareTo(startTime) >= 0, "截止时间不能小于开始时间");
	}
	
	public boolean isNull() {
		return isNull(startTimeStr) && isNull(endTimeStr) && isNull(amountStr);
	}
	
	public long getPlanDetailId() {
		return this.planDetailId;
	}
	
	public void setPlanDetailId(long planDetailId) {
		this.planDetailId = planDetailId;
	}
	
	public long getPlanId() {
		return this.planId;
	}
	
	public void setPlanId(long planId) {
		this.planId = planId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getPlanType() {
		return this.planType;
	}
	
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAmountStr() {
		return this.amountStr;
	}
	
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
	public String getStartTimeStr() {
		return this.startTimeStr;
	}
	
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	
	public String getEndTimeStr() {
		return this.endTimeStr;
	}
	
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}
