package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目计提台帐
 *
 *
 * @author wuzj
 *
 */
public class FinancialProjectWithdrawingInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -7761996376389210131L;
	
	/** 主键 */
	private long id;
	/** 项目编号 */
	private String projectCode;
	/** 转让交易ID */
	private long transferTradeId;
	/** 产品ID */
	private long productId;
	/** 产品名称 */
	private String productName;
	/** 计提类型 RECEIPT/PAYMENT 应收/应付 */
	private String withdrawType;
	/** 预期收益率/融资利率 */
	private double interestRate;
	/** 购买日期/转让日期 */
	private Date buyDate;
	/** 到期日/提前完成日/转让回购日 */
	private Date expireDate;
	/** 转让本金 */
	private Money principal = new Money(0, 0);
	/** 转让对象 */
	private String transferTo;
	/** 当月实时计提利息 */
	private Money withdrawingInterest = new Money(0, 0);
	/** 当月结息金额 */
	private Money withdrawedInterest = new Money(0, 0);
	/** 累计结息金额 */
	private Money totalInterest = new Money(0, 0);
	/** 计提时间 */
	private Date withdrawDate;
	/** 结息时间 */
	private Date withdrawedDate;
	/** 计提月 */
	private String withdrawMonth;
	/** 计提天 */
	private String withdrawDay;
	/** 是否完成 YES/NO */
	private BooleanEnum withdrawFinish;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 项目信息 */
	private ProjectFinancialInfo project;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getTransferTradeId() {
		return this.transferTradeId;
	}
	
	public void setTransferTradeId(long transferTradeId) {
		this.transferTradeId = transferTradeId;
	}
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getWithdrawType() {
		return this.withdrawType;
	}
	
	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public Date getBuyDate() {
		return this.buyDate;
	}
	
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	public Date getExpireDate() {
		return this.expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public Money getPrincipal() {
		return this.principal;
	}
	
	public void setPrincipal(Money principal) {
		this.principal = principal;
	}
	
	public String getTransferTo() {
		return this.transferTo;
	}
	
	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}
	
	public Money getWithdrawingInterest() {
		return this.withdrawingInterest;
	}
	
	public void setWithdrawingInterest(Money withdrawingInterest) {
		this.withdrawingInterest = withdrawingInterest;
	}
	
	public Money getWithdrawedInterest() {
		return this.withdrawedInterest;
	}
	
	public void setWithdrawedInterest(Money withdrawedInterest) {
		this.withdrawedInterest = withdrawedInterest;
	}
	
	public Money getTotalInterest() {
		return this.totalInterest;
	}
	
	public void setTotalInterest(Money totalInterest) {
		this.totalInterest = totalInterest;
	}
	
	public Date getWithdrawDate() {
		return this.withdrawDate;
	}
	
	public void setWithdrawDate(Date withdrawDate) {
		this.withdrawDate = withdrawDate;
	}
	
	public Date getWithdrawedDate() {
		return this.withdrawedDate;
	}
	
	public void setWithdrawedDate(Date withdrawedDate) {
		this.withdrawedDate = withdrawedDate;
	}
	
	public String getWithdrawMonth() {
		return this.withdrawMonth;
	}
	
	public void setWithdrawMonth(String withdrawMonth) {
		this.withdrawMonth = withdrawMonth;
	}
	
	public String getWithdrawDay() {
		return this.withdrawDay;
	}
	
	public void setWithdrawDay(String withdrawDay) {
		this.withdrawDay = withdrawDay;
	}
	
	public BooleanEnum getWithdrawFinish() {
		return this.withdrawFinish;
	}
	
	public void setWithdrawFinish(BooleanEnum withdrawFinish) {
		this.withdrawFinish = withdrawFinish;
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
	
	public ProjectFinancialInfo getProject() {
		return this.project;
	}
	
	public void setProject(ProjectFinancialInfo project) {
		this.project = project;
	}
}
