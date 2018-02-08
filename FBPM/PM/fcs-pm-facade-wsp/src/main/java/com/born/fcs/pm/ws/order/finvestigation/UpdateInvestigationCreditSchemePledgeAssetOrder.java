package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 更新资产抵押价格及抵押率
 * 
 * @author lirz
 * 
 * 2016-9-18 上午10:40:51
 * 
 */
public class UpdateInvestigationCreditSchemePledgeAssetOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 2064196147881485315L;
	
	private long assetsId; //资产id
	private Money evaluationPrice = new Money(0, 0); // 评估价格
	private double pledgeRate; //抵押率
	private String ownershipName;
	private String assetRemark;
	
	@Override
	public void check() {
		validateGreaterThan(assetsId, "资产id");
	}
	
	public long getAssetsId() {
		return this.assetsId;
	}
	
	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}
	
	public Money getEvaluationPrice() {
		return this.evaluationPrice;
	}
	
	public void setEvaluationPrice(Money evaluationPrice) {
		if (null == evaluationPrice) {
			this.evaluationPrice = new Money(0L);
		} else {
			this.evaluationPrice = evaluationPrice;
		}
	}
	
	public double getPledgeRate() {
		return this.pledgeRate;
	}
	
	public void setPledgeRate(double pledgeRate) {
		this.pledgeRate = pledgeRate;
	}
	
	public String getOwnershipName() {
		return this.ownershipName;
	}
	
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}
	
	public String getAssetRemark() {
		return this.assetRemark;
	}
	
	public void setAssetRemark(String assetRemark) {
		this.assetRemark = assetRemark;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
