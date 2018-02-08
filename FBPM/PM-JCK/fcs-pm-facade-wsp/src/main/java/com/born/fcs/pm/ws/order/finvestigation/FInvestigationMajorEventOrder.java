package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;

/**
 * 重大事项调查
 * 
 * @author lirz
 *
 * 2016-3-10 下午5:19:22
 */
public class FInvestigationMajorEventOrder extends ProjectFormOrderBase{

	private static final long serialVersionUID = 2934183398846799136L;

	private long id;
	//其他重点财务情况调查（民间借贷、异常科目等）
	private String financialCondition;
	//多元化投资调查
	private String investment;
	//其他重大事项调查（城市开发类项目对当地经济、财政、支持程度的分析填写本项内容）
	private String otherEvents;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}


	public String getFinancialCondition() {
		return financialCondition;
	}
	
	public void setFinancialCondition(String financialCondition) {
		this.financialCondition = financialCondition;
	}

	public String getInvestment() {
		return investment;
	}
	
	public void setInvestment(String investment) {
		this.investment = investment;
	}

	public String getOtherEvents() {
		return otherEvents;
	}
	
	public void setOtherEvents(String otherEvents) {
		this.otherEvents = otherEvents;
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
