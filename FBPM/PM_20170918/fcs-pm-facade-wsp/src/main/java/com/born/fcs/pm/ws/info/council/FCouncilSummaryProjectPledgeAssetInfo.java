package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 抵(质)押
 * @author wuzj
 */
public class FCouncilSummaryProjectPledgeAssetInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 19030272459982451L;
	/** 主键 */
	private long id;
	/** 会议纪要对应项目ID */
	private long spId;
	/** 类型 */
	private GuaranteeTypeEnum type;
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
	/** 新时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
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
	
	public GuaranteeTypeEnum getType() {
		return this.type;
	}
	
	public void setType(GuaranteeTypeEnum type) {
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
	
	public void setPledgeRate(double pledgeRate) {
		this.pledgeRate = pledgeRate;
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
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
