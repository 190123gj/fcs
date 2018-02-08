package com.born.fcs.pm.ws.info.financialkpi;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 财务指标 info
 * 
 * @author lirz
 *
 * 2016-6-3 上午10:34:43
 */
public class FinancialKpiInfo extends BaseToStringInfo {

	private static final long serialVersionUID = 4849631292632675781L;
	
	private long kpiId;
	private long formId;
	private long parentId;
	private String kpiType;
	private String kpiCode;
	private String kpiName;
	private String kpiValue1;
	private String kpiValue2;
	private String kpiValue3;
	private String kpiValue4;
	private String kpiValue5;
	private String kpiValue6;
	private String kpiValue7;
	private String remark;
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	private String kpiClass;

	public long getKpiId() {
		return kpiId;
	}
	
	public void setKpiId(long kpiId) {
		this.kpiId = kpiId;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getKpiType() {
		return kpiType;
	}
	
	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
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

	public String getKpiValue4() {
		return kpiValue4;
	}
	
	public void setKpiValue4(String kpiValue4) {
		this.kpiValue4 = kpiValue4;
	}

	public String getKpiValue5() {
		return kpiValue5;
	}
	
	public void setKpiValue5(String kpiValue5) {
		this.kpiValue5 = kpiValue5;
	}

	public String getKpiValue6() {
		return kpiValue6;
	}
	
	public void setKpiValue6(String kpiValue6) {
		this.kpiValue6 = kpiValue6;
	}

	public String getKpiValue7() {
		return kpiValue7;
	}
	
	public void setKpiValue7(String kpiValue7) {
		this.kpiValue7 = kpiValue7;
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

	public String getKpiClass() {
		return kpiClass;
	}

	public void setKpiClass(String kpiClass) {
		this.kpiClass = kpiClass;
	}

}
