package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Date;

/** 指标评分结果 */
public class EvaluetingInfo implements Serializable {
	private static final long serialVersionUID = -1657084344012999722L;
	/** 主键 */
	private long id;
	/** 客户Id */
	private String customerId;
	/** 哪一年评级 */
	private String year;
	/** 评价Id */
	private long evaluetingId;
	/** 非配置指标评价：评价项标识 */
	private String evaluetingKey;
	/** 评价实际值 */
	private String actualValue;
	/** 该项评分 */
	private String thisScore;
	/** 备注 */
	private String memo;
	/** 指标类型 */
	private String evalueType;
	/** 小分类 */
	private String evalueTypeChild;
	/** 创建时间 */
	private Date rawAddTime;
	/** 录入人 */
	private String inputPerson;
	/** 评级步骤，0:客户经理评级，1 ，复审 */
	private String step;
	
	public String getInputPerson() {
		return this.inputPerson;
	}
	
	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}
	
	public String getStep() {
		return this.step;
	}
	
	public void setStep(String step) {
		this.step = step;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getActualValue() {
		return this.actualValue;
	}
	
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	
	public String getEvaluetingKey() {
		return this.evaluetingKey;
	}
	
	public String getEvalueTypeChild() {
		return this.evalueTypeChild;
	}
	
	public void setEvalueTypeChild(String evalueTypeChild) {
		this.evalueTypeChild = evalueTypeChild;
	}
	
	public void setEvaluetingKey(String evaluetingKey) {
		this.evaluetingKey = evaluetingKey;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public long getEvaluetingId() {
		return this.evaluetingId;
	}
	
	public void setEvaluetingId(long evaluetingId) {
		this.evaluetingId = evaluetingId;
	}
	
	public String getThisScore() {
		return this.thisScore;
	}
	
	public void setThisScore(String thisScore) {
		this.thisScore = thisScore;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getEvalueType() {
		return this.evalueType;
	}
	
	public void setEvalueType(String evalueType) {
		this.evalueType = evalueType;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvaluetingInfo [id=");
		builder.append(id);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", year=");
		builder.append(year);
		builder.append(", evaluetingId=");
		builder.append(evaluetingId);
		builder.append(", evaluetingKey=");
		builder.append(evaluetingKey);
		builder.append(", actualValue=");
		builder.append(actualValue);
		builder.append(", thisScore=");
		builder.append(thisScore);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", evalueType=");
		builder.append(evalueType);
		builder.append(", evalueTypeChild=");
		builder.append(evalueTypeChild);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", inputPerson=");
		builder.append(inputPerson);
		builder.append(", step=");
		builder.append(step);
		builder.append("]");
		return builder.toString();
	}
	
}
