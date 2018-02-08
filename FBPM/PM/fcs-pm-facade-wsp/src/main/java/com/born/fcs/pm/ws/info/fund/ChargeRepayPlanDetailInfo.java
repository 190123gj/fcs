package com.born.fcs.pm.ws.info.fund;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收费及 还款计划 *
 *
 * @author wuzj
 *
 */
public class ChargeRepayPlanDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8062786399718857972L;
	
	/** 计划详细ID */
	private long planDetailId;
	/** 计划ID */
	private long planId;
	/** 对应项目编号 */
	private String projectCode;
	/** 收费计划/还款计划 */
	private PlanTypeEnum planType;
	/** 计划开始时间 */
	private Date startTime;
	/** 计划截止时间 */
	private Date endTime;
	/** 收费/还款金额 */
	private Money amount = Money.zero();
	/** 是否已经通知 */
	private BooleanEnum isNotify;
	/** 备注 */
	private String remark;
	/***/
	private Date rawAddTime;
	/***/
	private Date rawUpdateTime;
	
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
	
	public PlanTypeEnum getPlanType() {
		return this.planType;
	}
	
	public void setPlanType(PlanTypeEnum planType) {
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
	
	public BooleanEnum getIsNotify() {
		return this.isNotify;
	}
	
	public void setIsNotify(BooleanEnum isNotify) {
		this.isNotify = isNotify;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
}
