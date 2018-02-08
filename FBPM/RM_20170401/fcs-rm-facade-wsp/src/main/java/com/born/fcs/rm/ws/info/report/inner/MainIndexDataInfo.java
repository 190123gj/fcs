package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 主要业务指标情况一览表
 * @author heh
 */
public class MainIndexDataInfo extends BaseToStringInfo {

    private static final long serialVersionUID = -3016943956812745510L;

    private String dept;

    private String deptCode;

    /** 发生额-本年累计 */
    private Money occurYear = Money.zero();
    /** 发生额-本月 */
    private Money occurMonth = Money.zero();
    /** 解保额-本年累计 */
    private Money releaseYear = Money.zero();
    /** 解保额-本月 */
    private Money releaseMonth = Money.zero();
    /** 净增额-本年累计 */
    private Money increaseYear = Money.zero();
    /** 在保余额-年初余额 */
    private Money balanceInitial = Money.zero();
    /** 在保余额-期末余额*/
    private Money balanceEnding = Money.zero();
    /** 保费收入-本年累计 */
    private Money premiumYear = Money.zero();
    /** 保费收入-本月*/
    private Money premiumMonth = Money.zero();
    /** 委贷业务-贷款余额(委贷业务在保余额)*/
    private Money entrustedAmount = Money.zero();
    /** 委贷业务本年收入*/
    private Money incomeYear = Money.zero();

    public boolean isEmpty() {
        return occurMonth.getCent() == 0 && occurYear.getCent() == 0
                && releaseMonth.getCent() == 0
                && balanceInitial.getCent() == 0
                && increaseYear.getCent() == 0 && balanceEnding.getCent() == 0
                && incomeYear.getCent() == 0 && incomeYear.getCent() == 0
                && premiumMonth.getCent() == 0 && premiumYear.getCent() == 0
                && entrustedAmount.getCent() == 0;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Money getOccurYear() {
        return occurYear;
    }

    public void setOccurYear(Money occurYear) {
        this.occurYear = occurYear;
    }

    public Money getOccurMonth() {
        return occurMonth;
    }

    public void setOccurMonth(Money occurMonth) {
        this.occurMonth = occurMonth;
    }

    public Money getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Money releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Money getReleaseMonth() {
        return releaseMonth;
    }

    public void setReleaseMonth(Money releaseMonth) {
        this.releaseMonth = releaseMonth;
    }

    public Money getIncreaseYear() {
        return increaseYear;
    }

    public void setIncreaseYear(Money increaseYear) {
        this.increaseYear = increaseYear;
    }

    public Money getBalanceEnding() {
        return balanceEnding;
    }

    public void setBalanceEnding(Money balanceEnding) {
        this.balanceEnding = balanceEnding;
    }

    public Money getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(Money incomeYear) {
        this.incomeYear = incomeYear;
    }

    public Money getEntrustedAmount() {
        return entrustedAmount;
    }

    public void setEntrustedAmount(Money entrustedAmount) {
        this.entrustedAmount = entrustedAmount;
    }

    public Money getPremiumYear() {
        return premiumYear;
    }

    public void setPremiumYear(Money premiumYear) {
        this.premiumYear = premiumYear;
    }

    public Money getPremiumMonth() {
        return premiumMonth;
    }

    public void setPremiumMonth(Money premiumMonth) {
        this.premiumMonth = premiumMonth;
    }

    public Money getBalanceInitial() {
        return balanceInitial;
    }

    public void setBalanceInitial(Money balanceInitial) {
        this.balanceInitial = balanceInitial;
    }
}
