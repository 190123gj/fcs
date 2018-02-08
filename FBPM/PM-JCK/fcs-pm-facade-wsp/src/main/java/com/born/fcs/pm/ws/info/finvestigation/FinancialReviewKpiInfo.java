package com.born.fcs.pm.ws.info.finvestigation;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户财务评价 - 财务报表指标 (一行数据)
 * 
 * @author lirz
 * 
 * 2016-3-29 下午4:29:09
 */
public class FinancialReviewKpiInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -7480838690603380224L;
	
	private String kpiCode; //指标标识
	private String kpiName; //指标名
	private int kpiLevel; //指标层级
	private String kpiValue; //指标值(最近一期)
	private String kpiValue1; //指标值(前一年)
	private String kpiValue2; //指标值(前二年)
	private String kpiValue3; //指标值(前三年)
	private String kpiUnit; //指标单位
	private double kpiRatio; //占比(指标解释)
	private long parentId; //父指标ID
	private String termType; //分类（账期）
	private String remark; //说明
	private String kpiClass = ""; //样式
	
	private String errorMes;//统计错误提示(最近一期)
	private String errorMes1;//统计错误提示(前一年)
	private String errorMes2;//统计错误提示(前二年)
	private String errorMes3;//统计错误提示(前三年)
	
	public String getAverage() {
		String[] str = { kpiValue, kpiValue1, kpiValue2, kpiValue3 };
		int i = 0;
		Money m = new Money(0L);
		for (String s : str) {
			if (null == s || "".equals(s.trim())) {
				continue;
			}
			m.addTo(Money.amout(s));
			i++;
		}
		if (i > 0) {
			return m.divide(i).toStandardString();
		} else {
			return "";
		}
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
	
	public String getKpiClass() {
		return kpiClass;
	}
	
	public void setKpiClass(String kpiClass) {
		if (null != kpiClass) {
			this.kpiClass = kpiClass;
		}
	}
	
	public String getErrorMes() {
		return this.errorMes;
	}
	
	public void setErrorMes(String errorMes) {
		this.errorMes = errorMes;
	}
	
	public String getErrorMes1() {
		return this.errorMes1;
	}
	
	public void setErrorMes1(String errorMes1) {
		this.errorMes1 = errorMes1;
	}
	
	public String getErrorMes2() {
		return this.errorMes2;
	}
	
	public void setErrorMes2(String errorMes2) {
		this.errorMes2 = errorMes2;
	}
	
	public String getErrorMes3() {
		return this.errorMes3;
	}
	
	public void setErrorMes3(String errorMes3) {
		this.errorMes3 = errorMes3;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FinancialReviewKpiInfo [kpiCode=");
		builder.append(kpiCode);
		builder.append(", kpiName=");
		builder.append(kpiName);
		builder.append(", kpiLevel=");
		builder.append(kpiLevel);
		builder.append(", kpiValue=");
		builder.append(kpiValue);
		builder.append(", kpiValue1=");
		builder.append(kpiValue1);
		builder.append(", kpiValue2=");
		builder.append(kpiValue2);
		builder.append(", kpiValue3=");
		builder.append(kpiValue3);
		builder.append(", kpiUnit=");
		builder.append(kpiUnit);
		builder.append(", kpiRatio=");
		builder.append(kpiRatio);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", termType=");
		builder.append(termType);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", kpiClass=");
		builder.append(kpiClass);
		builder.append(", errorMes=");
		builder.append(errorMes);
		builder.append(", errorMes1=");
		builder.append(errorMes1);
		builder.append(", errorMes2=");
		builder.append(errorMes2);
		builder.append(", errorMes3=");
		builder.append(errorMes3);
		builder.append("]");
		return builder.toString();
	}
	
}
