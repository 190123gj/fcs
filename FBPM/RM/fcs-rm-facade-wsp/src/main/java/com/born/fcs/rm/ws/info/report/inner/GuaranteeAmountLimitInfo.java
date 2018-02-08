package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 担保业务金额/期限分类汇总表
 * @author heh
 */
public class GuaranteeAmountLimitInfo extends BaseToStringInfo {
    private static final long serialVersionUID = 996672034469097014L;
    /** 分类类别 */
    private String type;
    /** 子类别 */
    private String subType;
    /** 具体类别 */
    private String classify;
    /** 新增户数 */
    private long hs;
    /** 新增笔数 */
    private long bs;
    /** 在保户数 */
    private long zbhs;
    /** 在保笔数 */
    private long zbbs;
    /** 合同金额 */
    private Money contractAmount = Money.zero();
    /** 年初在保余额 */
    private Money balanceInit = Money.zero();
    /** 本年累计发生额 */
    private Money occurAmount = Money.zero();
    /** 本年累计解保额 */
    private Money releaseAmount = Money.zero();
    /** 期末在保余额 */
    private Money balanceEnding = Money.zero();

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public long getHs() {
        return hs;
    }

    public void setHs(long hs) {
        this.hs = hs;
    }

    public long getBs() {
        return bs;
    }

    public void setBs(long bs) {
        this.bs = bs;
    }

    public long getZbhs() {
        return zbhs;
    }

    public void setZbhs(long zbhs) {
        this.zbhs = zbhs;
    }

    public long getZbbs() {
        return zbbs;
    }

    public void setZbbs(long zbbs) {
        this.zbbs = zbbs;
    }

    public Money getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Money contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Money getBalanceInit() {
        return balanceInit;
    }

    public void setBalanceInit(Money balanceInit) {
        this.balanceInit = balanceInit;
    }

    public Money getOccurAmount() {
        return occurAmount;
    }

    public void setOccurAmount(Money occurAmount) {
        this.occurAmount = occurAmount;
    }

    public Money getReleaseAmount() {
        return releaseAmount;
    }

    public void setReleaseAmount(Money releaseAmount) {
        this.releaseAmount = releaseAmount;
    }

    public Money getBalanceEnding() {
        return balanceEnding;
    }

    public void setBalanceEnding(Money balanceEnding) {
        this.balanceEnding = balanceEnding;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
}
