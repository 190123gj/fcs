package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeWayPhaseEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 会议纪要 - 收费方式
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectChargeWayInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -2446461992614962316L;
	/** 主键 */
	private long id;
	/** 会议纪要项目ID */
	private long spId;
	/** 收费顺序，1表示首次收费 */
	private int chargeOrder;
	/** 收费阶段 */
	private ChargeWayPhaseEnum phase;
	/** phase 前 before_day 天内 */
	private int beforeDay;
	/** 收费 */
	private double amount;
	/** 元/% 每年 */
	private ChargeTypeEnum amountType;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public int getChargeOrder() {
		return this.chargeOrder;
	}
	
	public void setChargeOrder(int chargeOrder) {
		this.chargeOrder = chargeOrder;
	}
	
	public ChargeWayPhaseEnum getPhase() {
		return this.phase;
	}
	
	public void setPhase(ChargeWayPhaseEnum phase) {
		this.phase = phase;
	}
	
	public int getBeforeDay() {
		return this.beforeDay;
	}
	
	public void setBeforeDay(int beforeDay) {
		this.beforeDay = beforeDay;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public ChargeTypeEnum getAmountType() {
		return this.amountType;
	}
	
	public void setAmountType(ChargeTypeEnum amountType) {
		this.amountType = amountType;
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
