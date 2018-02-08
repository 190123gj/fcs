/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.pledgetext;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 抵质押品-文字信息-自定义Order
 *
 * @author jil
 *
 */
public class PledgeTextCustomQueryOrder extends QueryPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744020142207248192L;

	private long textId;

	private long typeId;

	private String fieldName;

	private String fieldType;

	private long controlLength;

	private String measurementUnit;

	private String mostCompleteSelection;

	private String timeSelectionRange;

	private String conditionItem;

	private String relationConditionItem;

	private String relationFieldName;

	private String isRequired;

	private String latestEntryForm;

	private String isByRelation;

	private Date rawAddTime;

	public long getTextId() {
		return textId;
	}

	public void setTextId(long textId) {
		this.textId = textId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public long getControlLength() {
		return controlLength;
	}

	public void setControlLength(long controlLength) {
		this.controlLength = controlLength;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getMostCompleteSelection() {
		return mostCompleteSelection;
	}

	public void setMostCompleteSelection(String mostCompleteSelection) {
		this.mostCompleteSelection = mostCompleteSelection;
	}

	public String getTimeSelectionRange() {
		return timeSelectionRange;
	}

	public void setTimeSelectionRange(String timeSelectionRange) {
		this.timeSelectionRange = timeSelectionRange;
	}

	public String getConditionItem() {
		return conditionItem;
	}

	public void setConditionItem(String conditionItem) {
		this.conditionItem = conditionItem;
	}

	public String getRelationConditionItem() {
		return relationConditionItem;
	}

	public void setRelationConditionItem(String relationConditionItem) {
		this.relationConditionItem = relationConditionItem;
	}

	public String getRelationFieldName() {
		return relationFieldName;
	}

	public void setRelationFieldName(String relationFieldName) {
		this.relationFieldName = relationFieldName;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getLatestEntryForm() {
		return latestEntryForm;
	}

	public void setLatestEntryForm(String latestEntryForm) {
		this.latestEntryForm = latestEntryForm;
	}

	public String getIsByRelation() {
		return isByRelation;
	}

	public void setIsByRelation(String isByRelation) {
		this.isByRelation = isByRelation;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

}
