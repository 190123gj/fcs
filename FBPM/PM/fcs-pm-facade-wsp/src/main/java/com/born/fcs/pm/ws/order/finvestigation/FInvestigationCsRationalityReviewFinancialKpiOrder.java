package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 保证人主要财务指标
 * 
 * @author lirz
 *
 * 2016-3-10 下午6:19:32
 */
public class FInvestigationCsRationalityReviewFinancialKpiOrder extends ValidateOrderBase{

	private static final long serialVersionUID = -173900728847246453L;

	private long id;
	private long csrReviewId;
	private String kpiCode; //指标标识
	private String kpiName; //指标名
	private String kpiValue; //指标值（存金额或者百分比）
	private String kpiUnit; //指标单位
	private String termType; //分类（账期）
	private String remark; //说明
	private int sortOrder;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getCsrReviewId() {
		return csrReviewId;
	}
	
	public void setCsrReviewId(long csrReviewId) {
		this.csrReviewId = csrReviewId;
	}

	public String getKpiCode() {
		return kpiCode;
	}
	
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getKpiName() {
		return kpiName;
	}
	
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiValue() {
		return kpiValue;
	}
	
	public void setKpiValue(String kpiValue) {
		this.kpiValue = kpiValue;
	}

	public String getKpiUnit() {
		return kpiUnit;
	}
	
	public void setKpiUnit(String kpiUnit) {
		this.kpiUnit = kpiUnit;
	}

	public String getTermType() {
		return termType;
	}
	
	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
