package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 合同回传渠道
 */
public class ProjectContractChannelOrder extends ValidateOrderBase {
    private static final long serialVersionUID = 1514641034494809165L;
    /** 渠道ID */
    private Long channelId;
    /** 合同金额 */
    private Money contractAmount = new Money(0, 0);
    /** 流动资金贷款(合同金额) */
    private Money liquidityLoansAmount = new Money(0, 0);
    /** 固定资产融资(合同金额) */
    private Money financialAmount = new Money(0, 0);
    /** 承兑汇票担保(合同金额) */
    private Money acceptanceBillAmount = new Money(0, 0);
    /** 信用证担保(合同金额) */
    private Money creditAmount = new Money(0, 0);

    public Money getLiquidityLoansAmount() {
        return liquidityLoansAmount;
    }

    public void setLiquidityLoansAmount(Money liquidityLoansAmount) {
        this.liquidityLoansAmount = liquidityLoansAmount;
    }

    public Money getFinancialAmount() {
        return financialAmount;
    }

    public void setFinancialAmount(Money financialAmount) {
        this.financialAmount = financialAmount;
    }

    public Money getAcceptanceBillAmount() {
        return acceptanceBillAmount;
    }

    public void setAcceptanceBillAmount(Money acceptanceBillAmount) {
        this.acceptanceBillAmount = acceptanceBillAmount;
    }

    public Money getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Money creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Money getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Money contractAmount) {
        this.contractAmount = contractAmount;
    }
}
