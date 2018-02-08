package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 抵(质)押
 * @author wuzj
 */
public class FCouncilSummaryProjectPledgeAssetOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -5137305565439559381L;
	/** 主键 */
	private long id;
	/** 会议纪要对应项目ID */
	private long spId;
	/** 类型 */
	private String type;
	/** 类型描述 */
	private String typeDesc;
	/** 资产ID */
	private long assetsId;
	/** 资产类型 AM 存的pledge_goods_type表中three_level_classification_two值 */
	private String assetType;
	/** 所有权利人名称 */
	private String ownershipName;
	/** 评估价格 */
	private Money evaluationPrice = new Money(0, 0);
	/** 抵质押率 */
	private double pledgeRate;
	/** 抵押顺位 */
	private String synPosition;
	/** 是否后置抵押 */
	private String postposition;
	/** 已抵债金额 */
	private Money debtedAmount = new Money(0, 0);
	/** 资产关键信息 */
	private String assetRemark;
	/** 备注 */
	private String remark;
	/** 排序号 */
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(id) && isNull(spId) && isNull(type) && isNull(typeDesc) && isNull(assetsId)
				&& isNull(assetType) && isNull(ownershipName) && isNull(evaluationPrice)
				&& isNull(pledgeRate) && isNull(synPosition) && isNull(postposition)
				&& isNull(debtedAmount) && isNull(assetRemark);
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTypeDesc() {
		return this.typeDesc;
	}
	
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	
	public long getAssetsId() {
		return this.assetsId;
	}
	
	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}
	
	public String getAssetType() {
		return this.assetType;
	}
	
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	
	public String getOwnershipName() {
		return this.ownershipName;
	}
	
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}
	
	public Money getEvaluationPrice() {
		return this.evaluationPrice;
	}
	
	public void setEvaluationPrice(Money evaluationPrice) {
		this.evaluationPrice = evaluationPrice;
	}
	
	public double getPledgeRate() {
		return this.pledgeRate;
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
	
	public void setDebtedAmountStr(String debtedAmountStr) {
		if (isNull(debtedAmountStr)) {
			this.debtedAmount = new Money(0, 0);
		} else {
			this.debtedAmount = Money.amout(debtedAmountStr);
		}
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
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
