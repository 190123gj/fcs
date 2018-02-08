package com.born.fcs.pm.ws.order.fund;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 收费及 还款计划 *
 *
 * @author wuzj
 *
 */
public class ChargeRepayPlanQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -161046047010678178L;
	
	/** 计划ID */
	private long planId;
	/** 是否收费计划 */
	private BooleanEnum isChargePlan;
	/** 是否还款计划 */
	private BooleanEnum isRepayPlan;
	/** 是否查询明细 */
	private boolean queryDetail;
	/** 是否确认录入 */
	private String isAffirm;
	
	public long getPlanId() {
		return this.planId;
	}
	
	public void setPlanId(long planId) {
		this.planId = planId;
	}
	
	public BooleanEnum getIsChargePlan() {
		return this.isChargePlan;
	}
	
	public void setIsChargePlan(BooleanEnum isChargePlan) {
		this.isChargePlan = isChargePlan;
	}
	
	public BooleanEnum getIsRepayPlan() {
		return this.isRepayPlan;
	}
	
	public void setIsRepayPlan(BooleanEnum isRepayPlan) {
		this.isRepayPlan = isRepayPlan;
	}
	
	public boolean isQueryDetail() {
		return this.queryDetail;
	}
	
	public void setQueryDetail(boolean queryDetail) {
		this.queryDetail = queryDetail;
	}
	
	public String getIsAffirm() {
		return isAffirm;
	}
	
	public void setIsAffirm(String isAffirm) {
		this.isAffirm = isAffirm;
	}
	
}
