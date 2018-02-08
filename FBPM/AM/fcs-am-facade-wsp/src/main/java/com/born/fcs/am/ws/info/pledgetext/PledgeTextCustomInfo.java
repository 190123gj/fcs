package com.born.fcs.am.ws.info.pledgetext;

import java.util.Date;

import com.born.fcs.am.ws.enums.FieldTypeEnum;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.enums.TimeRangeEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 抵质押品-文字信息-自定义Info
 *
 * @author jil
 *
 */
public class PledgeTextCustomInfo extends BaseToStringInfo {

	private static final long serialVersionUID = 4707698711824974520L;

	private long textId;

	private long typeId;

	private String fieldName;

	private String fieldNameDesc;

	private FieldTypeEnum fieldType;

	private long controlLength;

	private String measurementUnit;

	private String mostCompleteSelection;

	private TimeRangeEnum timeSelectionRange;

	private String conditionItem;

	private String relationConditionItem;

	private String relationFieldName;

	private BooleanEnum isRequired;

	private LatestEntryFormEnum latestEntryForm;

	private BooleanEnum isByRelation;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

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

	public String getFieldNameDesc() {
		return fieldNameDesc;
	}

	public void setFieldNameDesc(String fieldNameDesc) {
		this.fieldNameDesc = fieldNameDesc;
	}

	public FieldTypeEnum getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldTypeEnum fieldType) {
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

	public TimeRangeEnum getTimeSelectionRange() {
		return timeSelectionRange;
	}

	public void setTimeSelectionRange(TimeRangeEnum timeSelectionRange) {
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

	public BooleanEnum getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(BooleanEnum isRequired) {
		this.isRequired = isRequired;
	}

	public LatestEntryFormEnum getLatestEntryForm() {
		return latestEntryForm;
	}

	public void setLatestEntryForm(LatestEntryFormEnum latestEntryForm) {
		this.latestEntryForm = latestEntryForm;
	}

	public BooleanEnum getIsByRelation() {
		return isByRelation;
	}

	public void setIsByRelation(BooleanEnum isByRelation) {
		this.isByRelation = isByRelation;
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

}
