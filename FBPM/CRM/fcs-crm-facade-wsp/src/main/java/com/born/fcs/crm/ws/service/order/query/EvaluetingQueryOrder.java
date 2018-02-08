package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.StringUtil;

/**
 * 分级指标评价详情查询
 * */
public class EvaluetingQueryOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5057589876585507583L;
	/** 客户Id */
	private String customerId;
	/** 哪一年评级 */
	private String year;
	/** 指标类型 */
	private String evalueType;
	/** 指标类型 */
	private String evaluetingKey;
	/** 评级步骤 0. 客户经理，1 审核 */
	private String step;
	
	private long formId;
	
	@Override
	public void check() {
		validateHasText(customerId, "客户ID");
		validateHasText(year, "评价年限");
		if (StringUtil.isBlank(evalueType) && StringUtil.isBlank(evaluetingKey)) {
			validateHasText(evalueType, "指标类型");
			validateHasText(evaluetingKey, "指标类型");
			validateHasText(step, "指标类型");
		}
		
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getStep() {
		return this.step;
	}
	
	public void setStep(String step) {
		this.step = step;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getEvaluetingKey() {
		return this.evaluetingKey;
	}
	
	public void setEvaluetingKey(String evaluetingKey) {
		this.evaluetingKey = evaluetingKey;
	}
	
	public String getEvalueType() {
		return this.evalueType;
	}
	
	public void setEvalueType(String evalueType) {
		this.evalueType = evalueType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvaluetingQueryOrder [customerId=");
		builder.append(customerId);
		builder.append(", year=");
		builder.append(year);
		builder.append(", evalueType=");
		builder.append(evalueType);
		builder.append(", evaluetingKey=");
		builder.append(evaluetingKey);
		builder.append("]");
		return builder.toString();
	}
	
}
