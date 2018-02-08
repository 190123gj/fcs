package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 担保业务主要指标汇总表
 * @author wuzj
 */
public class GuaranteeKpiDataInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -9160872452108261053L;
	
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
	/** 净增额-年度目标 */
	private Money increaseGoal = Money.zero();
	
	/** 净增额-完成进度 */
	public double getIncreaseFinishRate() {
		if (increaseGoal.getCent() == 0) {
			return 0;
		} else {
			return (double) increaseYear.getCent() / (double) increaseGoal.getCent();
		}
	}
	
	/** 在保余额-年度目标 */
	private Money balanceGoal = Money.zero();
	/** 在保余额-年初余额 */
	private Money balanceInitial = Money.zero();
	/** 在保余额-期末余额 */
	private Money balanceEnding = Money.zero();
	
	/** 收入-年度目标 */
	private Money incomeGoal = Money.zero();
	/** 收入-本年累计 */
	private Money incomeYear = Money.zero();
	/** 收入-本月 */
	private Money incomeMonth = Money.zero();
	
	/** 收入-完成进度 */
	public double getIncomeFinishRate() {
		if (incomeGoal.getCent() == 0) {
			return 0;
		} else {
			return (double) incomeYear.getCent() / (double) incomeGoal.getCent();
		}
	}
	
	public boolean isEmpty() {
		return occurYear.getCent() == 0 && occurMonth.getCent() == 0 && releaseYear.getCent() == 0
				&& releaseMonth.getCent() == 0 && increaseYear.getCent() == 0
				&& increaseGoal.getCent() == 0 && balanceGoal.getCent() == 0
				&& balanceInitial.getCent() == 0 && balanceEnding.getCent() == 0
				&& incomeGoal.getCent() == 0 && incomeYear.getCent() == 0
				&& incomeMonth.getCent() == 0;
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
	
	public Money getIncreaseGoal() {
		return increaseGoal;
	}
	
	public void setIncreaseGoal(Money increaseGoal) {
		this.increaseGoal = increaseGoal;
	}
	
	public Money getBalanceGoal() {
		return balanceGoal;
	}
	
	public void setBalanceGoal(Money balanceGoal) {
		this.balanceGoal = balanceGoal;
	}
	
	public Money getBalanceInitial() {
		return balanceInitial;
	}
	
	public void setBalanceInitial(Money balanceInitial) {
		this.balanceInitial = balanceInitial;
	}
	
	public Money getBalanceEnding() {
		return balanceEnding;
	}
	
	public void setBalanceEnding(Money balanceEnding) {
		this.balanceEnding = balanceEnding;
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
