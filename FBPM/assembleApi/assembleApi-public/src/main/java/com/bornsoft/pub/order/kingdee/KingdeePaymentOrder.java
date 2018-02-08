package com.bornsoft.pub.order.kingdee;

import java.util.List;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;

/**
 * @Description: 付款单
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午9:20:10
 * @version:     v1.0
 */
public class KingdeePaymentOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/**转让对象**/
	private String transferName;
	/** 单据号 **/
	private String bizNo;
	/** 业务类型 **/
	private String busiType;
	/** 客户编码 **/
	private String customerCode;
	/** 项目编号 **/
	private String projectCode;
	/** 客户经理 **/
	private String busiManager;
	/** 部门编号 **/
	private String deptCode;
	/** 合同编号 **/
	private String contractNo;
	/** 客户名称 **/
	private String customerName;
	/** 客户经理名称 **/
	private String busiManagerName;
	/** 合同名称 **/
	private String contractName;
	/** 保证金账号名称 **/
	private String depositName;
	/** 理财产品名称 **/
	private String productName;
	/** 付款详情 **/
	private List<PaymentInfo> feeDetail;
	
	public String getTransferName() {
		return transferName;
	}

	public void setTransferName(String transferName) {
		this.transferName = transferName;
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
	 * @return the busiType
	 */
	public String getBusiType() {
		return busiType;
	}

	/**
	 * @param busiType the busiType to set
	 */
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the busiManager
	 */
	public String getBusiManager() {
		return busiManager;
	}

	/**
	 * @param busiManager the busiManager to set
	 */
	public void setBusiManager(String busiManager) {
		this.busiManager = busiManager;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public List<PaymentInfo> getFeeDetail() {
		return feeDetail;
	}

	public void setFeeDetail(List<PaymentInfo> feeDetail) {
		this.feeDetail = feeDetail;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the busiManagerName
	 */
	public String getBusiManagerName() {
		return busiManagerName;
	}

	/**
	 * @param busiManagerName the busiManagerName to set
	 */
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}

	/**
	 * @return the contractName
	 */
	public String getContractName() {
		return contractName;
	}

	/**
	 * @param contractName the contractName to set
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * @return the depositName
	 */
	public String getDepositName() {
		return depositName;
	}

	/**
	 * @param depositName the depositName to set
	 */
	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @Description: 付款详情
	 * @author:      xiaohui@yiji.com
	 * @date         2016-9-1 上午9:35:16
	 * @version:     v1.0
	 */
	public static class PaymentInfo extends BornInfoBase{
		/** 付款种类 **/
		private String feeType;
		/** 付款金额 **/
		private String amount;
		/** 付款时间 **/
		private String fkDate;
		/** 借方科目 **/
		private String debit;
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
		 * @return the fkDate
		 */
		public String getFkDate() {
			return fkDate;
		}
		/**
		 * @param fkDate the fkDate to set
		 */
		public void setFkDate(String fkDate) {
			this.fkDate = fkDate;
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
