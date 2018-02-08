package com.bornsoft.pub.order.risk;

import java.util.List;

import com.bornsoft.pub.enums.BudgetRelationEnum;
import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;
import com.yjf.common.lang.util.money.Money;

/***
 * @Description: 客户基础信息补充接口
 * @author taibai@yiji.com
 * @date 2016-8-6 下午3:07:19
 * @version V1.0
 */
public class SynsCustomInfoOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;
	/**
	 */
	private String licenseNo;
	private String customName;

	private List<CreditStatusInfo> creditStatus;
	private List<GuaranteeInfo> guaranteeInfo;
	private List<DebtInfo> debtInfo;
	private List<BusinessRelationShipInfo> businessRelationship;

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public List<CreditStatusInfo> getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(List<CreditStatusInfo> creditStatus) {
		this.creditStatus = creditStatus;
	}

	public List<GuaranteeInfo> getGuaranteeInfo() {
		return guaranteeInfo;
	}

	public void setGuaranteeInfo(List<GuaranteeInfo> guaranteeInfo) {
		this.guaranteeInfo = guaranteeInfo;
	}

	public List<DebtInfo> getDebtInfo() {
		return debtInfo;
	}

	public void setDebtInfo(List<DebtInfo> debtInfo) {
		this.debtInfo = debtInfo;
	}

	public List<BusinessRelationShipInfo> getBusinessRelationship() {
		return businessRelationship;
	}

	public void setBusinessRelationship(
			List<BusinessRelationShipInfo> businessRelationship) {
		this.businessRelationship = businessRelationship;
	}
	
	/***
	  * @Description: 担保信息
	  * @author taibai@yiji.com 
	  * @date  2016-8-6 下午3:18:03
	  * @version V1.0
	 */
	public static class GuaranteeInfo extends BornInfoBase {
		/** 被担保人 */
		private String vouchee;
		/** 担保金额 */
		private Money guaranteeAmount;
		/** 担保方式 */
		private String guaranteeWay;
		/** 担保到期日 */
		private String expiryDate;
		/** 提供的对价 */
		private Money consideration;

		public String getVouchee() {
			return vouchee;
		}

		public void setVouchee(String vouchee) {
			this.vouchee = vouchee;
		}

		public String getGuaranteeWay() {
			return guaranteeWay;
		}

		public void setGuaranteeWay(String guaranteeWay) {
			this.guaranteeWay = guaranteeWay;
		}

		public String getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
		}

		public Money getGuaranteeAmount() {
			return guaranteeAmount;
		}

		public void setGuaranteeAmount(Money guaranteeAmount) {
			this.guaranteeAmount = guaranteeAmount;
		}

		public Money getConsideration() {
			return consideration;
		}

		public void setConsideration(Money consideration) {
			this.consideration = consideration;
		}

	}

	/***
	 * @Description: 负债状况
	 * @author taibai@yiji.com
	 * @date 2016-8-6 下午3:18:03
	 * @version V1.0
	 */
	public  static  class DebtInfo extends BornInfoBase {
		
		/** 负债类型 */
		private String debtType;
		/** 借款银行 */
		private String bank;
		/** 借款金额 */
		private Money loanAmount;
		/** 贷款起始日 */
		private String startTime;
		/** 贷款截止日 */
		private String endTime;

		public String getDebtType() {
			return debtType;
		}

		public void setDebtType(String debtType) {
			this.debtType = debtType;
		}

		public String getBank() {
			return bank;
		}

		public void setBank(String bank) {
			this.bank = bank;
		}

		public Money getLoanAmount() {
			return loanAmount;
		}

		public void setLoanAmount(Money loanAmount) {
			this.loanAmount = loanAmount;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

	}

	/***
	  * @Description: 信用状态 
	  * @author taibai@yiji.com 
	  * @date  2016-8-6 下午3:18:31
	  * @version V1.0
	 */
	public static class CreditStatusInfo extends BornInfoBase {
		/** 融资机构 */
		private String organization;
		/** 融资金额 */
		private Money financeAmount;
		/** 融资起始日期  yyyy-MM-dd HH:mm:ss*/
		private String startTime;
		/** 融资截止日期 yyyy-MM-dd HH:mm:ss */
		private String endTime;
		/** 融资成本 */
		private String financeCost;
		/** 担保方式 */
		private String guaranteeWay;
		/** 提供的对价 */
		private Money consideration;
		/** 被担保人 */
		private String vouchee;

		public String getOrganization() {
			return organization;
		}

		public void setOrganization(String organization) {
			this.organization = organization;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}


		public Money getFinanceAmount() {
			return financeAmount;
		}

		public void setFinanceAmount(Money financeAmount) {
			this.financeAmount = financeAmount;
		}

		public String getFinanceCost() {
			return financeCost;
		}

		public void setFinanceCost(String financeCost) {
			this.financeCost = financeCost;
		}

		public String getGuaranteeWay() {
			return guaranteeWay;
		}

		public void setGuaranteeWay(String guaranteeWay) {
			this.guaranteeWay = guaranteeWay;
		}

		public Money getConsideration() {
			return consideration;
		}

		public void setConsideration(Money consideration) {
			this.consideration = consideration;
		}

		public String getVouchee() {
			return vouchee;
		}

		public void setVouchee(String vouchee) {
			this.vouchee = vouchee;
		}

	}

	/***
	  * @Description: 经营关系 
	  * @author taibai@yiji.com 
	  * @date  2016-8-6 下午3:23:04
	  * @version V1.0
	 */
	public static class BusinessRelationShipInfo extends BornInfoBase {
		/** 收支关系 */
		private BudgetRelationEnum budgetRelation;
		/** 关联企业名称 */
		private String enterpriseName;
		/** 交易金额 */
		private Money tradeAmount;

		public String getEnterpriseName() {
			return enterpriseName;
		}

		public void setEnterpriseName(String enterpriseName) {
			this.enterpriseName = enterpriseName;
		}

		public BudgetRelationEnum getBudgetRelation() {
			return budgetRelation;
		}

		public void setBudgetRelation(BudgetRelationEnum budgetRelation) {
			this.budgetRelation = budgetRelation;
		}

		public Money getTradeAmount() {
			return tradeAmount;
		}

		public void setTradeAmount(Money tradeAmount) {
			this.tradeAmount = tradeAmount;
		}
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(licenseNo, "licenseNo");
		ValidateParamUtil.hasTextV2(customName, "customName");
		
		ValidateParamUtil.hasTrue(
				  (creditStatus!=null&&creditStatus.size()>0)
				||(guaranteeInfo!=null&&guaranteeInfo.size()>0)
				||(debtInfo!=null&&debtInfo.size()>0)
				||(businessRelationship!=null&&businessRelationship.size()>0), "至少填写一项基础信息");
	}

	@Override
	public String getService() {
		return "synCustomInfo";
	}
}
