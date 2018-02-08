package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请 - 回执
 *
 * @author wuzj
 */
public class FLoanUseApplyReceiptOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -7030036068182827969L;
	
	private Long id;
	
	private Long applyId;
	
	/** 实际存储的金金额 */
	private Money actualAmount;
	
	/** 实际存储的保证金 */
	private Money actualDepositAmount;
	
	/** 流动资金贷款 */
	private Money liquidityLoanAmount = Money.zero();
	/** 固定资产融资 */
	private Money fixedAssetsFinancingAmount = Money.zero();
	/** 承兑汇票担保 */
	private Money acceptanceBillAmount = Money.zero();
	/** 信用证担保 */
	private Money creditLetterAmount = Money.zero();
	
	private String busiSubType;
	
	private String busiSubTypeName;
	
	/** 实际放款时间 */
	private Date actualLoanTime;
	/** 放款凭证 */
	private String voucherUrl;
	/** 其他资料 */
	private String otherName;
	private String otherUrl;
	
	private String remark;
	
	@Override
	public void check() {
		validateNotNull(applyId, "申请单ID");
		validateHasZore(applyId, "申请单ID");
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public Date getActualLoanTime() {
		return this.actualLoanTime;
	}
	
	public void setActualLoanTime(Date actualLoanTime) {
		this.actualLoanTime = actualLoanTime;
	}
	
	public Money getLiquidityLoanAmount() {
		return this.liquidityLoanAmount;
	}
	
	public void setLiquidityLoanAmount(Money liquidityLoanAmount) {
		this.liquidityLoanAmount = liquidityLoanAmount;
	}
	
	public Money getFixedAssetsFinancingAmount() {
		return this.fixedAssetsFinancingAmount;
	}
	
	public void setFixedAssetsFinancingAmount(Money fixedAssetsFinancingAmount) {
		this.fixedAssetsFinancingAmount = fixedAssetsFinancingAmount;
	}
	
	public Money getAcceptanceBillAmount() {
		return this.acceptanceBillAmount;
	}
	
	public void setAcceptanceBillAmount(Money acceptanceBillAmount) {
		this.acceptanceBillAmount = acceptanceBillAmount;
	}
	
	public Money getCreditLetterAmount() {
		return this.creditLetterAmount;
	}
	
	public void setCreditLetterAmount(Money creditLetterAmount) {
		this.creditLetterAmount = creditLetterAmount;
	}
	
	public String getVoucherUrl() {
		return this.voucherUrl;
	}
	
	public void setVoucherUrl(String voucherUrl) {
		this.voucherUrl = voucherUrl;
	}
	
	public String getOtherName() {
		return this.otherName;
	}
	
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	
	public String getOtherUrl() {
		return this.otherUrl;
	}
	
	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Money getActualDepositAmount() {
		return this.actualDepositAmount;
	}
	
	public void setActualDepositAmount(Money actualDepositAmount) {
		this.actualDepositAmount = actualDepositAmount;
	}
	
	public Money getActualAmount() {
		return this.actualAmount;
	}
	
	public void setActualAmount(Money actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public String getBusiSubType() {
		return this.busiSubType;
	}
	
	public void setBusiSubType(String busiSubType) {
		this.busiSubType = busiSubType;
	}
	
	public String getBusiSubTypeName() {
		return this.busiSubTypeName;
	}
	
	public void setBusiSubTypeName(String busiSubTypeName) {
		this.busiSubTypeName = busiSubTypeName;
	}
	
}
