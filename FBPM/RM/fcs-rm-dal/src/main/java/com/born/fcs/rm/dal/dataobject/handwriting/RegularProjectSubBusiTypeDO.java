package com.born.fcs.rm.dal.dataobject.handwriting;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

/**
 * 多资金渠道项目情况
 * @author wuzj
 */
public class RegularProjectSubBusiTypeDO implements Serializable {
	
	private static final long serialVersionUID = -5397936912368675559L;
	
	private Date reportDate;
	
	private long customerId;
	
	private String customerName;
	
	private String projectCode;
	
	private String projectName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money amount = Money.zero();
	
	private double feeRate;
	
	private String feeType;
	
	/**
	 * 业务类型&&首次发生时间||业务类型1&&首次发生时间1
	 */
	private String firstOccurInfo;
	
	/**
	 * 业务类型&&业务类型名称&&合同金额||业务类型1&&业务类型名称1&&合同金额1
	 */
	private String contractInfo;
	
	/**
	 * 累计发生：业务类型&&业务类型名称&&发生额||业务类型1&&业务类型名称1&&发生额1
	 */
	private String occurInfo;
	
	/**
	 * 累计解保：业务类型&&业务类型名称&&解保额||业务类型1&&业务类型名称1&&解保额1
	 */
	private String releaseInfo;
	/**
	 * 本月发生： 业务类型&&业务类型名称&&发生额||业务类型1&&业务类型名称1&&发生额1
	 */
	private String occurInfoThisMonth;
	
	/**
	 * 本月解保：业务类型&&业务类型名称&&解保额||业务类型1&&业务类型名称1&&解保额1
	 */
	private String releaseInfoThisMonth;
	/**
	 * 本年发生： 业务类型&&业务类型名称&&发生额||业务类型1&&业务类型名称1&&发生额1
	 */
	private String occurInfoThisYear;
	
	/**
	 * 本年解保：业务类型&&业务类型名称&&解保额||业务类型1&&业务类型名称1&&解保额1
	 */
	private String releaseInfoThisYear;
	/**
	 * 期初发生： 业务类型&&业务类型名称&&发生额||业务类型1&&业务类型名称1&&发生额1
	 */
	private String occurInfoBeginning;
	
	/**
	 * 期初解保：业务类型&&业务类型名称&&解保额||业务类型1&&业务类型名称1&&解保额1
	 */
	private String releaseInfoBeginning;
	
	public Date getReportDate() {
		return reportDate;
	}
	
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
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
	
	public String getFirstOccurInfo() {
		return firstOccurInfo;
	}
	
	public void setFirstOccurInfo(String firstOccurInfo) {
		this.firstOccurInfo = firstOccurInfo;
	}
	
	public String getContractInfo() {
		return contractInfo;
	}
	
	public void setContractInfo(String contractInfo) {
		this.contractInfo = contractInfo;
	}
	
	public String getOccurInfo() {
		return occurInfo;
	}
	
	public void setOccurInfo(String occurInfo) {
		this.occurInfo = occurInfo;
	}
	
	public String getReleaseInfo() {
		return releaseInfo;
	}
	
	public void setReleaseInfo(String releaseInfo) {
		this.releaseInfo = releaseInfo;
	}
	
	public String getOccurInfoThisMonth() {
		return occurInfoThisMonth;
	}
	
	public void setOccurInfoThisMonth(String occurInfoThisMonth) {
		this.occurInfoThisMonth = occurInfoThisMonth;
	}
	
	public String getOccurInfoThisYear() {
		return occurInfoThisYear;
	}
	
	public void setOccurInfoThisYear(String occurInfoThisYear) {
		this.occurInfoThisYear = occurInfoThisYear;
	}
	
	public String getOccurInfoBeginning() {
		return occurInfoBeginning;
	}
	
	public void setOccurInfoBeginning(String occurInfoBeginning) {
		this.occurInfoBeginning = occurInfoBeginning;
	}
	
	public String getReleaseInfoBeginning() {
		return releaseInfoBeginning;
	}
	
	public void setReleaseInfoBeginning(String releaseInfoBeginning) {
		this.releaseInfoBeginning = releaseInfoBeginning;
	}
	
	public String getReleaseInfoThisMonth() {
		return releaseInfoThisMonth;
	}
	
	public void setReleaseInfoThisMonth(String releaseInfoThisMonth) {
		this.releaseInfoThisMonth = releaseInfoThisMonth;
	}
	
	public String getReleaseInfoThisYear() {
		return releaseInfoThisYear;
	}
	
	public void setReleaseInfoThisYear(String releaseInfoThisYear) {
		this.releaseInfoThisYear = releaseInfoThisYear;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public double getFeeRate() {
		return feeRate;
	}
	
	public void setFeeRate(double feeRate) {
		this.feeRate = feeRate;
	}
	
	public String getFeeType() {
		return feeType;
	}
	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
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
