package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.HolderTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 主要股东情况
 * 
 * @author lirz
 * 
 * 2016-3-10 下午2:09:39
 */
public class FInvestigationMainlyReviewStockholderInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8848774550983079595L;
	
	private long id; //主键
	private long MReviewId; //对应客户评价ID
	private String holderName; //股东名称
	private HolderTypeEnum holderType; //股东性质
	private String holderCertType; //股东证件类型
	private String holderCertNo; //股东证件号码(注册号)
	private Money actualInvestment = new Money(0, 0); //实际投资
	private double paidinCapitalRatio; //占实收资本比例
	private String holderMajorBusi; //股东主营业务
	private Money capitalScale; //股东规模（资产）
	private Money produceScale; //股东规模（生产能力）
	private Money incomeScale; //股东规模（收入）
	private String holderContactNo; //股东联系电话
	private String maritalStatus; //婚姻情况
	private String spouseName; //配偶姓名
	private String spouseCertType; //配偶证件类型
	private String spouseCertNo; //配偶证件号码
	private String spouseContactNo; //配偶联系电话（手机）
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getMReviewId() {
		return MReviewId;
	}
	
	public void setMReviewId(long mReviewId) {
		MReviewId = mReviewId;
	}
	
	public String getHolderName() {
		return holderName;
	}
	
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	
	public HolderTypeEnum getHolderType() {
		return holderType;
	}
	
	public void setHolderType(HolderTypeEnum holderType) {
		this.holderType = holderType;
	}
	
	public String getHolderCertType() {
		return holderCertType;
	}
	
	public void setHolderCertType(String holderCertType) {
		this.holderCertType = holderCertType;
	}
	
	public String getHolderCertNo() {
		return holderCertNo;
	}
	
	public void setHolderCertNo(String holderCertNo) {
		this.holderCertNo = holderCertNo;
	}
	
	public Money getActualInvestment() {
		return actualInvestment;
	}
	
	public void setActualInvestment(Money actualInvestment) {
		this.actualInvestment = actualInvestment;
	}
	
	public double getPaidinCapitalRatio() {
		return paidinCapitalRatio;
	}
	
	public void setPaidinCapitalRatio(double paidinCapitalRatio) {
		this.paidinCapitalRatio = paidinCapitalRatio;
	}
	
	public String getHolderMajorBusi() {
		return holderMajorBusi;
	}
	
	public void setHolderMajorBusi(String holderMajorBusi) {
		this.holderMajorBusi = holderMajorBusi;
	}
	
	public Money getCapitalScale() {
		return capitalScale;
	}
	
	public void setCapitalScale(Money capitalScale) {
		this.capitalScale = capitalScale;
	}
	
	public Money getProduceScale() {
		return produceScale;
	}
	
	public void setProduceScale(Money produceScale) {
		this.produceScale = produceScale;
	}
	
	public Money getIncomeScale() {
		return incomeScale;
	}
	
	public void setIncomeScale(Money incomeScale) {
		this.incomeScale = incomeScale;
	}
	
	public String getHolderContactNo() {
		return holderContactNo;
	}
	
	public void setHolderContactNo(String holderContactNo) {
		this.holderContactNo = holderContactNo;
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
