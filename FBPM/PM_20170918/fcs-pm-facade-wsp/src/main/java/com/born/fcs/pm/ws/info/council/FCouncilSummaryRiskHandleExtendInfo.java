package com.born.fcs.pm.ws.info.council;

import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 风险处置会 - 展期数据
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryRiskHandleExtendInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8137089545309033226L;
	/** 展期本金 */
	private Money extendPrincipal = new Money(0, 0);
	/** 展期期限 */
	private int extendTimeLimit;
	/** 展期期限单位 */
	private TimeUnitEnum extendTimeUnit;
	
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
}
