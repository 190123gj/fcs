package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 会议纪要 - 收费方式
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectChargeWayOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 4923063863659363145L;
	
	/** 主键 */
	private Integer id;
	/** 会议纪要项目ID */
	private Integer spId;
	/** 收费顺序，1表示首次收费 */
	private Integer chargeOrder;
	/** 收费阶段 */
	private String phase;
	/** phase 前 before_day 天内 */
	private Integer beforeDay;
	/** 收费 */
	private Double amount;
	/** 元/% 每年 */
	private String amountType;
	
	public boolean isNull() {
		return isNull(phase) && isNull(beforeDay);
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSpId() {
		return this.spId;
	}
	
	public void setSpId(Integer spId) {
		this.spId = spId;
	}
	
	public Integer getChargeOrder() {
		return this.chargeOrder;
	}
	
	public void setChargeOrder(Integer chargeOrder) {
		this.chargeOrder = chargeOrder;
	}
	
	public String getPhase() {
		return this.phase;
	}
	
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	public Integer getBeforeDay() {
		return this.beforeDay;
	}
	
	public void setBeforeDay(Integer beforeDay) {
		this.beforeDay = beforeDay;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getAmountType() {
		return this.amountType;
	}
	
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	
}
