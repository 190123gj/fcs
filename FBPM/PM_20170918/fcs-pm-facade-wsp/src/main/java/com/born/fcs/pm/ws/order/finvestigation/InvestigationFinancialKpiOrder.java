package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 财务指标数据(一行)
 * 
 * @author lirz
 * 
 * 2016-3-29 上午10:05:09
 */
public class InvestigationFinancialKpiOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 3269501808238404826L;
	
	private String itemName; //科目名称
	private String itemCode; //科目代码
	private String value; //最近一期值
	private String value1; //前一年
	private String value2; //前两年
	private String value3; //前三年
	private String average; //平均值
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public Money getValueAmount() {
		if (isNull(this.value)) {
			return new Money(0L);
		}
		return Money.amout(this.value);
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Money getValue1Amount() {
		if (isNull(this.value1)) {
			return new Money(0L);
		}
		return Money.amout(this.value1);
	}
	
	public String getValue1() {
		return value1;
	}
	
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public Money getValue2Amount() {
		if (isNull(this.value2)) {
			return new Money(0L);
		}
		return Money.amout(this.value2);
	}
	
	public String getValue2() {
		return value2;
	}
	
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	public Money getValue3Amount() {
		if (isNull(this.value3)) {
			return new Money(0L);
		}
		return Money.amout(this.value3);
	}
	
	public String getValue3() {
		return value3;
	}
	
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	
	public Money getAverageAmount() {
		if (isNull(this.average)) {
			return new Money(0L);
		}
		return Money.amout(this.average);
	}
	
	public String getAverage() {
		return average;
	}
	
	public void setAverage(String average) {
		this.average = average;
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
