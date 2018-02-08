package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;

/**
 * 客户财务评价
 * 
 * @author lirz
 * 
 * 2016-3-11 上午10:07:10
 */
public class FInvestigationFinancialReviewOrder extends FInvestigationBaseOrder {
	
	private static final long serialVersionUID = 2240558156340295582L;
	
	private boolean isInit = false; //是否初始化
	private int subIndex = 0; //子页面
	private String isActive;
	
	private long FReviewId;
	private String importExcel; //是否导入了excel
	private String amountUnit1; //货币单位
	private String amountUnit2; //货币单位
	private String amountUnit3; //货币单位
	private String isAudited; //是否审计
	private String auditUnit; //审计单位
	private String auditSuggest; //审计意见类型
	//审计信息
	private List<FInvestigationFinancialReviewKpiOrder> auditInfos;
	//财务报表(资产负债表)
	private FinancialReviewKpiOrder financialHeader;
	private List<FinancialReviewKpiOrder> financialTables;
	private String auditSuggestExplain; //审计意见解释与说明
	//财务报表(利润表)
	private FinancialReviewKpiOrder profitHeader;
	private List<FinancialReviewKpiOrder> profitTables;
	//财务报表(现金流量表)
	private FinancialReviewKpiOrder flowHeader;
	private List<FinancialReviewKpiOrder> flowTables;
	//财务数据
	private FinancialReviewKpiOrder financialDataHeader;
	private List<FInvestigationFinancialReviewKpiOrder> financialDatas;
	//偿债能力
	private FinancialReviewKpiOrder payAblilitieHeader;
	private List<FinancialReviewKpiOrder> payAblilities;
	private String debtPayingAbility; //偿债能力解释与说明
	//运营能力
	private FinancialReviewKpiOrder operateAblilitieHeader;
	private List<FinancialReviewKpiOrder> operateAblilities;
	private String operatingAbility; //运营能力分析解释与说明
	//盈利能力
	private FinancialReviewKpiOrder benifitAblilitieHeader;
	private List<FinancialReviewKpiOrder> benifitAblilities;
	private String profitAbility; //盈利能力分析解释与说明
	//现金流
	private FinancialReviewKpiOrder cashFlowHeader;
	private List<FinancialReviewKpiOrder> cashFlows;
	private String cashFlowExplain; //现金流分析解释与说明
	private String assetQualityReview; //资产质量总体评价
	
	public long getFReviewId() {
		return FReviewId;
	}
	
	public void setFReviewId(long FReviewId) {
		this.FReviewId = FReviewId;
	}
	
	public String getImportExcel() {
		return importExcel;
	}
	
	public void setImportExcel(String importExcel) {
		this.importExcel = importExcel;
	}
	
	public String getIsAudited() {
		return isAudited;
	}
	
	public void setIsAudited(String isAudited) {
		this.isAudited = isAudited;
	}
	
	public String getAuditUnit() {
		return auditUnit;
	}
	
	public void setAuditUnit(String auditUnit) {
		this.auditUnit = auditUnit;
	}
	
	public String getAuditSuggest() {
		return auditSuggest;
	}
	
	public void setAuditSuggest(String auditSuggest) {
		this.auditSuggest = auditSuggest;
	}
	
	public String getAuditSuggestExplain() {
		return auditSuggestExplain;
	}
	
	public void setAuditSuggestExplain(String auditSuggestExplain) {
		this.auditSuggestExplain = auditSuggestExplain;
	}
	
	public String getDebtPayingAbility() {
		return debtPayingAbility;
	}
	
	public void setDebtPayingAbility(String debtPayingAbility) {
		this.debtPayingAbility = debtPayingAbility;
	}
	
	public String getOperatingAbility() {
		return operatingAbility;
	}
	
	public void setOperatingAbility(String operatingAbility) {
		this.operatingAbility = operatingAbility;
	}
	
	public String getProfitAbility() {
		return profitAbility;
	}
	
	public void setProfitAbility(String profitAbility) {
		this.profitAbility = profitAbility;
	}
	
	public String getCashFlowExplain() {
		return cashFlowExplain;
	}
	
	public void setCashFlowExplain(String cashFlowExplain) {
		this.cashFlowExplain = cashFlowExplain;
	}
	
	public String getAssetQualityReview() {
		return assetQualityReview;
	}
	
	public void setAssetQualityReview(String assetQualityReview) {
		this.assetQualityReview = assetQualityReview;
	}
	
	public List<FinancialReviewKpiOrder> getFinancialTables() {
		return financialTables;
	}
	
	public void setFinancialTables(List<FinancialReviewKpiOrder> financialTables) {
		this.financialTables = financialTables;
	}
	
	public List<FInvestigationFinancialReviewKpiOrder> getFinancialDatas() {
		return financialDatas;
	}
	
