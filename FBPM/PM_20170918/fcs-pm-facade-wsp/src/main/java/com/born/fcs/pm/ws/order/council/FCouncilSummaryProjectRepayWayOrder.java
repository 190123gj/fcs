package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 会议纪要 - 还款方式
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectRepayWayOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 3315836580483881543L;
	/** 主键 */
	private Long id;
	/** 会议纪要项目ID */
	private Long spId;
	/** 还款阶段 */
	private String phase;
	/** 到达授信期限截止日后after_day内还款 */
	private Integer afterDay;
	/** 自 phase 起 第after_year年 */
	private Integer afterYear;
	/** 自 phase 起 第after_year年 到 第after_year_end年 */
	private Integer afterYearEnd;
	/** 每 month_period 月 */
	private Integer monthPeriod;
	/** 偿还率 */
	private Double repayRate;
	
	public boolean isNull() {
		return isNull(phase);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSpId() {
		return this.spId;
	}
	
	public void setSpId(Long spId) {
		this.spId = spId;
	}
	
	public String getPhase() {
		return this.phase;
	}
	
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	public Integer getAfterDay() {
		return this.afterDay;
	}
	
	public void setAfterDay(Integer afterDay) {
		this.afterDay = afterDay;
	}
	
	public Integer getAfterYear() {
		return this.afterYear;
	}
	
	public void setAfterYear(Integer afterYear) {
		this.afterYear = afterYear;
	}
	
	public Integer getAfterYearEnd() {
		return this.afterYearEnd;
	}
	
	public void setAfterYearEnd(Integer afterYearEnd) {
		this.afterYearEnd = afterYearEnd;
	}
	
	public Integer getMonthPeriod() {
		return this.monthPeriod;
	}
	
	public void setMonthPeriod(Integer monthPeriod) {
		this.monthPeriod = monthPeriod;
	}
	
	public Double getRepayRate() {
		return this.repayRate;
	}
	
	public void setRepayRate(Double repayRate) {
		this.repayRate = repayRate;
	}
	
}
