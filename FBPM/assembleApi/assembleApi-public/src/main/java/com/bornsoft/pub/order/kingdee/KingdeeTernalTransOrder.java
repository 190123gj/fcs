package com.bornsoft.pub.order.kingdee;

import java.util.List;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 内部转账
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午9:47:34
 * @version:     v1.0
 */
public class KingdeeTernalTransOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 单据号 **/
	private String bizNo;
	/** 经办人 **/
	private String dealMan;
	/** 经办人部门编号 **/
	private String dealManDeptCode;
	/** 经办人名称 **/
	private String dealManName;
	/** 详情 **/
	private List<TernalTransInfo> transDetail;
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(dealManDeptCode, "dealManDeptCode");
		ValidateParamUtil.hasTextV2(dealMan, "dealMan");
		ValidateParamUtil.hasTextV2(bizNo, "bizNo");
		ValidateParamUtil.hasTrue(transDetail!=null && transDetail.size()>0, "transDetail");
		
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
	 * @return the transDetail
	 */
	public List<TernalTransInfo> getTransDetail() {
		return transDetail;
	}

	/**
	 * @param transDetail the transDetail to set
	 */
	public void setTransDetail(List<TernalTransInfo> transDetail) {
		this.transDetail = transDetail;
	}


	/**
	 * @Description: 
	 * @author:      xiaohui@yiji.com
	 * @date         2016-9-1 上午9:52:35
	 * @version:     v1.0
	 */
	public static class TernalTransInfo extends BornInfoBase{
		/** 收款账号 **/
		private String skAccount;
		/** 付款账号 **/
		private String fkAccount;
		/** 划款金额 **/
		private String amount;
		/** 借方科目 **/
		private String debit;
		/** 贷方科目 **/
		private String credit;
		/**
		 * @return the skAccount
		 */
		public String getSkAccount() {
			return skAccount;
		}
		/**
		 * @param skAccount the skAccount to set
		 */
		public void setSkAccount(String skAccount) {
			this.skAccount = skAccount;
		}
		/**
		 * @return the fkAccount
		 */
		public String getFkAccount() {
			return fkAccount;
		}
		/**
		 * @param fkAccount the fkAccount to set
		 */
		public void setFkAccount(String fkAccount) {
			this.fkAccount = fkAccount;
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
		 * @return the debit
		 */
		public String getDebit() {
			return debit;
		}
		/**
		 * @param debit the debit to set
		 */
		public void setDebit(String debit) {
			this.debit = debit;
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
