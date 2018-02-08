package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 客户财务评价 - 财务报表指标 (一行数据)
 * 
 * @author lirz
 * 
 * 2016-3-29 下午4:29:09
 */
public class FinancialReviewKpiOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 1046277365484495496L;
	
	private String kpiCode; //指标标识
	private String kpiName; //指标名
	private Integer kpiLevel; //指标层级
	private String kpiValue; //指标值(最近一期)
	private String kpiValue1; //指标值(前一年)
	private String kpiValue2; //指标值(前二年)
	private String kpiValue3; //指标值(前三年)
	private String kpiUnit; //指标单位
	private Double kpiRatio; //占比(指标解释)
	private Long parentId; //父指标ID
	private String termType; //分类（账期）
	private String remark; //说明
	
	public boolean isNull() {
		return isNull(kpiCode)
				&& isNull(kpiName)
				&& isNull(kpiValue)
				&& isNull(kpiValue1)
				&& isNull(kpiValue2)
				&& isNull(kpiValue3)
				&& isNull(termType)
				;
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
	
	public Integer getKpiLevel() {
		return (null == kpiLevel) ? 0 : kpiLevel.intValue();
	}
	
	public void setKpiLevel(Integer kpiLevel) {
		this.kpiLevel = kpiLevel;
	}
	
	public String getKpiValue() {
		return kpiValue;
	}
	
	public void setKpiValue(String kpiValue) {
		this.kpiValue = kpiValue;
	}
	
	public String getKpiValue1() {
		return kpiValue1;
	}
	
	public void setKpiValue1(String kpiValue1) {
		this.kpiValue1 = kpiValue1;
	}
	
	public String getKpiValue2() {
		return kpiValue2;
	}
	
	public void setKpiValue2(String kpiValue2) {
		this.kpiValue2 = kpiValue2;
	}
	
	public String getKpiValue3() {
		return kpiValue3;
	}
	
	public void setKpiValue3(String kpiValue3) {
		this.kpiValue3 = kpiValue3;
	}
	
	public String getKpiUnit() {
		return kpiUnit;
	}
	
	public void setKpiUnit(String kpiUnit) {
		this.kpiUnit = kpiUnit;
	}
	
	public Double getKpiRatio() {
		return (null == kpiRatio) ? 0d : kpiRatio.doubleValue();
	}
	
	public void setKpiRatio(Double kpiRatio) {
		this.kpiRatio = kpiRatio;
	}
	
	public Long getParentId() {
		return (null == parentId) ? 0L : parentId.longValue();
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
