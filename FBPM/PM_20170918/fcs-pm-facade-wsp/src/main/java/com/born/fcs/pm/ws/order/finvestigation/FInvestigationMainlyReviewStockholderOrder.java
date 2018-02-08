package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.HolderTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 主要股东情况
 * 
 * @author lirz
 * 
 * 2016-3-10 下午2:09:39
 */
public class FInvestigationMainlyReviewStockholderOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -2677573972582651204L;
	
	private long id; //主键'
	private long MReviewId; //对应客户评价ID
	private String holderName; //股东名称
	private HolderTypeEnum holderType; //股东性质
	private String holderCertType; //股东证件类型
	private String holderCertNo; //股东证件号码(注册号)
//	private String legalCertno; //法人证件号
	private String actualInvestmentStr; //实际投资
	private Double paidinCapitalRatio; //占实收资本比例
	private String holderMajorBusi; //股东主营业务
	private String capitalScaleStr;
	private String produceScaleStr;
	private String incomeScaleStr;
	private String holderContactNo; //股东联系电话
	private String maritalStatus; //婚姻情况
	private String spouseName; //配偶姓名
	private String spouseCertType; //配偶证件类型
	private String spouseCertNo; //配偶证件号码
	private String spouseContactNo; //配偶联系电话（手机）
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(holderName) 
				&& null == holderType 
				&& isNull(holderCertType)
				&& isNull(holderCertNo)
//				&& isNull(legalCertno)
				&& isNull(actualInvestmentStr)
				&& isNull(paidinCapitalRatio)
				&& isNull(holderMajorBusi)
				&& isNull(capitalScaleStr)
				&& isNull(produceScaleStr)
				&& isNull(incomeScaleStr)
				&& isNull(holderContactNo)
				&& isNull(maritalStatus)
				&& isNull(spouseName)
				&& isNull(spouseCertType)
				&& isNull(spouseCertNo)
				&& isNull(spouseContactNo)
				;
	}
	
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
	
	public String getHolderTypeStr() {
		return (null == this.holderType) ? "" : this.holderType.code();
	}
	
	public void setHolderTypeStr(String code) {
		this.holderType = HolderTypeEnum.getByCode(code);
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
	
	public String getActualInvestmentStr() {
		return actualInvestmentStr;
	}
	
	public void setActualInvestmentStr(String actualInvestmentStr) {
		this.actualInvestmentStr = actualInvestmentStr;
	}
	
	public Money getActualInvestment() {
		if (isNull(this.actualInvestmentStr)) {
			return new Money(0L);
		}
		return Money.amout(this.actualInvestmentStr);
	}
	
	public Double getPaidinCapitalRatio() {
		return paidinCapitalRatio;
	}
	
	public void setPaidinCapitalRatio(Double paidinCapitalRatio) {
		this.paidinCapitalRatio = paidinCapitalRatio;
	}
	
	public String getHolderMajorBusi() {
		return holderMajorBusi;
	}
	
	public void setHolderMajorBusi(String holderMajorBusi) {
		this.holderMajorBusi = holderMajorBusi;
	}
	
	public String getCapitalScaleStr() {
		return capitalScaleStr;
	}
	
	public void setCapitalScaleStr(String capitalScaleStr) {
		this.capitalScaleStr = capitalScaleStr;
	}
	
	public Money getCapitalScale() {
		if (isNull(this.capitalScaleStr)) {
			return new Money(0L);
		}
		return Money.amout(this.capitalScaleStr);
	}
	
	public String getProduceScaleStr() {
		return produceScaleStr;
	}
	
	public void setProduceScaleStr(String produceScaleStr) {
		this.produceScaleStr = produceScaleStr;
	}
	
	public Money getProduceScale() {
		if (isNull(this.produceScaleStr)) {
			return new Money(0L);
		}
		return Money.amout(this.produceScaleStr);
	}
	
	public String getIncomeScaleStr() {
		return incomeScaleStr;
	}
	
	public void setIncomeScaleStr(String incomeScaleStr) {
		this.incomeScaleStr = incomeScaleStr;
	}
	
	public Money getIncomeScale() {
		if (isNull(this.incomeScaleStr)) {
			return new Money(0L);
		}
		return Money.amout(this.incomeScaleStr);
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
	
	public String getLegalCertno() {
		return holderCertNo;
	}

	public void setLegalCertno(String legalCertno) {
		this.holderCertNo = legalCertno;
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
