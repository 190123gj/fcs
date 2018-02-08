package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 担保项目分类汇总表
 * @author wuzj
 */
public class GuaranteeClassifyInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 336065869871242045L;
	
	/** 分类 */
	private String classify;
	/**是否标题*/
	private boolean title;
	/** 在保户数 */
	private String zbhs = "0";
	/** 在保笔数 */
	private String zbbs = "0";
	/** 上年余额（在保） */
	private Money balanceLastYear = Money.zero();
	/** 本年新增 */
	private Money increase = Money.zero();
	
	/** 新增占比 */
	public double getIncreaseRate() {
		if (balanceLastYear.getCent() == 0) {
			return 0;
		} else {
			return (double) increase.getCent() / (double) balanceLastYear.getCent();
		}
	}
	
	/** 本年还款 */
	private Money repayThisYear = Money.zero();
	/** 在保余额-期末余额 */
	private Money balanceEnding = Money.zero();
	
	/** 余额占比 */
	public double getBalanceRate() {
		if (balanceLastYear.getCent() == 0) {
			return 0;
		} else {
			return (double) balanceEnding.getCent() / (double) balanceLastYear.getCent();
		}
	}
	

	
	public String getClassify() {
		return classify;
	}



	public void setClassify(String classify) {
		this.classify = classify;
	}



	public String getZbhs() {
		return zbhs;
	}
	
	public void setZbhs(String zbhs) {
		this.zbhs = zbhs;
	}
	
	public String getZbbs() {
		return zbbs;
	}
	
	public void setZbbs(String zbbs) {
		this.zbbs = zbbs;
	}
	
	public Money getBalanceLastYear() {
		return balanceLastYear;
	}
	
	public void setBalanceLastYear(Money balanceLastYear) {
		this.balanceLastYear = balanceLastYear;
	}
	
	public Money getIncrease() {
		return increase;
	}
	
	public void setIncrease(Money increase) {
		this.increase = increase;
	}
	
	public Money getRepayThisYear() {
		return repayThisYear;
	}
	
	public void setRepayThisYear(Money repayThisYear) {
		this.repayThisYear = repayThisYear;
	}
	
	public Money getBalanceEnding() {
		return balanceEnding;
	}
	
	public void setBalanceEnding(Money balanceEnding) {
		this.balanceEnding = balanceEnding;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}
}
