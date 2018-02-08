package com.born.fcs.pm.ws.service.report.info;

import java.util.Date;

import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目划付明细
 * 
 * @author wuzj
 */
public class ProjectPayDetailInfo extends ProjectReportInfo {
	
	private static final long serialVersionUID = -5447481774790219498L;
	/** 费用种类 */
	private PaymentMenthodEnum feeType;
	/** 划付金额 */
	private Money payAmount = new Money(0, 0);
	/** 划付时间 */
	private Date payTime;
	/** 银行账号 */
	private String payAccount;
	
	public PaymentMenthodEnum getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(PaymentMenthodEnum feeType) {
		this.feeType = feeType;
	}
	
	public Money getPayAmount() {
		return this.payAmount;
	}
	
	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}
	
	public Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getPayAccount() {
		return this.payAccount;
	}
	
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	
}
