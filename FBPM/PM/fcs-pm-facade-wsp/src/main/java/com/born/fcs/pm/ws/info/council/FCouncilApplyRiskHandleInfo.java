package com.born.fcs.pm.ws.info.council;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目上会申请 - 风险处置会/代偿项目上会
 * 
 * @author lirz
 * 
 * 2016-4-19 下午3:06:55
 */
public class FCouncilApplyRiskHandleInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -1026658771377985794L;
	
	private long id; //主键
	private long applyId; //待上会申请ID
	private String isRepay; //是否代偿
	private String companyName; //公司名称
	private String enterpriseType; //企业性质
	private Money guaranteeAmount = new Money(0, 0); //担保金额
	private double guaranteeRate; //担保费率
	private ChargeTypeEnum guaranteeRateType;
	private BooleanEnum canEditGuaranteeRate = BooleanEnum.YES; //是否可编辑担保费率
	private String loanBank; //放款银行
	private String loanTimeLimit; //放款期限
	private String busiManagerName; //客户经理
	private String riskManagerName; //风险
	private Money repayAmount = new Money(0, 0); //代偿金额
	private Date repayDate; //代偿日期
	private String loanType; //授信品种
	private String creditTimeLimit; //授信期限
	private Money creditAmount = new Money(0, 0); //授信金额
	private String basicInfo; //基本信息
	private String riskInfo; //风险事项
	private String lastCouncilDecision; //前次风险处置会决议事项
	private String lastCouncilCheck; //前次风险处置会决议落实情况
	private String thisCouncilScheme; //本次上会提交的代偿方案及追偿方案/本次上会提交的处置方案
	private String status; //状态
	private Date rawAddTime;
	private Date rawUpdateTime;
	private List<FCouncilApplyCreditInfo> credits; //授信基本情况
	
	protected List<FCouncilApplyCreditCompensationInfo> creditCompensationInfos;//担保明细
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public String getIsRepay() {
		return isRepay;
	}
	
	public void setIsRepay(String isRepay) {
		this.isRepay = isRepay;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getEnterpriseType() {
		return enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public Money getGuaranteeAmount() {
		return guaranteeAmount;
	}
	
	public void setGuaranteeAmount(Money guaranteeAmount) {
		if (guaranteeAmount == null) {
			this.guaranteeAmount = new Money(0, 0);
		} else {
			this.guaranteeAmount = guaranteeAmount;
		}
	}
	
	public double getGuaranteeRate() {
		return guaranteeRate;
	}
	
	public void setGuaranteeRate(double guaranteeRate) {
		this.guaranteeRate = guaranteeRate;
	}
	
	public BooleanEnum getCanEditGuaranteeRate() {
		return canEditGuaranteeRate;
	}
	
	public ChargeTypeEnum getGuaranteeRateType() {
		return this.guaranteeRateType;
	}
	
	public void setGuaranteeRateType(ChargeTypeEnum guaranteeRateType) {
		this.guaranteeRateType = guaranteeRateType;
	}
	
	public void setCanEditGuaranteeRate(BooleanEnum canEditGuaranteeRate) {
		this.canEditGuaranteeRate = canEditGuaranteeRate;
	}
	
	public String getLoanBank() {
		return loanBank;
	}
	
	public void setLoanBank(String loanBank) {
		this.loanBank = loanBank;
	}
	
	public String getLoanTimeLimit() {
		return loanTimeLimit;
	}
	
	public void setLoanTimeLimit(String loanTimeLimit) {
		this.loanTimeLimit = loanTimeLimit;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getRiskManagerName() {
		return riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public Money getRepayAmount() {
		return repayAmount;
	}
	
	public void setRepayAmount(Money repayAmount) {
		if (repayAmount == null) {
			this.repayAmount = new Money(0, 0);
		} else {
			this.repayAmount = repayAmount;
		}
	}
	
	public Date getRepayDate() {
		return repayDate;
	}
	
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	public String getLoanType() {
		return loanType;
	}
	
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	public String getCreditTimeLimit() {
		return creditTimeLimit;
	}
	
	public void setCreditTimeLimit(String creditTimeLimit) {
		this.creditTimeLimit = creditTimeLimit;
	}
	
	public Money getCreditAmount() {
		return creditAmount;
	}
	
	public void setCreditAmount(Money creditAmount) {
		if (creditAmount == null) {
			this.creditAmount = new Money(0, 0);
		} else {
			this.creditAmount = creditAmount;
		}
	}
	
	public String getBasicInfo() {
		return basicInfo;
	}
	
	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}
	
	public String getRiskInfo() {
		return riskInfo;
	}
	
	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}
	
	public String getLastCouncilDecision() {
		return lastCouncilDecision;
	}
	
	public void setLastCouncilDecision(String lastCouncilDecision) {
		this.lastCouncilDecision = lastCouncilDecision;
	}
	
	public String getLastCouncilCheck() {
		return lastCouncilCheck;
	}
	
	public void setLastCouncilCheck(String lastCouncilCheck) {
		this.lastCouncilCheck = lastCouncilCheck;
	}
	
	public String getThisCouncilScheme() {
		return thisCouncilScheme;
	}
	
	public void setThisCouncilScheme(String thisCouncilScheme) {
		this.thisCouncilScheme = thisCouncilScheme;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public List<FCouncilApplyCreditInfo> getCredits() {
		return credits;
	}
	
	public void setCredits(List<FCouncilApplyCreditInfo> credits) {
		this.credits = credits;
	}
	
	public List<FCouncilApplyCreditCompensationInfo> getCreditCompensationInfos() {
		return this.creditCompensationInfos;
	}
	
	public void setCreditCompensationInfos(	List<FCouncilApplyCreditCompensationInfo> creditCompensationInfos) {
		this.creditCompensationInfos = creditCompensationInfos;
	}
	
}
