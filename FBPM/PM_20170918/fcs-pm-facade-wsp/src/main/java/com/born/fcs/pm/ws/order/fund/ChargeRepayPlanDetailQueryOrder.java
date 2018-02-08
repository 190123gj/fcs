package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 收费及 还款计划 *
 *
 * @author wuzj
 *
 */
public class ChargeRepayPlanDetailQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -3577899473880321699L;
	
	/** 计划详细ID */
	private long planDetailId;
	/** 计划ID */
	private long planId;
	/** 对应项目编号 */
	private String projectCode;
	/** 收费计划/还款计划 */
	private String planType;
	/** 计划开始时间 开始 */
	private Date startTime;
	/** 计划开始时间 结束 */
	private Date endTime;
	/** 是否已经通知 */
	private BooleanEnum isNotify;
	
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
	
	public BooleanEnum getIsNotify() {
		return this.isNotify;
	}
	
	public void setIsNotify(BooleanEnum isNotify) {
		this.isNotify = isNotify;
	}
	
}
