package com.born.fcs.pm.ws.order.fund;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请 - 回执
 *
 * @author wuzj
 */
public class FLoanUseApplyReceiptBatchOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 3421317011074582771L;
	
	/** 申请ID */
	private long applyId;
	
	/** 多回执情况 */
	List<FLoanUseApplyReceiptOrder> receiptOrder;
	
	/** 单条信息 2016-11-17 兼容之前的单回执情况以下字段保留 */
	/** 放款凭证 */
	private String voucherUrl;
	/** 其他资料 */
	private String otherUrl;
	/** 备注 */
	private String remark;
	
	/** 实际存储的金金额 */
	private Money actualAmount = Money.zero();
	/** 实际存储的保证金 */
	private Money actualDepositAmount = Money.zero();
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
	
	public void setActualAmountStr(String actualAmountStr) {
		try {
			actualAmount = Money.amout(actualAmountStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setActualDepositAmountStr(String actualDepositAmountStr) {
		try {
			actualDepositAmount = Money.amout(actualDepositAmountStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLiquidityLoanAmountStr(String liquidityLoanAmountStr) {
		try {
			liquidityLoanAmount = Money.amout(liquidityLoanAmountStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setFixedAssetsFinancingAmountStr(String fixedAssetsFinancingAmountStr) {
		try {
			fixedAssetsFinancingAmount = Money.amout(fixedAssetsFinancingAmountStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setAcceptanceBillAmountStr(String acceptanceBillAmountStr) {
		try {
			acceptanceBillAmount = Money.amout(acceptanceBillAmountStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setCreditLetterAmountStr(String creditLetterAmountStr) {
		try {
			creditLetterAmount = Money.amout(creditLetterAmountStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setActualLoanTimeStr(String actualLoanTimeStr) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			actualLoanTime = df.parse(actualLoanTimeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void check() {
		validateHasZore(applyId, "申请单ID");
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
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
	
	public List<FLoanUseApplyReceiptOrder> getReceiptOrder() {
		return this.receiptOrder;
	}
	
	public void setReceiptOrder(List<FLoanUseApplyReceiptOrder> receiptOrder) {
		this.receiptOrder = receiptOrder;
	}
	
	public Money getActualAmount() {
		return this.actualAmount;
	}
	
	public void setActualAmount(Money actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public Money getActualDepositAmount() {
		return this.actualDepositAmount;
	}
	
	public void setActualDepositAmount(Money actualDepositAmount) {
		this.actualDepositAmount = actualDepositAmount;
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
}
