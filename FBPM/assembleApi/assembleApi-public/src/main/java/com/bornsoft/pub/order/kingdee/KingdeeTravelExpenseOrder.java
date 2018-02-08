package com.bornsoft.pub.order.kingdee;

import java.util.List;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 差旅费报销
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午9:39:33
 * @version:     v1.0
 */
public class KingdeeTravelExpenseOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 单据号 **/
	private String bizNo;
	/** 经办人 **/
	private String dealMan;
	/** 经办人部门编号 **/
	private String dealManDeptCode;
	/** 是否公务卡支付 **/
	private boolean isGwk;
	/** 经办人名称 **/
	private String dealManName;	
	/** 报销详情 **/
	private List<TravelExpenseInfo> feeDetail;

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(bizNo, "bizNo");
		ValidateParamUtil.hasTextV2(dealMan, "dealMan");
		ValidateParamUtil.hasTextV2(dealManDeptCode, "dealManDeptCode");
		ValidateParamUtil.hasTrue(feeDetail!=null, "feeDetail");
	}

	/**
	 * @return the bizNo
	 */
	public String getBizNo() {
		return bizNo;
	}

	/**
	 * @param bizNo the bizNo to set
	 */
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	/**
	 * @return the dealMan
	 */
	public String getDealMan() {
		return dealMan;
	}

	/**
	 * @param dealMan the dealMan to set
	 */
	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	/**
	 * @return the dealManDeptCode
	 */
	public String getDealManDeptCode() {
		return dealManDeptCode;
	}

	/**
	 * @param dealManDeptCode the dealManDeptCode to set
	 */
	public void setDealManDeptCode(String dealManDeptCode) {
		this.dealManDeptCode = dealManDeptCode;
	}


	public boolean isGwk() {
		return isGwk;
	}

	public void setGwk(boolean isGwk) {
		this.isGwk = isGwk;
	}

	public List<TravelExpenseInfo> getFeeDetail() {
		return feeDetail;
	}

	public void setFeeDetail(List<TravelExpenseInfo> feeDetail) {
		this.feeDetail = feeDetail;
	}

	/**
	 * @return the dealManName
	 */
	public String getDealManName() {
		return dealManName;
	}

	/**
	 * @param dealManName the dealManName to set
	 */
	public void setDealManName(String dealManName) {
		this.dealManName = dealManName;
	}

	/**
	 * @Description: 详情
	 * @author:      xiaohui@yiji.com
	 * @date         2016-9-1 上午9:42:54
	 * @version:     v1.0
	 */
	public static class TravelExpenseInfo extends BornInfoBase{
		/** 费用种类 **/
		private String feeType;
		/** 报销金额 **/
		private String amount;
		/** 报销部门编号 **/
		private String bxDeptCode;
		/** 进项税金 **/
		private String taxAmount;
		/** 贷方科目 **/
		private String credit;
		/**
		 * @return the feeType
		 */
		public String getFeeType() {
			return feeType;
		}
		/**
		 * @param feeType the feeType to set
		 */
		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		/**
		 * @return the amount
		 */
		public String getAmount() {
			return amount;
		}
		/**
		 * @param amount the amount to set
		 */
		public void setAmount(String amount) {
			this.amount = amount;
		}
		/**
		 * @return the bxDeptCode
		 */
		public String getBxDeptCode() {
			return bxDeptCode;
		}
		/**
		 * @param bxDeptCode the bxDeptCode to set
		 */
		public void setBxDeptCode(String bxDeptCode) {
			this.bxDeptCode = bxDeptCode;
		}
		/**
		 * @return the taxAmount
		 */
		public String getTaxAmount() {
			return taxAmount;
		}
		/**
		 * @param taxAmount the taxAmount to set
		 */
		public void setTaxAmount(String taxAmount) {
			this.taxAmount = taxAmount;
		}
		/**
		 * @return the credit
		 */
		public String getCredit() {
			return credit;
		}
		/**
		 * @param credit the credit to set
		 */
		public void setCredit(String credit) {
			this.credit = credit;
		}
	}
}
