package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 实际控制人或自然人股东个人资产、负债状况（非国有必填）
 * 
 * @author lirz
 *
 * 2016-3-10 下午4:15:11
 */
public class FInvestigationMainlyReviewAssetAndLiabilityInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 3577474184571605063L;
	
	private long id;
	private long MReviewId; //对应客户主体评价ID
	private String name; //际控制人或自然人股东姓名
	private String sex; //性别
	private String certNo; //证件号码
	private int houseNum; //房屋数量
	private int carNum; //车辆数量
	private Money investAmount = new Money(0, 0); //投资金额
	private Money depositAmount = new Money(0, 0); //个人存款金额
	private String maritalStatus; //婚姻情况
	private String spouseName; //配有姓名
	private String spouseCertType; //配偶证件类型
	private String spouseCertNo; //配偶证件号码
	private String spouseContactNo; //配偶联系电话(手机)
	private String spouseAddress; //配偶家庭住址
	private String spouseImmovableProperty; //配偶不动产信息
	private String spouseMovableProperty; //配偶动产信息
	private String hasBankLoan; //是否有银行贷款
	private String hasFolkLoan; //是否有民间贷款
	private Money bankLoanAmount = new Money(0, 0); //银行贷款总额
	private Money folkLoanAmount = new Money(0, 0); //民间贷款总额
	private String consumerLoanBank; //消费类贷款银行
	private Money consumerLoanAmount; //消费类贷款金额
	private Date consumerLoanStartDate; //消费类贷款开始时间
	private Date consumerLoanEndDate; //消费类贷款结束时间
	private String businesLoanBank; //个人经营性贷款银行
	private Money businesLoanAmount = new Money(0, 0); //个人经营性贷款金额
	private Date businesLoanStartDate; //个人经营性贷款开始时间
	private Date businesLoanEndDate; //个人经营性贷款结束时间
	private String mortgageLoanBank; //按揭贷款银行
	private Money mortgageLoanAmount = new Money(0, 0); //按揭贷款金额
	private Date mortgageLoanStartDate; //按揭贷款开始时间
	private Date mortgageLoanEndDate; //按揭贷款结束时间
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
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
	
	public int getHouseNum() {
		return houseNum;
	}
	
	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}
	
	public int getCarNum() {
		return carNum;
	}
	
	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}
	
	public Money getInvestAmount() {
		return investAmount;
	}
	
	public void setInvestAmount(Money investAmount) {
		if (investAmount == null) {
			this.investAmount = new Money(0, 0);
		} else {
			this.investAmount = investAmount;
		}
	}
	
	public Money getDepositAmount() {
		return depositAmount;
	}
	
	public void setDepositAmount(Money depositAmount) {
		if (depositAmount == null) {
			this.depositAmount = new Money(0, 0);
		} else {
			this.depositAmount = depositAmount;
		}
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
		return hasBankLoan;
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
		return bankLoanAmount;
	}
	
	public void setBankLoanAmount(Money bankLoanAmount) {
		if (bankLoanAmount == null) {
			this.bankLoanAmount = new Money(0, 0);
		} else {
			this.bankLoanAmount = bankLoanAmount;
		}
	}
	
	public Money getFolkLoanAmount() {
		return folkLoanAmount;
	}
	
	public void setFolkLoanAmount(Money folkLoanAmount) {
		if (folkLoanAmount == null) {
			this.folkLoanAmount = new Money(0, 0);
		} else {
			this.folkLoanAmount = folkLoanAmount;
		}
	}
	
	public String getConsumerLoanBank() {
		return consumerLoanBank;
	}
	
	public void setConsumerLoanBank(String consumerLoanBank) {
		this.consumerLoanBank = consumerLoanBank;
	}
	
	public Money getConsumerLoanAmount() {
		return consumerLoanAmount;
	}
	
	public void setConsumerLoanAmount(Money consumerLoanAmount) {
		this.consumerLoanAmount = consumerLoanAmount;
	}
	
	public Date getConsumerLoanStartDate() {
		return consumerLoanStartDate;
	}
	
	public void setConsumerLoanStartDate(Date consumerLoanStartDate) {
		this.consumerLoanStartDate = consumerLoanStartDate;
	}
	
	public Date getConsumerLoanEndDate() {
		return consumerLoanEndDate;
	}
	
	public void setConsumerLoanEndDate(Date consumerLoanEndDate) {
		this.consumerLoanEndDate = consumerLoanEndDate;
	}
	
	public String getBusinesLoanBank() {
		return businesLoanBank;
	}
	
	public void setBusinesLoanBank(String businesLoanBank) {
		this.businesLoanBank = businesLoanBank;
	}
	
	public Money getBusinesLoanAmount() {
		return businesLoanAmount;
	}
	
	public void setBusinesLoanAmount(Money businesLoanAmount) {
		if (businesLoanAmount == null) {
			this.businesLoanAmount = new Money(0, 0);
		} else {
			this.businesLoanAmount = businesLoanAmount;
		}
	}
	
	public Date getBusinesLoanStartDate() {
		return businesLoanStartDate;
	}
	
	public void setBusinesLoanStartDate(Date businesLoanStartDate) {
		this.businesLoanStartDate = businesLoanStartDate;
	}
	
	public Date getBusinesLoanEndDate() {
		return businesLoanEndDate;
	}
	
	public void setBusinesLoanEndDate(Date businesLoanEndDate) {
		this.businesLoanEndDate = businesLoanEndDate;
	}
	
	public String getMortgageLoanBank() {
		return mortgageLoanBank;
	}
	
	public void setMortgageLoanBank(String mortgageLoanBank) {
		this.mortgageLoanBank = mortgageLoanBank;
	}
	
	public Money getMortgageLoanAmount() {
		return mortgageLoanAmount;
	}
	
	public void setMortgageLoanAmount(Money mortgageLoanAmount) {
		if (mortgageLoanAmount == null) {
			this.mortgageLoanAmount = new Money(0, 0);
		} else {
			this.mortgageLoanAmount = mortgageLoanAmount;
		}
	}
	
	public Date getMortgageLoanStartDate() {
		return mortgageLoanStartDate;
	}
	
	public void setMortgageLoanStartDate(Date mortgageLoanStartDate) {
		this.mortgageLoanStartDate = mortgageLoanStartDate;
	}
	
	public Date getMortgageLoanEndDate() {
		return mortgageLoanEndDate;
	}
	
	public void setMortgageLoanEndDate(Date mortgageLoanEndDate) {
		this.mortgageLoanEndDate = mortgageLoanEndDate;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	
}
