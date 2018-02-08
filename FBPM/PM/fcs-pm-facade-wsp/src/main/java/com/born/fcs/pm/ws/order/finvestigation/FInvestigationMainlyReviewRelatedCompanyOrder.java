package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.CompanyTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户主体评价 - 相关联公司
 * 
 * @author lirz
 *
 * 2016-3-10 下午2:51:15
 */
public class FInvestigationMainlyReviewRelatedCompanyOrder extends ValidateOrderBase{

	private static final long serialVersionUID = 5787595083886847087L;
	private long id; //主键
	private long MReviewId; //对应客户主体评价ID
	//(客户下属公司、全资和控股子公司/客户主要参股公司/客户其它关联公司)
	private CompanyTypeEnum relation; //关系
	private String relationDesc; //从属关系/关联关系
	private String companyName; //公司名称
	private String registerCapitalStr; //注册资本
	private Double capitalRatio; //持股比例
	private String majorBusi; //主营业务
	private String assetScale; //资产规模
	private Double assetLiabilityRatio; //资产负债率
	private String netProfitThisYearStr; //本年净利润
	private String netProfitLastYearStr; //去年净利润
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(relationDesc)
				&& isNull(companyName)
				&& isNull(registerCapitalStr)
				&& isNull(capitalRatio)
				&& isNull(majorBusi)
				&& isNull(assetScale)
				&& isNull(assetLiabilityRatio)
				&& isNull(netProfitThisYearStr)
				&& isNull(netProfitLastYearStr)
				;
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

	public CompanyTypeEnum getRelation() {
		return relation;
	}
	
	public void setRelation(CompanyTypeEnum relation) {
		this.relation = relation;
	}
	
	public String getRelationStr() {
		return (null == this.relation) ? "" : this.relation.code();
	}
	
	public void setRelationStr(String code) {
		this.relation = CompanyTypeEnum.getByCode(code);
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
		if(isNull(this.registerCapitalStr)) {
			return new Money(0L);
		}
		return Money.amout(this.registerCapitalStr);
	}
	
	public String getRegisterCapitalStr() {
		return registerCapitalStr;
	}
	
	public void setRegisterCapitalStr(String registerCapitalStr) {
		this.registerCapitalStr = registerCapitalStr;
	}

	public Double getCapitalRatio() {
		return capitalRatio;
	}
	
	public void setCapitalRatio(Double capitalRatio) {
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

	public Double getAssetLiabilityRatio() {
		return assetLiabilityRatio;
	}
	
	public void setAssetLiabilityRatio(Double assetLiabilityRatio) {
		this.assetLiabilityRatio = assetLiabilityRatio;
	}

	public Money getNetProfitThisYear() {
		if(isNull(this.netProfitThisYearStr)) {
			return new Money(0L);
		}
		return Money.amout(this.netProfitThisYearStr);
	}
	
	public String getNetProfitThisYearStr() {
		return netProfitThisYearStr;
	}
	
	public void setNetProfitThisYearStr(String netProfitThisYearStr) {
		this.netProfitThisYearStr = netProfitThisYearStr;
	}

	public Money getNetProfitLastYear() {
		if(isNull(this.netProfitLastYearStr)) {
			return new Money(0L);
		}
		return Money.amout(this.netProfitLastYearStr);
	}
	
	public String getNetProfitLastYearStr() {
		return netProfitLastYearStr;
	}
	
	public void setNetProfitLastYearStr(String netProfitLastYearStr) {
		this.netProfitLastYearStr = netProfitLastYearStr;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
