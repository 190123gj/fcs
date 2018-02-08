package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 实际控制人或自然人股东个人资产、负债状况（非国有必填）
 * 
 * @author lirz
 * 
 * 2016-3-10 下午4:15:11
 */
public class FInvestigationMainlyReviewAssetAndLiabilityOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 8190546831927874854L;
	private long id;
	private long MReviewId; //对应客户主体评价ID
	private String name; //际控制人或自然人股东姓名
	private String sex; //性别
	private String certNo; //证件号码
	private String houseNum; //房屋数量
	private String carNum; //车辆数量
	private String investAmount;
	
	private String depositAmount;
	private String investAmountStr; //投资金额
	private String depositAmountStr; //个人存款金额
	private String maritalStatus; //婚姻情况
	private String spouseName; //配有姓名
	private String spouseCertType; //配偶证件类型
	private String spouseCertNo; //配偶证件号码
	private String spouseContactNo; //配偶联系电话(手机)
	private String spouseAddress; //配偶家庭住址
	private String spouseImmovableProperty; //配偶不动产信息
	private String spouseMovableProperty; //配偶动产信息
	private String hasBankLoan; //是否有银行贷款
	private String consumerLoanBankCheckbox; //消费类借款
	private String businesLoanBankCheckbox; //个人经营性贷款
	private String mortgageLoanBankCheckbox; //按揭类贷款
	private String hasFolkLoan; //是否有民间贷款
	private String bankLoanAmountStr; //银行贷款总额
	private String folkLoanAmountStr; //民间贷款总额
	private String consumerLoanBank; //消费类贷款银行
	private String consumerLoanAmountStr; //消费类贷款金额
	private Date consumerLoanStartDate; //消费类贷款开始时间
	private Date consumerLoanEndDate; //消费类贷款结束时间
	private String businesLoanBank; //个人经营性贷款银行
	private String businesLoanAmountStr; //个人经营性贷款金额
	private Date businesLoanStartDate; //个人经营性贷款开始时间
	private Date businesLoanEndDate; //个人经营性贷款结束时间
	private String mortgageLoanBank; //按揭贷款银行
	private String mortgageLoanAmountStr; //按揭贷款金额
	private Date mortgageLoanStartDate; //按揭贷款开始时间
	private Date mortgageLoanEndDate; //按揭贷款结束时间
	private String remark; //备注
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(name) && isNull(sex) && isNull(certNo) && isNull(houseNum) && isNull(carNum)
				&& isNull(investAmountStr) && isNull(investAmountStr) && isNull(depositAmountStr)
				&& isNull(maritalStatus) && isNull(spouseName) && isNull(spouseCertType)
				&& isNull(spouseCertNo) && isNull(spouseContactNo) && isNull(spouseAddress)
				&& isNull(spouseImmovableProperty) && isNull(spouseMovableProperty)
				&& isNull(hasBankLoan) && isNull(hasFolkLoan) && isNull(bankLoanAmountStr)
				&& isNull(folkLoanAmountStr) && isNull(consumerLoanBank)
				&& isNull(consumerLoanAmountStr) && isNull(consumerLoanStartDate)
				&& isNull(consumerLoanEndDate) && isNull(businesLoanBank)
				&& isNull(businesLoanAmountStr) && isNull(businesLoanStartDate)
				&& isNull(businesLoanEndDate) && isNull(mortgageLoanBank)
				&& isNull(mortgageLoanAmountStr) && isNull(mortgageLoanStartDate)
				&& isNull(mortgageLoanEndDate) && isNull(remark);
	}
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getMReviewId() {
		return MReviewId;
	}
	
	public void setMReviewId(long MReviewId) {
		this.MReviewId = MReviewId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getHouseNum() {
		return this.houseNum;
	}
	
	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}
	
	public String getCarNum() {
		return this.carNum;
	}
	
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
	public String getInvestAmount() {
		if (StringUtil.isNotBlank(investAmount)) {
			return this.investAmount;
		}
		return this.investAmountStr;
	}
	
	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}
	
	public String getDepositAmount() {
		if (StringUtil.isNotBlank(this.depositAmount)) {
			return this.depositAmount;
		}
		return this.depositAmountStr;
	}
	
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	public String getInvestAmountStr() {
		return investAmountStr;
	}
	
	public void setInvestAmountStr(String investAmountStr) {
		this.investAmountStr = investAmountStr;
	}
	
	public String getDepositAmountStr() {
		return depositAmountStr;
	}
	
	public void setDepositAmountStr(String depositAmountStr) {
		this.depositAmountStr = depositAmountStr;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getSpouseName() {
		return spouseName;
	}
	
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	
	public String getSpouseCertType() {
		return spouseCertType;
	}
	
	public void setSpouseCertType(String spouseCertType) {
		this.spouseCertType = spouseCertType;
	}
	
	public String getSpouseCertNo() {
		return spouseCertNo;
	}
	
	public void setSpouseCertNo(String spouseCertNo) {
		this.spouseCertNo = spouseCertNo;
	}
	
	public String getSpouseContactNo() {
		return spouseContactNo;
	}
	
	public void setSpouseContactNo(String spouseContactNo) {
		this.spouseContactNo = spouseContactNo;
	}
	
	public String getSpouseAddress() {
		return spouseAddress;
	}
	
	public void setSpouseAddress(String spouseAddress) {
		this.spouseAddress = spouseAddress;
	}
	
	public String getSpouseImmovableProperty() {
		return spouseImmovableProperty;
	}
	
	public void setSpouseImmovableProperty(String spouseImmovableProperty) {
		this.spouseImmovableProperty = spouseImmovableProperty;
	}
	
	public String getSpouseMovableProperty() {
		return spouseMovableProperty;
	}
	
	public void setSpouseMovableProperty(String spouseMovableProperty) {
		this.spouseMovableProperty = spouseMovableProperty;
	}
	
	public String getHasBankLoan() {
		StringBuffer sb = new StringBuffer();
		if (!isNull(hasBankLoan)) {
			sb.append(hasBankLoan);
		}
		if (!isNull(consumerLoanBankCheckbox)) {
			sb.append(consumerLoanBankCheckbox);
		}
		if (!isNull(businesLoanBankCheckbox)) {
			sb.append(businesLoanBankCheckbox);
		}
		if (!isNull(mortgageLoanBankCheckbox)) {
			sb.append(mortgageLoanBankCheckbox);
		}
		return sb.toString();
	}
	
	public void setHasBankLoan(String hasBankLoan) {
		this.hasBankLoan = hasBankLoan;
	}
	
	public String getHasFolkLoan() {
		return hasFolkLoan;
	}
	
	public void setHasFolkLoan(String hasFolkLoan) {
		this.hasFolkLoan = hasFolkLoan;
	}
	
	public Money getBankLoanAmount() {
		if (isNull(this.bankLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.bankLoanAmountStr);
	}
	
	public String getBankLoanAmountStr() {
		return bankLoanAmountStr;
	}
	
	public void setBankLoanAmountStr(String bankLoanAmountStr) {
		this.bankLoanAmountStr = bankLoanAmountStr;
	}
	
	public Money getFolkLoanAmount() {
		if (isNull(this.folkLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.folkLoanAmountStr);
	}
	
	public String getFolkLoanAmountStr() {
		return folkLoanAmountStr;
	}
	
	public void setFolkLoanAmountStr(String folkLoanAmountStr) {
		this.folkLoanAmountStr = folkLoanAmountStr;
	}
	
	public String getConsumerLoanBank() {
		return consumerLoanBank;
	}
	
	public void setConsumerLoanBank(String consumerLoanBank) {
		this.consumerLoanBank = consumerLoanBank;
	}
	
	public Money getConsumerLoanAmount() {
		if (isNull(this.consumerLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.consumerLoanAmountStr);
	}
	
	public String getConsumerLoanAmountStr() {
		return consumerLoanAmountStr;
	}
	
	public void setConsumerLoanAmountStr(String consumerLoanAmountStr) {
		this.consumerLoanAmountStr = consumerLoanAmountStr;
	}
	
	public Date getConsumerLoanStartDate() {
		return consumerLoanStartDate;
	}
	
	public void setConsumerLoanStartDate(Date consumerLoanStartDate) {
		this.consumerLoanStartDate = consumerLoanStartDate;
	}
	
	public void setConsumerLoanStartDateStr(String consumerLoanStartDateStr) {
		this.consumerLoanStartDate = DateUtil.strToDtSimpleFormat(consumerLoanStartDateStr);
	}
	
	public Date getConsumerLoanEndDate() {
		return consumerLoanEndDate;
	}
	
	public void setConsumerLoanEndDate(Date consumerLoanEndDate) {
		this.consumerLoanEndDate = consumerLoanEndDate;
	}
	
	public void setConsumerLoanEndDateStr(String consumerLoanEndDateStr) {
		this.consumerLoanEndDate = DateUtil.strToDtSimpleFormat(consumerLoanEndDateStr);
	}
	
	public String getBusinesLoanBank() {
		return businesLoanBank;
	}
	
	public void setBusinesLoanBank(String businesLoanBank) {
		this.businesLoanBank = businesLoanBank;
	}
	
	public Money getBusinesLoanAmount() {
		if (isNull(this.businesLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.businesLoanAmountStr);
	}
	
	public String getBusinesLoanAmountStr() {
		return this.businesLoanAmountStr;
	}
	
	public void setBusinesLoanAmountStr(String businesLoanAmountStr) {
		this.businesLoanAmountStr = businesLoanAmountStr;
	}
	
	public Date getBusinesLoanStartDate() {
		return businesLoanStartDate;
	}
	
	public void setBusinesLoanStartDate(Date businesLoanStartDate) {
		this.businesLoanStartDate = businesLoanStartDate;
	}
	
	public void setBusinesLoanStartDateStr(String businesLoanStartDateStr) {
		this.businesLoanStartDate = DateUtil.strToDtSimpleFormat(businesLoanStartDateStr);
	}
	
	public Date getBusinesLoanEndDate() {
		return businesLoanEndDate;
	}
	
	public void setBusinesLoanEndDate(Date businesLoanEndDate) {
		this.businesLoanEndDate = businesLoanEndDate;
	}
	
	public void setBusinesLoanEndDateStr(String businesLoanEndDateStr) {
		this.businesLoanEndDate = DateUtil.strToDtSimpleFormat(businesLoanEndDateStr);
	}
	
	public String getMortgageLoanBank() {
		return mortgageLoanBank;
	}
	
	public void setMortgageLoanBank(String mortgageLoanBank) {
		this.mortgageLoanBank = mortgageLoanBank;
	}
	
	public Money getMortgageLoanAmount() {
		if (isNull(this.mortgageLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.mortgageLoanAmountStr);
	}
	
	public String getMortgageLoanAmountStr() {
		return mortgageLoanAmountStr;
	}
	
	public void setMortgageLoanAmountStr(String mortgageLoanAmountStr) {
		this.mortgageLoanAmountStr = mortgageLoanAmountStr;
	}
	
	public Date getMortgageLoanStartDate() {
		return mortgageLoanStartDate;
	}
	
	public void setMortgageLoanStartDate(Date mortgageLoanStartDate) {
		this.mortgageLoanStartDate = mortgageLoanStartDate;
	}
	
	public void setMortgageLoanStartDateStr(String mortgageLoanStartDateStr) {
		this.mortgageLoanStartDate = DateUtil.strToDtSimpleFormat(mortgageLoanStartDateStr);
	}
	
	public Date getMortgageLoanEndDate() {
		return mortgageLoanEndDate;
	}
	
	public void setMortgageLoanEndDate(Date mortgageLoanEndDate) {
		this.mortgageLoanEndDate = mortgageLoanEndDate;
	}
	
	public void setMortgageLoanEndDateStr(String mortgageLoanEndDateStr) {
		this.mortgageLoanEndDate = DateUtil.strToDtSimpleFormat(mortgageLoanEndDateStr);
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getConsumerLoanBankCheckbox() {
		return consumerLoanBankCheckbox;
	}
	
	public void setConsumerLoanBankCheckbox(String consumerLoanBankCheckbox) {
		this.consumerLoanBankCheckbox = consumerLoanBankCheckbox;
	}
	
	public String getBusinesLoanBankCheckbox() {
		return businesLoanBankCheckbox;
	}
	
	public void setBusinesLoanBankCheckbox(String businesLoanBankCheckbox) {
		this.businesLoanBankCheckbox = businesLoanBankCheckbox;
	}
	
	public String getMortgageLoanBankCheckbox() {
		return mortgageLoanBankCheckbox;
	}
	
	public void setMortgageLoanBankCheckbox(String mortgageLoanBankCheckbox) {
		this.mortgageLoanBankCheckbox = mortgageLoanBankCheckbox;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
