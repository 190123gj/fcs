package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 委贷业务主要指标汇总表
 * @author heh
 */
public class EntrustedeMainIndexInfo extends BaseToStringInfo {


    private static final long serialVersionUID = 7264046306604847980L;
    private String dept;

    private String deptCode;

    /** 期初余额-上月 */
    private Money balanceInitial = Money.zero();
    /** 期初余额-年初 */
    private Money balanceInitialYear = Money.zero();
    /** 发生额-本月 */
    private Money occurMonth = Money.zero();
    /** 发生额-本年 */
    private Money occurYear = Money.zero();
    /** 还款额-本年累计 */
    private Money paymentsYear = Money.zero();
    /** 还款额-本月 */
    private Money paymentsMonth = Money.zero();
    /** 收入-年度目标 */
    private Money incomeGoal = Money.zero();
    /** 收入-本年累计 */
    private Money incomeYear = Money.zero();
    /** 收入-本月 */
    private Money incomeMonth = Money.zero();


    public boolean isEmpty() {
        return occurMonth.getCent() == 0 && paymentsMonth.getCent() == 0
                && balanceInitialYear.getCent() == 0
                && balanceInitial.getCent() == 0
                && balanceInitialYear.getCent() == 0 && paymentsYear.getCent() == 0
                && incomeGoal.getCent() == 0 && incomeYear.getCent() == 0
                && incomeMonth.getCent() == 0;
    }

    public Money getOccurYear() {
        return occurYear;
    }

    public void setOccurYear(Money occurYear) {
        this.occurYear = occurYear;
    }

    public Money getBalanceInitialYear() {
        return balanceInitialYear;
    }

    public void setBalanceInitialYear(Money balanceInitialYear) {
        this.balanceInitialYear = balanceInitialYear;
    }

    public Money getPaymentsYear() {
        return paymentsYear;
    }

    public void setPaymentsYear(Money paymentsYear) {
        this.paymentsYear = paymentsYear;
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

    public Money getBalanceInitial() {
        return balanceInitial;
    }

    public void setBalanceInitial(Money balanceInitial) {
        this.balanceInitial = balanceInitial;
    }

    public Money getOccurMonth() {
        return occurMonth;
    }

    public void setOccurMonth(Money occurMonth) {
        this.occurMonth = occurMonth;
    }

    public Money getPaymentsMonth() {
        return paymentsMonth;
    }

    public void setPaymentsMonth(Money paymentsMonth) {
        this.paymentsMonth = paymentsMonth;
    }

    public Money getEndingAmount() {

            return balanceInitialYear.add(occurYear).subtract(paymentsYear);
    }


    public Money getIncomeGoal() {
        return incomeGoal;
    }

    public void setIncomeGoal(Money incomeGoal) {
        this.incomeGoal = incomeGoal;
    }

    public Money getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(Money incomeYear) {
        this.incomeYear = incomeYear;
    }

    public Money getIncomeMonth() {
        return incomeMonth;
    }

    public void setIncomeMonth(Money incomeMonth) {
        this.incomeMonth = incomeMonth;
    }
}
