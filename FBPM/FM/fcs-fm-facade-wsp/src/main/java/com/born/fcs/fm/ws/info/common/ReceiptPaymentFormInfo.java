package com.born.fcs.fm.ws.info.common;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收款单/付款单数据来源
 * @author wuzj
 */
public class ReceiptPaymentFormInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 2469930788925509096L;
	/** 主键 */
	private long sourceId;
	/** 单据来源 */
	private SourceFormEnum sourceForm;
	/** 来源单据ID */
	private String sourceFormId;
	/** 单据来源系统 */
	private SystemEnum sourceFormSys;
	/** 资金方向 */
	private FundDirectionEnum fundDirection;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 合同编号 */
	private String contractNo;
	/** 合同名称 */
	private String contractName;
	/** 理财转让对象 */
	private String transferName;
	/** 理财产品名称 */
	private String productName;
	/** 处理状态 */
	private ReceiptPaymentFormStatusEnum status;
	/** 总金额 */
	private Money amount = new Money(0, 0);
	/** 发起人 */
	private long userId;
	/** 发起人账号 */
	private String userAccount;
	/** 发起人名称 */
	private String userName;
	/** 发起部门ID */
	private long deptId;
	/** 发起部门编号 */
	private String deptCode;
	/** 发起部门名称 */
	private String deptName;
	/** 备注 */
	private String remark;
	/**
	 * 简单流程（IS/NO）
	 */
	private String isSimple;
	/** 推送过来的附件 */
	private String attach;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	/** 费用明细 */
	List<ReceiptPaymentFormFeeInfo> feeList;
	
	public long getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	
	public SourceFormEnum getSourceForm() {
		return sourceForm;
	}
	
	public void setSourceForm(SourceFormEnum sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	public String getSourceFormId() {
		return this.sourceFormId;
	}
	
	public void setSourceFormId(String sourceFormId) {
		this.sourceFormId = sourceFormId;
	}
	
	public SystemEnum getSourceFormSys() {
		return sourceFormSys;
	}
	
	public void setSourceFormSys(SystemEnum sourceFormSys) {
		this.sourceFormSys = sourceFormSys;
	}
	
	public FundDirectionEnum getFundDirection() {
		return fundDirection;
	}
	
	public void setFundDirection(FundDirectionEnum fundDirection) {
		this.fundDirection = fundDirection;
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
	
	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public ReceiptPaymentFormStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(ReceiptPaymentFormStatusEnum status) {
		this.status = status;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getIsSimple() {
		return isSimple;
	}
	
	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
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
	
	public List<ReceiptPaymentFormFeeInfo> getFeeList() {
		return feeList;
	}
	
	public void setFeeList(List<ReceiptPaymentFormFeeInfo> feeList) {
		this.feeList = feeList;
	}
	
	public String getContractName() {
		return contractName;
	}
	
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	public String getTransferName() {
		return transferName;
	}
	
	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
}
