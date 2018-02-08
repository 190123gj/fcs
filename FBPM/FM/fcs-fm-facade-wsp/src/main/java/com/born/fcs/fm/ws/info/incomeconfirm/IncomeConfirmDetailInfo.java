package com.born.fcs.fm.ws.info.incomeconfirm;

import java.util.Date;

import com.born.fcs.fm.ws.enums.ConfirmStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收入确认 -明细
 * @author jil
 *
 */
public class IncomeConfirmDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -2447639351040730533L;
	/** 主键ID */
	private long id;
	/** 收入确认ID */
	private long incomeId;
	/** 收入期间 */
	private String incomePeriod;
	/** 确认状态 */
	private ConfirmStatusEnum confirmStatus;
	/** 系统预估分摊金额 */
	private Money systemEstimatedAmount = new Money(0, 0);
	/** 收入确认金额 */
	private Money incomeConfirmedAmount = new Money(0, 0);
	/** 是否确认 */
	private BooleanEnum isConfirmed;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}
	
	public String getIncomePeriod() {
		return incomePeriod;
	}
	
	public void setIncomePeriod(String incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	
	public ConfirmStatusEnum getConfirmStatus() {
		return confirmStatus;
	}
	
	public void setConfirmStatus(ConfirmStatusEnum confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	public Money getSystemEstimatedAmount() {
		return systemEstimatedAmount;
	}
	
	public void setSystemEstimatedAmount(Money systemEstimatedAmount) {
		this.systemEstimatedAmount = systemEstimatedAmount;
	}
	
	public Money getIncomeConfirmedAmount() {
		return incomeConfirmedAmount;
	}
	
	public void setIncomeConfirmedAmount(Money incomeConfirmedAmount) {
		this.incomeConfirmedAmount = incomeConfirmedAmount;
	}
	
	public BooleanEnum getIsConfirmed() {
		return isConfirmed;
	}
	
	public void setIsConfirmed(BooleanEnum isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
