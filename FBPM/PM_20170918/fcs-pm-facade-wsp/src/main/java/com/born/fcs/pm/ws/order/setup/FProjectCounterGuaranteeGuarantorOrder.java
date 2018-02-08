package com.born.fcs.pm.ws.order.setup;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

public class FProjectCounterGuaranteeGuarantorOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = -3530112405883538396L;
	
	private Long id;
	
	private String guarantor;
	
	private String legalPersion;
	
	private String registerCapitalStr;
	
	private Money registerCapital = Money.zero();
	
	private String totalAssetStr;
	
	private Money totalAsset = Money.zero();
	
	private String externalGuaranteeAmountStr;
	
	private Money externalGuaranteeAmount = Money.zero();
	
	private Integer sortOrder;
	
	public boolean isNull() {
		return isNull(guarantor) && isNull(legalPersion) && isNull(registerCapitalStr)
				&& isNull(totalAssetStr) && isNull(externalGuaranteeAmountStr);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getGuarantor() {
		return this.guarantor;
	}
	
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}
	
	public String getLegalPersion() {
		return this.legalPersion;
	}
	
	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}
	
	public String getRegisterCapitalStr() {
		return this.registerCapitalStr;
	}
	
	public void setRegisterCapitalStr(String registerCapitalStr) {
		this.registerCapitalStr = registerCapitalStr;
	}
	
	public Money getRegisterCapital() {
		if (StringUtil.isNotBlank(registerCapitalStr))
			return Money.amout(registerCapitalStr);
		return this.registerCapital;
	}
	
	public void setRegisterCapital(Money registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	public String getTotalAssetStr() {
		return this.totalAssetStr;
	}
	
	public void setTotalAssetStr(String totalAssetStr) {
		this.totalAssetStr = totalAssetStr;
	}
	
	public Money getTotalAsset() {
		if (StringUtil.isNotBlank(totalAssetStr))
			return Money.amout(totalAssetStr);
		return this.totalAsset;
	}
	
	public void setTotalAsset(Money totalAsset) {
		this.totalAsset = totalAsset;
	}
	
	public String getExternalGuaranteeAmountStr() {
		return this.externalGuaranteeAmountStr;
	}
	
	public void setExternalGuaranteeAmountStr(String externalGuaranteeAmountStr) {
		this.externalGuaranteeAmountStr = externalGuaranteeAmountStr;
	}
	
	public Money getExternalGuaranteeAmount() {
		if (StringUtil.isNotBlank(externalGuaranteeAmountStr))
			return Money.amout(externalGuaranteeAmountStr);
		return this.externalGuaranteeAmount;
	}
	
	public void setExternalGuaranteeAmount(Money externalGuaranteeAmount) {
		this.externalGuaranteeAmount = externalGuaranteeAmount;
	}
	
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
