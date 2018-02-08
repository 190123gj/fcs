package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.AssetReviewStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 尽调-资产复评
 * 
 * @author lirz
 * 
 * 2016-9-19 下午4:42:52
 * 
 */
public class FInvestigationAssetReviewInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1742414787862571820L;
	
	private long id;
	
	private long formId;
	
	private String review;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money amount = new Money(0, 0);
	
	private long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	
	private long riskManagerId;

	private String riskManagerAccount;

	private String riskManagerName;
	
	private AssetReviewStatusEnum status;
	
	private long reviewerId;
	
	private String reviewerAccount;
	
	private String reviewerName;
	
	private Date reviewTime;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//抵押
	private List<FInvestigationCreditSchemePledgeAssetInfo> pledges;
	private Money pledgeAssessPrice = new Money(0L); //抵押评估价格
	private Money pledgePrice = new Money(0L); //抵押价格
	//质押
	private List<FInvestigationCreditSchemePledgeAssetInfo> mortgages;
	private Money mortgageAssessPrice = new Money(0L); //质押评估价格
	private Money mortgagePrice = new Money(0L); //质押价格
	
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
	
	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		if (amount == null) {
			this.amount = new Money(0, 0);
		} else {
			this.amount = amount;
		}
	}
	
	public long getBusiManagerId() {
		return busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public long getRiskManagerId() {
		return this.riskManagerId;
	}

	public void setRiskManagerId(long riskManagerId) {
		this.riskManagerId = riskManagerId;
	}

	public String getRiskManagerAccount() {
		return this.riskManagerAccount;
	}

	public void setRiskManagerAccount(String riskManagerAccount) {
		this.riskManagerAccount = riskManagerAccount;
	}

	public String getRiskManagerName() {
		return this.riskManagerName;
	}

	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}

	public AssetReviewStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(AssetReviewStatusEnum status) {
		this.status = status;
	}
	
	public long getReviewerId() {
		return reviewerId;
	}
	
	public void setReviewerId(long reviewerId) {
		this.reviewerId = reviewerId;
	}
	
	public String getReviewerAccount() {
		return reviewerAccount;
	}
	
	public void setReviewerAccount(String reviewerAccount) {
		this.reviewerAccount = reviewerAccount;
	}
	
	public String getReviewerName() {
		return reviewerName;
	}
	
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	
	public Date getReviewTime() {
		return reviewTime;
	}
	
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
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
	
	public List<FInvestigationCreditSchemePledgeAssetInfo> getPledges() {
		return this.pledges;
	}
	
	public void setPledges(List<FInvestigationCreditSchemePledgeAssetInfo> pledges) {
		this.pledges = pledges;
	}
	
	public Money getPledgeAssessPrice() {
		return this.pledgeAssessPrice;
	}
	
	public void setPledgeAssessPrice(Money pledgeAssessPrice) {
		this.pledgeAssessPrice = pledgeAssessPrice;
	}
	
	public Money getPledgePrice() {
		return this.pledgePrice;
	}
	
	public void setPledgePrice(Money pledgePrice) {
		this.pledgePrice = pledgePrice;
	}
	
	public List<FInvestigationCreditSchemePledgeAssetInfo> getMortgages() {
		return this.mortgages;
	}
	
	public void setMortgages(List<FInvestigationCreditSchemePledgeAssetInfo> mortgages) {
		this.mortgages = mortgages;
	}
	
	public Money getMortgageAssessPrice() {
		return this.mortgageAssessPrice;
	}
	
	public void setMortgageAssessPrice(Money mortgageAssessPrice) {
		this.mortgageAssessPrice = mortgageAssessPrice;
	}
	
	public Money getMortgagePrice() {
		return this.mortgagePrice;
	}
	
	public void setMortgagePrice(Money mortgagePrice) {
		this.mortgagePrice = mortgagePrice;
	}
	
}
