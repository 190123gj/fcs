package com.born.fcs.pm.ws.info.financeaffirm;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.enums.FinanceAffirmTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 财务确认-资金划付和收费通知info
 *
 * @author Ji
 *
 */
public class ChargeNoticeCapitalApproproationInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8652920502460155267L;
	
	private long id;
	
	private long detailId;
	
	private FinanceAffirmTypeEnum type;
	
	private Money useAmount = new Money(0, 0);

	private Money leftAmount = new Money(0, 0);
	
	private String payId;

	private String isApproval;
	
	private Date rawAddTime;

	public String getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(String isApproval) {
		this.isApproval = isApproval;
	}

	public Money getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Money leftAmount) {
		this.leftAmount = leftAmount;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public FinanceAffirmTypeEnum getType() {
		return type;
	}
	
	public void setType(FinanceAffirmTypeEnum type) {
		this.type = type;
	}
	
	public Money getUseAmount() {
		return useAmount;
	}
	
	public void setUseAmount(Money useAmount) {
		this.useAmount = useAmount;
	}
	
	public String getPayId() {
		return payId;
	}
	
	public void setPayId(String payId) {
		this.payId = payId;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
