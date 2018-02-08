package com.born.fcs.pm.ws.order.finvestigation;

import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 客户财务评价 - 财务报表指标
 * 
 * @author lirz
 *
 * 2016-3-11 上午10:23:23
 */
public class FInvestigationFinancialReviewKpiOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 114404055970762272L;
	
	private long id;
	private long FReviewId;
	private String kpiCode; //指标标识
	private KpiTypeEnum kpiType; //指标分类（财务数据/偿债能力/运营能力/盈利能力/现金流）
	private String kpiName; //指标名
	private Integer kpiLevel; //指标层级
	private String kpiValue; //指标值
	private String kpiUnit; //指标单位
	private Double kpiRatio; //占比(指标解释)
	private Long parentId; //父指标ID
	private String termType; //分类（账期）
	private String remark; //说明
	private int sortOrder;
	//数据解释
	private String explainName; //数据解释：名称
	private String explainValue; //数据解释：金额
	private String explainRate; //数据解释：占比
	
	//重大科目异常变动提醒
	private String itemName; //科目
	private String itemDate; //科目时间
	private String itemValue; //金额(元)
	//2016-9-9 wuzj 重大科目异常变动提醒 数据解释 动态新增
	/**
	 * kpiExplainJson = {explaination:"解释说明",kpiExplain:[{explainName:
	 * '名称',explainValue:'金额',explainRate:'占比'}]}
	 */
	private String kpiExplainJosn; //解释条目s
	private String explaination; //解释
	//审计信息
	private String auditValue; //审计单位
	private String auditRemark; //审计意见
	private String kpiUnit0; //指标单位
	private String kpiUnit1; //指标单位
	private String kpiUnit2; //指标单位
	private String kpiUnit3; //指标单位
	
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
	
	public Integer getKpiLevel() {
		return kpiLevel;
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
	
	public String getKpiUnit() {
		return kpiUnit;
	}
	
	public void setKpiUnit(String kpiUnit) {
		this.kpiUnit = kpiUnit;
	}
	
	public Double getKpiRatio() {
		return kpiRatio;
	}
	
	public void setKpiRatio(Double kpiRatio) {
		this.kpiRatio = kpiRatio;
	}
	
	public Long getParentId() {
		return parentId;
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
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getExplainName() {
		return explainName;
	}
	
	public void setExplainName(String explainName) {
		this.explainName = explainName;
		this.kpiName = explainName;
	}
	
	public String getExplainValue() {
		return explainValue;
	}
	
	public void setExplainValue(String explainValue) {
		this.explainValue = explainValue;
		this.kpiValue = explainValue;
	}
	
	public String getExplainRate() {
		return explainRate;
	}
	
	public void setExplainRate(String explainRate) {
		this.explainRate = explainRate;
		try {
			this.kpiRatio = Double.parseDouble(explainRate);
		} catch (Exception e) {
			
		}
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
		this.kpiName = itemName;
	}
	
	public String getItemDate() {
		return itemDate;
	}
	
	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
		this.termType = itemDate;
	}
	
	public String getItemValue() {
		return itemValue;
	}
	
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
		this.kpiValue = itemValue;
	}
	
	public String getAuditValue() {
		return auditValue;
	}
	
	public void setAuditValue(String auditValue) {
		this.auditValue = auditValue;
		this.kpiValue = auditValue;
	}
	
	public String getAuditRemark() {
		return auditRemark;
	}
	
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
		this.remark = auditRemark;
	}
	
	public String getKpiUnit0() {
		return kpiUnit0;
	}
	
	public void setKpiUnit0(String kpiUnit0) {
		this.kpiUnit0 = kpiUnit0;
		this.kpiUnit = kpiUnit0;
	}
	
	public String getKpiUnit1() {
		return kpiUnit1;
	}
	
	public void setKpiUnit1(String kpiUnit1) {
		this.kpiUnit1 = kpiUnit1;
		this.kpiUnit = kpiUnit1;
	}
	
	public String getKpiUnit2() {
		return kpiUnit2;
	}
	
	public void setKpiUnit2(String kpiUnit2) {
		this.kpiUnit2 = kpiUnit2;
		this.kpiUnit = kpiUnit2;
	}
	
	public String getKpiUnit3() {
		return kpiUnit3;
	}
	
	public void setKpiUnit3(String kpiUnit3) {
		this.kpiUnit3 = kpiUnit3;
		this.kpiUnit = kpiUnit3;
	}
	
	public String getExplaination() {
		return this.explaination;
	}
	
	public void setExplaination(String explaination) {
		this.explaination = explaination;
	}
	
	public String getKpiExplainJosn() {
		return this.kpiExplainJosn;
	}
	
	public void setKpiExplainJosn(String kpiExplainJosn) {
		this.kpiExplainJosn = kpiExplainJosn;
	}
	
}
