package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.RepayWayPhaseEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 会议纪要 - 还款方式
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectRepayWayInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 7157293976457103812L;
	/** 主键 */
	private long id;
	/** 会议纪要项目ID */
	private long spId;
	/** 还款阶段 */
	private RepayWayPhaseEnum phase;
	/** 到达授信期限截止日后after_day内还款 */
	private int afterDay;
	/** 自 phase 起 第after_year年 */
	private int afterYear;
	/** 自 phase 起 第after_year年 到 第after_year_end年 */
	private int afterYearEnd;
	/** 每 month_period 月 */
	private int monthPeriod;
	/** 偿还率 */
	private double repayRate;
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
	
	public RepayWayPhaseEnum getPhase() {
		return this.phase;
	}
	
	public void setPhase(RepayWayPhaseEnum phase) {
		this.phase = phase;
	}
	
	public int getAfterDay() {
		return this.afterDay;
	}
	
	public void setAfterDay(int afterDay) {
		this.afterDay = afterDay;
	}
	
	public int getAfterYear() {
		return this.afterYear;
	}
	
	public void setAfterYear(int afterYear) {
		this.afterYear = afterYear;
	}
	
	public int getAfterYearEnd() {
		return this.afterYearEnd;
	}
	
	public void setAfterYearEnd(int afterYearEnd) {
		this.afterYearEnd = afterYearEnd;
	}
	
	public int getMonthPeriod() {
		return this.monthPeriod;
	}
	
	public void setMonthPeriod(int monthPeriod) {
		this.monthPeriod = monthPeriod;
	}
	
	public double getRepayRate() {
		return this.repayRate;
	}
	
	public void setRepayRate(double repayRate) {
		this.repayRate = repayRate;
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