	public void setFinancialDatas(List<FInvestigationFinancialReviewKpiOrder> financialDatas) {
		this.financialDatas = financialDatas;
	}
	
	public List<FinancialReviewKpiOrder> getPayAblilities() {
		return payAblilities;
	}
	
	public void setPayAblilities(List<FinancialReviewKpiOrder> payAblilities) {
		this.payAblilities = payAblilities;
	}
	
	public List<FinancialReviewKpiOrder> getOperateAblilities() {
		return operateAblilities;
	}
	
	public void setOperateAblilities(List<FinancialReviewKpiOrder> operateAblilities) {
		this.operateAblilities = operateAblilities;
	}
	
	public List<FinancialReviewKpiOrder> getBenifitAblilities() {
		return benifitAblilities;
	}
	
	public void setBenifitAblilities(List<FinancialReviewKpiOrder> benifitAblilities) {
		this.benifitAblilities = benifitAblilities;
	}
	
	public List<FinancialReviewKpiOrder> getCashFlows() {
		return cashFlows;
	}
	
	public void setCashFlows(List<FinancialReviewKpiOrder> cashFlows) {
		this.cashFlows = cashFlows;
	}
	
	public FinancialReviewKpiOrder getFinancialHeader() {
		return financialHeader;
	}
	
	public void setFinancialHeader(FinancialReviewKpiOrder financialHeader) {
		this.financialHeader = financialHeader;
	}
	
	public FinancialReviewKpiOrder getFinancialDataHeader() {
		return financialDataHeader;
	}
	
	public void setFinancialDataHeader(FinancialReviewKpiOrder financialDataHeader) {
		this.financialDataHeader = financialDataHeader;
	}
	
	public FinancialReviewKpiOrder getPayAblilitieHeader() {
		return payAblilitieHeader;
	}
	
	public void setPayAblilitieHeader(FinancialReviewKpiOrder payAblilitieHeader) {
		this.payAblilitieHeader = payAblilitieHeader;
	}
	
	public FinancialReviewKpiOrder getOperateAblilitieHeader() {
		return operateAblilitieHeader;
	}
	
	public void setOperateAblilitieHeader(FinancialReviewKpiOrder operateAblilitieHeader) {
		this.operateAblilitieHeader = operateAblilitieHeader;
	}
	
	public FinancialReviewKpiOrder getBenifitAblilitieHeader() {
		return benifitAblilitieHeader;
	}
	
	public void setBenifitAblilitieHeader(FinancialReviewKpiOrder benifitAblilitieHeader) {
		this.benifitAblilitieHeader = benifitAblilitieHeader;
	}
	
	public FinancialReviewKpiOrder getCashFlowHeader() {
		return cashFlowHeader;
	}
	
	public void setCashFlowHeader(FinancialReviewKpiOrder cashFlowHeader) {
		this.cashFlowHeader = cashFlowHeader;
	}
	
	public FinancialReviewKpiOrder getProfitHeader() {
		return profitHeader;
	}
	
	public void setProfitHeader(FinancialReviewKpiOrder profitHeader) {
		this.profitHeader = profitHeader;
	}
	
	public List<FinancialReviewKpiOrder> getProfitTables() {
		return profitTables;
	}
	
	public void setProfitTables(List<FinancialReviewKpiOrder> profitTables) {
		this.profitTables = profitTables;
	}
	
	public FinancialReviewKpiOrder getFlowHeader() {
		return flowHeader;
	}
	
	public void setFlowHeader(FinancialReviewKpiOrder flowHeader) {
		this.flowHeader = flowHeader;
	}
	
	public List<FinancialReviewKpiOrder> getFlowTables() {
		return flowTables;
	}
	
	public void setFlowTables(List<FinancialReviewKpiOrder> flowTables) {
		this.flowTables = flowTables;
	}
	
	public boolean isInit() {
		return isInit;
	}
	
	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}
	
	public int getSubIndex() {
		return subIndex;
	}

	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<FInvestigationFinancialReviewKpiOrder> getAuditInfos() {
		return auditInfos;
	}
	
	public void setAuditInfos(List<FInvestigationFinancialReviewKpiOrder> auditInfos) {
		this.auditInfos = auditInfos;
	}
	
	public String getAmountUnit1() {
		return this.amountUnit1;
	}
	
	public void setAmountUnit1(String amountUnit1) {
		this.amountUnit1 = amountUnit1;
	}
	
	public String getAmountUnit2() {
		return this.amountUnit2;
	}
	
	public void setAmountUnit2(String amountUnit2) {
		this.amountUnit2 = amountUnit2;
	}
	
	public String getAmountUnit3() {
		return this.amountUnit3;
	}
	
	public void setAmountUnit3(String amountUnit3) {
		this.amountUnit3 = amountUnit3;
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
