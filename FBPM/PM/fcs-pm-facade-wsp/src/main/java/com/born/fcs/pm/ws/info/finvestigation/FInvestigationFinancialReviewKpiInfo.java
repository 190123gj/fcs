package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 客户财务评价 - 财务报表指标
 * 
 * @author lirz
 *
 * 2016-3-11 上午10:23:23
 */
public class FInvestigationFinancialReviewKpiInfo extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5554003991164483705L;
	
	private long id;
	private long FReviewId;
	private String kpiCode; //指标标识
	private KpiTypeEnum kpiType; //指标分类（财务数据/偿债能力/运营能力/盈利能力/现金流）
	private String kpiName; //指标名
	private int kpiLevel; //指标层级
	private String kpiValue; //指标值 审计单位
	private String kpiUnit; //指标单位 是否审计(Y/N)
	private double kpiRatio; //占比(指标解释)
	private long parentId; //父指标ID
	private String termType; //分类（账期） 报表时间
	private String remark; //说明 审计意见、数据解释
	private String kpiExplainJosn; //数据解释详细 [{explainName:'名称',explainValue:'金额',explainRate:'占比'}]
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFReviewId() {
		return FReviewId;
	}
	
	public void setFReviewId(long FReviewId) {
		this.FReviewId = FReviewId;
	}
	
	public String getKpiCode() {
		return kpiCode;
	}
	
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}
	
	public KpiTypeEnum getKpiType() {
		return kpiType;
	}
	
	public void setKpiType(KpiTypeEnum kpiType) {
		this.kpiType = kpiType;
	}
	
	public String getKpiName() {
		return kpiName;
	}
	
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	
	public int getKpiLevel() {
		return kpiLevel;
	}
	
	public void setKpiLevel(int kpiLevel) {
		this.kpiLevel = kpiLevel;
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
	
	public double getKpiRatio() {
		return kpiRatio;
	}
	
	public void setKpiRatio(double kpiRatio) {
		this.kpiRatio = kpiRatio;
	}
	
	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
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
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public String getKpiExplainJosn() {
		return this.kpiExplainJosn;
	}
	
	public void setKpiExplainJosn(String kpiExplainJosn) {
		this.kpiExplainJosn = kpiExplainJosn;
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
