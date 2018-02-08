package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 企业收入核实情况工作底稿
 * 
 * @author lirz
 *
 * 2016-6-7 下午5:32:11
 */
public class FAfterwardsCheckReportIncomeOrder extends ValidateOrderBase{

	private static final long serialVersionUID = 6112990362181178788L;

	private long incomeId;
	private long formId;
	private String projectCode;
	private String incomeName;
	private int lastYear;
	private Money lastTotalAmount = new Money(0, 0);
	private int lastTotalDay;
	private Money lastAverageDay = new Money(0, 0);
	private Money lastAccumulation = new Money(0, 0);
	private int currentYear;
	private Money currentTotalAmount = new Money(0, 0);
	private int currentTotalDay;
	private Money currentAverageDay = new Money(0, 0);
	private Money currentAccumulation = new Money(0, 0);
	
	private String delAble;
	private int sortOrder;

    //========== getters and setters ==========

	public long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getIncomeName() {
		return incomeName;
	}
	
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	public int getLastYear() {
		return lastYear;
	}
	
	public void setLastYear(int lastYear) {
		this.lastYear = lastYear;
	}

	public Money getLastTotalAmount() {
		return lastTotalAmount;
	}
	
	public void setLastTotalAmount(Money lastTotalAmount) {
		if (lastTotalAmount == null) {
			this.lastTotalAmount = new Money(0, 0);
		} else {
			this.lastTotalAmount = lastTotalAmount;
		}
	}
	
	public void setLastTotalAmountStr(String lastTotalAmountStr) {
		if (!isNull(lastTotalAmountStr)) {
			this.lastTotalAmount = Money.amout(lastTotalAmountStr);
		}
	}

	public int getLastTotalDay() {
		return lastTotalDay;
	}
	
	public void setLastTotalDay(int lastTotalDay) {
		this.lastTotalDay = lastTotalDay;
	}

	public void setLastTotalDayStr(String lastTotalDayStr) {
		if (!isNull(lastTotalDayStr)) {
			this.lastTotalDay = NumberUtils.toInt(lastTotalDayStr);
		}
	}
	
	public Money getLastAverageDay() {
		return lastAverageDay;
	}
	
	public void setLastAverageDay(Money lastAverageDay) {
		if (lastAverageDay == null) {
			this.lastAverageDay = new Money(0, 0);
		} else {
			this.lastAverageDay = lastAverageDay;
		}
	}
	
	public void setLastAverageDayStr(String lastAverageDayStr) {
		if (!isNull(lastAverageDayStr)) {
			this.lastAverageDay = Money.amout(lastAverageDayStr);
		}
	}

	public Money getLastAccumulation() {
		return lastAccumulation;
	}
	
	public void setLastAccumulation(Money lastAccumulation) {
		if (lastAccumulation == null) {
			this.lastAccumulation = new Money(0, 0);
		} else {
			this.lastAccumulation = lastAccumulation;
		}
	}
	
	public void setLastAccumulationStr(String lastAccumulationStr) {
		if (!isNull(lastAccumulationStr)) {
			this.lastAccumulation = Money.amout(lastAccumulationStr);
		}
	}

	public int getCurrentYear() {
		return currentYear;
	}
	
	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public Money getCurrentTotalAmount() {
		return currentTotalAmount;
	}
	
	public void setCurrentTotalAmount(Money currentTotalAmount) {
		if (currentTotalAmount == null) {
			this.currentTotalAmount = new Money(0, 0);
		} else {
			this.currentTotalAmount = currentTotalAmount;
		}
	}
	
	public void setCurrentTotalAmountStr(String currentTotalAmountStr) {
		if (!isNull(currentTotalAmountStr)) {
			this.currentTotalAmount = Money.amout(currentTotalAmountStr);
		}
	}

	public int getCurrentTotalDay() {
		return currentTotalDay;
	}
	
	public void setCurrentTotalDay(int currentTotalDay) {
		this.currentTotalDay = currentTotalDay;
	}
	
	public void setCurrentTotalDayStr(String currentTotalDayStr) {
		if (!isNull(currentTotalDayStr)) {
			this.currentTotalDay = NumberUtils.toInt(currentTotalDayStr);
		}
	}

	public Money getCurrentAverageDay() {
		return currentAverageDay;
	}
	
	public void setCurrentAverageDay(Money currentAverageDay) {
		if (currentAverageDay == null) {
			this.currentAverageDay = new Money(0, 0);
		} else {
			this.currentAverageDay = currentAverageDay;
		}
	}
	
	public void setCurrentAverageDayStr(String currentAverageDayStr) {
		if (!isNull(currentAverageDayStr)) {
			this.currentAverageDay = Money.amout(currentAverageDayStr);
		}
	}

	public Money getCurrentAccumulation() {
		return currentAccumulation;
	}
	
	public void setCurrentAccumulation(Money currentAccumulation) {
		if (currentAccumulation == null) {
			this.currentAccumulation = new Money(0, 0);
		} else {
			this.currentAccumulation = currentAccumulation;
		}
	}
	
	public void setCurrentAccumulationStr(String currentAccumulationStr) {
		if (!isNull(currentAccumulationStr)) {
			this.currentAccumulation = Money.amout(currentAccumulationStr);
		}
	}

	public String getDelAble() {
		return delAble;
	}

	public void setDelAble(String delAble) {
		this.delAble = delAble;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
