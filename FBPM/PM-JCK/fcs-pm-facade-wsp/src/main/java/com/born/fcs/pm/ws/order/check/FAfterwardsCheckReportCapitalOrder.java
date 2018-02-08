package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 保后检查报告 在建项目-前十大客户-按期收回情况等
 * 
 * @author lirz
 *
 * 2016-6-8 下午4:23:48
 */
public class FAfterwardsCheckReportCapitalOrder extends ValidateOrderBase{

	private static final long serialVersionUID = -3464714961975856514L;
	
	private long capitalId;
	private long formId;

	private String capitalType;
	private String capitalItem;
	private String capitalValue1;
	private String capitalValue2;
	private String capitalValue3;
	private String capitalValue4;
	private String capitalValue5;
	private String capitalValue6;
	private String capitalValue7;
	private String capitalValue8;
	private String capitalValue9;
	private String capitalValue10;
	
	private String delAble;

	private int sortOrder;
	
	public boolean isNull() {
		return isNull(capitalItem) 
				&& isNull(capitalValue1)
				&& isNull(capitalValue2)
				&& isNull(capitalValue3)
				&& isNull(capitalValue4)
				&& isNull(capitalValue5)
				&& isNull(capitalValue6)
				&& isNull(capitalValue7)
				&& isNull(capitalValue8)
				&& isNull(capitalValue9)
				&& isNull(capitalValue10)
				&& isNull(delAble)
				;
	}

    //========== getters and setters ==========

	public long getCapitalId() {
		return capitalId;
	}
	
	public void setCapitalId(long capitalId) {
		this.capitalId = capitalId;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getCapitalType() {
		return capitalType;
	}
	
	public void setCapitalType(String capitalType) {
		this.capitalType = capitalType;
	}

	public String getCapitalItem() {
		return capitalItem;
	}
	
	public void setCapitalItem(String capitalItem) {
		this.capitalItem = capitalItem;
	}

	public String getCapitalValue1() {
		return capitalValue1;
	}
	
	public void setCapitalValue1(String capitalValue1) {
		this.capitalValue1 = capitalValue1;
	}

	public String getCapitalValue2() {
		return capitalValue2;
	}
	
	public void setCapitalValue2(String capitalValue2) {
		this.capitalValue2 = capitalValue2;
	}

	public String getCapitalValue3() {
		return capitalValue3;
	}
	
	public void setCapitalValue3(String capitalValue3) {
		this.capitalValue3 = capitalValue3;
	}

	public String getCapitalValue4() {
		return capitalValue4;
	}
	
	public void setCapitalValue4(String capitalValue4) {
		this.capitalValue4 = capitalValue4;
	}

	public String getCapitalValue5() {
		return capitalValue5;
	}
	
	public void setCapitalValue5(String capitalValue5) {
		this.capitalValue5 = capitalValue5;
	}

	public String getCapitalValue6() {
		return capitalValue6;
	}
	
	public void setCapitalValue6(String capitalValue6) {
		this.capitalValue6 = capitalValue6;
	}

	public String getCapitalValue7() {
		return capitalValue7;
	}
	
	public void setCapitalValue7(String capitalValue7) {
		this.capitalValue7 = capitalValue7;
	}

	public String getCapitalValue8() {
		return capitalValue8;
	}
	
	public void setCapitalValue8(String capitalValue8) {
		this.capitalValue8 = capitalValue8;
	}

	public String getCapitalValue9() {
		return capitalValue9;
	}
	
	public void setCapitalValue9(String capitalValue9) {
		this.capitalValue9 = capitalValue9;
	}

	public String getCapitalValue10() {
		return capitalValue10;
	}
	
	public void setCapitalValue10(String capitalValue10) {
		this.capitalValue10 = capitalValue10;
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
