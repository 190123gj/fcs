package com.born.fcs.pm.ws.order.projectissueinformation;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 承销/发债类项目发行信息Order
 *
 *
 * @author Ji
 *
 */
public class ProjectIssueInformationOrder extends FormOrderBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8289531988022355743L;
	
	private Long id;
	
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 客户ID
	 */
	private long customerId;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户类型
	 */
	private String customerType;
	/**
	 * 业务类型
	 */
	private String busiType;
	/**
	 * 业务类型名称
	 */
	private String busiTypeName;
	/**
	 * 期限
	 */
	private int timeLimit;
	/**
	 * 期限单位
	 */
	private String timeUnit;
	/**
	 * 拟发行金额
	 */
	private Double amount;
	/**
	 * 交易所/发行机构ID
	 */
	private long institutionId;
	/**
	 * 交易所/发行机构名称
	 */
	private String institutionName;
	/**
	 * 发行利率
	 */
	private double issueRate;
	/**
	 * 项目依据
	 */
	private String projectGist;
	/**
	 * 债权代码
	 */
	private String bondCode;
	/**
	 * 担保函地址
	 */
	private String letterUrl;
	/**
	 * 付款凭证地址
	 */
	private String voucherUrl;
	/**
	 * 实际发行金额
	 */
	private Double actualAmount;
	/**
	 * 发行日期
	 */
	private Date issueDate;
	/**
	 * 到期日期
	 */
	private Date expireTime;
	/**
	 * 是否继续发售
	 */
	private String isContinue;
	
	/**
	 * 发售状态
	 */
	private String status;
	
	private Date rawUpdateTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public String getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public long getInstitutionId() {
		return institutionId;
	}
	
	public void setInstitutionId(long institutionId) {
		this.institutionId = institutionId;
	}
	
	public String getInstitutionName() {
		return institutionName;
	}
	
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	
	public String getProjectGist() {
		return projectGist;
	}
	
	public void setProjectGist(String projectGist) {
		this.projectGist = projectGist;
	}
	
	public String getBondCode() {
		return bondCode;
	}
	
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}
	
	public String getLetterUrl() {
		return letterUrl;
	}
	
	public void setLetterUrl(String letterUrl) {
		this.letterUrl = letterUrl;
	}
	
	public String getVoucherUrl() {
		return voucherUrl;
	}
	
	public void setVoucherUrl(String voucherUrl) {
		this.voucherUrl = voucherUrl;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getActualAmount() {
		return actualAmount;
	}
	
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public Date getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public String getIsContinue() {
		return isContinue;
	}
	
	public void setIsContinue(String isContinue) {
		this.isContinue = isContinue;
	}
	
	public void setIssueRate(double issueRate) {
		this.issueRate = issueRate;
	}
	
	public double getIssueRate() {
		return issueRate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
