package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CompanyTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户主体评价 - 相关联公司
 * 
 * @author lirz
 *
 * 2016-3-10 下午2:51:15
 */
public class FInvestigationMainlyReviewRelatedCompanyInfo extends BaseToStringInfo{

	private static final long serialVersionUID = 5943806237473030400L;
	
	private long id; //主键
	private long MReviewId; //对应客户主体评价ID
	//(客户下属公司、全资和控股子公司/客户主要参股公司/客户其它关联公司)
	private CompanyTypeEnum relation; //关系
	private String relationDesc; //从属关系/关联关系
	private String companyName; //公司名称
	private Money registerCapital = new Money(0, 0); //注册资本
	private double capitalRatio; //持股比例
	private String majorBusi; //主营业务
	private String assetScale; //资产规模
	private double assetLiabilityRatio; //资产负债率
	private Money netProfitThisYear = new Money(0, 0); //本年净利润
	private Money netProfitLastYear = new Money(0, 0); //去年净利润
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

	public CompanyTypeEnum getRelation() {
		return relation;
	}
	
	public void setRelation(CompanyTypeEnum relation) {
		this.relation = relation;
	}

	public String getRelationDesc() {
		return relationDesc;
	}
	
	public void setRelationDesc(String relationDesc) {
		this.relationDesc = relationDesc;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Money getRegisterCapital() {
		return registerCapital;
	}
	
	public void setRegisterCapital(Money registerCapital) {
		if (registerCapital == null) {
			this.registerCapital = new Money(0, 0);
		} else {
			this.registerCapital = registerCapital;
		}
	}

	public double getCapitalRatio() {
		return capitalRatio;
	}
	
	public void setCapitalRatio(double capitalRatio) {
		this.capitalRatio = capitalRatio;
	}

	public String getMajorBusi() {
		return majorBusi;
	}
	
	public void setMajorBusi(String majorBusi) {
		this.majorBusi = majorBusi;
	}

	public String getAssetScale() {
		return assetScale;
	}
	
	public void setAssetScale(String assetScale) {
		this.assetScale = assetScale;
	}

	public double getAssetLiabilityRatio() {
		return assetLiabilityRatio;
	}
	
	public void setAssetLiabilityRatio(double assetLiabilityRatio) {
		this.assetLiabilityRatio = assetLiabilityRatio;
	}

	public Money getNetProfitThisYear() {
		return netProfitThisYear;
	}
	
	public void setNetProfitThisYear(Money netProfitThisYear) {
		this.netProfitThisYear = netProfitThisYear;
	}

	public Money getNetProfitLastYear() {
		return netProfitLastYear;
	}
	
	public void setNetProfitLastYear(Money netProfitLastYear) {
		this.netProfitLastYear = netProfitLastYear;
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
