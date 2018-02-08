package com.born.fcs.am.ws.order.pledgetypeattribute;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 资产-抵质押品分类-属性Order
 *
 * @author jil
 *
 */
public class PledgeTypeAttributeTextQueryOrder extends ProcessOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3711634544523781287L;

	private long attributeId;

	private long assetsId;

	private long typeId;

	private String attributeKey;

	private String attributeValue;

	private String customType;

	private Date rawAddTime;
	// 文字信息
	private String fieldName;

	private String fieldType;

	private String relationConditionItem;

	private String relationFieldName;

	private String isByRelation;

	public long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(long attributeId) {
		this.attributeId = attributeId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getCustomType() {
		return customType;
	}

	public void setCustomType(String customType) {
		this.customType = customType;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public long getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
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

	public String getIsByRelation() {
		return isByRelation;
	}

	public void setIsByRelation(String isByRelation) {
		this.isByRelation = isByRelation;
	}

}
