package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 资产抵质押
 *
 * @author lirz
 * 
 * 2016-8-22 下午6:28:19
 *
 */
public class FInvestigationCreditSchemePledgeAssetInfo extends BaseToStringInfo{

	private static final long serialVersionUID = 7788126800785466436L;

	private long id;

	private long formId;

	private GuaranteeTypeEnum type;

	private String typeDesc;

	private long assetsId;

	private String assetType;

	private String ownershipName;

	private Money evaluationPrice = new Money(0, 0);

	private double pledgeRate;
	
	private String synPosition;

	private String postposition;

	private Money debtedAmount = new Money(0, 0);
	
	private String assetRemark;
	
	private String remark;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public GuaranteeTypeEnum getType() {
		return type;
	}
	
	public void setType(GuaranteeTypeEnum type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}
	
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public long getAssetsId() {
		return assetsId;
	}
	
	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}

	public String getAssetType() {
		return assetType;
	}
	
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getOwnershipName() {
		return ownershipName;
	}
	
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}

	public Money getEvaluationPrice() {
		return evaluationPrice;
	}
	
	public void setEvaluationPrice(Money evaluationPrice) {
		if (evaluationPrice == null) {
			this.evaluationPrice = new Money(0, 0);
		} else {
			this.evaluationPrice = evaluationPrice;
		}
	}

	public double getPledgeRate() {
		return pledgeRate;
	}
	
	public void setPledgeRate(double pledgeRate) {
		this.pledgeRate = pledgeRate;
	}

	public String getSynPosition() {
		return this.synPosition;
	}

	public void setSynPosition(String synPosition) {
		this.synPosition = synPosition;
	}

	public String getPostposition() {
		return this.postposition;
	}

	public void setPostposition(String postposition) {
		this.postposition = postposition;
	}

	public Money getDebtedAmount() {
		return this.debtedAmount;
	}

	public void setDebtedAmount(Money debtedAmount) {
		this.debtedAmount = debtedAmount;
	}

	public String getAssetRemark() {
		return this.assetRemark;
	}

	public void setAssetRemark(String assetRemark) {
		this.assetRemark = assetRemark;
	}

	public String getRemark() {
		return this.remark;
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

}
