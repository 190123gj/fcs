package com.born.fcs.pm.ws.order.fund;

import java.text.ParseException;
import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请 - 回执 - 多金额
 *
 * @author wuzj
 */
public class FLoanUseApplyReceiptOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -7030036068182827969L;
	
	/** 实际存储的金金额 */
	private Money actualAmount = Money.zero();
	/** 流动资金贷款 */
	private Money liquidityLoanAmount = Money.zero();
	/** 固定资产融资 */
	private Money fixedAssetsFinancingAmount = Money.zero();
	/** 承兑汇票担保 */
	private Money acceptanceBillAmount = Money.zero();
	/** 信用证担保 */
	private Money creditLetterAmount = Money.zero();
	/** 实际放款时间 */
	private Date actualLoanTime;
	/** 子业务类型 */
	private String busiSubType;
	private String busiSubTypeName;
	
	/** 资金渠道 */
	private Long capitalChannelId;
	private String capitalChannelCode;
	private String capitalChannelType;
	private String capitalChannelName;
	/** 二级资金渠道 */
	private Long capitalSubChannelId;
	private String capitalSubChannelCode;
	private String capitalSubChannelType;
	private String capitalSubChannelName;
	
	/** 放款凭证 */
	private String voucherUrl;
	/** 其他资料 */
	private String otherUrl;
	/** 备注 */
	private String remark;
	
	public void setActualAmountStr(String actualAmountStr) {
		if (StringUtil.isNotBlank(actualAmountStr)) {
			this.actualAmount = Money.amout(actualAmountStr);
		}
	}
	
	public void setLiquidityLoanAmountStr(String liquidityLoanAmountStr) {
		if (StringUtil.isNotBlank(liquidityLoanAmountStr)) {
			this.liquidityLoanAmount = Money.amout(liquidityLoanAmountStr);
		}
	}
	
	public void setfixedAssetsFinancingAmountStr(String fixedAssetsFinancingAmountStr) {
		if (StringUtil.isNotBlank(fixedAssetsFinancingAmountStr)) {
			this.fixedAssetsFinancingAmount = Money.amout(fixedAssetsFinancingAmountStr);
		}
	}
	
	public void setAcceptanceBillAmountStr(String acceptanceBillAmountStr) {
		if (StringUtil.isNotBlank(acceptanceBillAmountStr)) {
			this.acceptanceBillAmount = Money.amout(acceptanceBillAmountStr);
		}
	}
	
	public void setCreditLetterAmountStr(String creditLetterAmountStr) {
		if (StringUtil.isNotBlank(creditLetterAmountStr)) {
			this.creditLetterAmount = Money.amout(creditLetterAmountStr);
		}
	}
	
	public void setActualLoanTimeStr(String actualLoanTimeStr) throws ParseException {
		if (StringUtil.isNotBlank(actualLoanTimeStr)) {
			this.actualLoanTime = DateUtil.getFormat("yyyy-MM-dd").parse(actualLoanTimeStr);
		}
	}
	
	public Money getActualAmount() {
		return this.actualAmount;
	}
	
	public void setActualAmount(Money actualAmount) {
		this.actualAmount = actualAmount;
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
	
	public Date getActualLoanTime() {
		return this.actualLoanTime;
	}
	
	public void setActualLoanTime(Date actualLoanTime) {
		this.actualLoanTime = actualLoanTime;
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
	
	public Long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(Long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelCode() {
		return this.capitalChannelCode;
	}
	
	public void setCapitalChannelCode(String capitalChannelCode) {
		this.capitalChannelCode = capitalChannelCode;
	}
	
	public String getCapitalChannelType() {
		return this.capitalChannelType;
	}
	
	public void setCapitalChannelType(String capitalChannelType) {
		this.capitalChannelType = capitalChannelType;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public Long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(Long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelCode() {
		return this.capitalSubChannelCode;
	}
	
	public void setCapitalSubChannelCode(String capitalSubChannelCode) {
		this.capitalSubChannelCode = capitalSubChannelCode;
	}
	
	public String getCapitalSubChannelType() {
		return this.capitalSubChannelType;
	}
	
	public void setCapitalSubChannelType(String capitalSubChannelType) {
		this.capitalSubChannelType = capitalSubChannelType;
	}
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public String getVoucherUrl() {
		return this.voucherUrl;
	}
	
	public void setVoucherUrl(String voucherUrl) {
		this.voucherUrl = voucherUrl;
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
}
