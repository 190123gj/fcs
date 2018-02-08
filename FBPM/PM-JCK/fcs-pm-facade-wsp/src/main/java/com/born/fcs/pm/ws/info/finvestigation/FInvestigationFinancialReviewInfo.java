package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.AmountUnitEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 客户财务评价
 * 
 * @author lirz
 * 
 * 2016-3-11 上午10:07:10
 */
public class FInvestigationFinancialReviewInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -6225155273032122921L;
	
	private long FReviewId;
	private String importExcel; //是否导入了excel
	private AmountUnitEnum amountUnit1; //货币单位
	private AmountUnitEnum amountUnit2; //货币单位
	private AmountUnitEnum amountUnit3; //货币单位
	private String isAudited; //是否审计
	private String auditUnit; //审计单位
	private String auditSuggest; //审计意见类型
	//审计信息
	private List<FInvestigationFinancialReviewKpiInfo> auditInfos;
	//财务报表(资产负债表)
	private FinancialReviewKpiInfo financialHeader;
	private List<FinancialReviewKpiInfo> financialTables;
	private String auditSuggestExplain; //审计意见解释与说明
	//财务报表(利润表)
	private FinancialReviewKpiInfo profitHeader;
	private List<FinancialReviewKpiInfo> profitTables;
	//财务报表(现金流量表)
	private FinancialReviewKpiInfo flowHeader;
	private List<FinancialReviewKpiInfo> flowTables;
	//财务数据
	private FinancialReviewKpiInfo financialDataHeader;
	private List<FInvestigationFinancialReviewKpiInfo> financialDatas;
	//偿债能力
	private FinancialReviewKpiInfo payAblilitieHeader;
	private List<FinancialReviewKpiInfo> payAblilities;
	private String debtPayingAbility; //偿债能力解释与说明
	//运营能力
	private FinancialReviewKpiInfo operateAblilitieHeader;
	private List<FinancialReviewKpiInfo> operateAblilities;
	private String operatingAbility; //运营能力分析解释与说明
	//盈利能力
	private FinancialReviewKpiInfo benifitAblilitieHeader;
	private List<FinancialReviewKpiInfo> benifitAblilities;
	private String profitAbility; //盈利能力分析解释与说明
	//现金流
	private FinancialReviewKpiInfo cashFlowHeader;
	private List<FinancialReviewKpiInfo> cashFlows;
	private String cashFlowExplain; //现金流分析解释与说明
	private String assetQualityReview; //资产质量总体评价
	private Date rawAddTime;
	private Date rawUpdateTime;
	
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
	
	public List<FinancialReviewKpiInfo> getFinancialTables() {
		return financialTables;
	}
	
	public void setFinancialTables(List<FinancialReviewKpiInfo> financialTables) {
		this.financialTables = financialTables;
	}
	
	public List<FInvestigationFinancialReviewKpiInfo> getFinancialDatas() {
		return financialDatas;
	}
	
	public void setFinancialDatas(List<FInvestigationFinancialReviewKpiInfo> financialDatas) {
		this.financialDatas = financialDatas;
	}
	
	public List<FinancialReviewKpiInfo> getPayAblilities() {
		return payAblilities;
	}
	
	public void setPayAblilities(List<FinancialReviewKpiInfo> payAblilities) {
		this.payAblilities = payAblilities;
	}
	
	public List<FinancialReviewKpiInfo> getOperateAblilities() {
		return operateAblilities;
	}
	
	public void setOperateAblilities(List<FinancialReviewKpiInfo> operateAblilities) {
		this.operateAblilities = operateAblilities;
	}
	
	public List<FinancialReviewKpiInfo> getBenifitAblilities() {
		return benifitAblilities;
	}
	
	public void setBenifitAblilities(List<FinancialReviewKpiInfo> benifitAblilities) {
		this.benifitAblilities = benifitAblilities;
	}
	
	public List<FinancialReviewKpiInfo> getCashFlows() {
		return cashFlows;
	}
	
	public void setCashFlows(List<FinancialReviewKpiInfo> cashFlows) {
		this.cashFlows = cashFlows;
	}
	
	public FinancialReviewKpiInfo getFinancialHeader() {
		return financialHeader;
	}
	
	public void setFinancialHeader(FinancialReviewKpiInfo financialHeader) {
		this.financialHeader = financialHeader;
	}
	
	public FinancialReviewKpiInfo getFinancialDataHeader() {
		return financialDataHeader;
	}
	
	public void setFinancialDataHeader(FinancialReviewKpiInfo financialDataHeader) {
		this.financialDataHeader = financialDataHeader;
	}
	
	public FinancialReviewKpiInfo getPayAblilitieHeader() {
		return payAblilitieHeader;
	}
	
	public void setPayAblilitieHeader(FinancialReviewKpiInfo payAblilitieHeader) {
		this.payAblilitieHeader = payAblilitieHeader;
	}
	
	public FinancialReviewKpiInfo getOperateAblilitieHeader() {
		return operateAblilitieHeader;
	}
	
	public void setOperateAblilitieHeader(FinancialReviewKpiInfo operateAblilitieHeader) {
		this.operateAblilitieHeader = operateAblilitieHeader;
	}
	
	public FinancialReviewKpiInfo getBenifitAblilitieHeader() {
		return benifitAblilitieHeader;
	}
	
	public void setBenifitAblilitieHeader(FinancialReviewKpiInfo benifitAblilitieHeader) {
		this.benifitAblilitieHeader = benifitAblilitieHeader;
	}
	
	public FinancialReviewKpiInfo getCashFlowHeader() {
		return cashFlowHeader;
	}
	
	public void setCashFlowHeader(FinancialReviewKpiInfo cashFlowHeader) {
		this.cashFlowHeader = cashFlowHeader;
	}
	
	public FinancialReviewKpiInfo getProfitHeader() {
		return profitHeader;
	}
	
	public void setProfitHeader(FinancialReviewKpiInfo profitHeader) {
		this.profitHeader = profitHeader;
	}
	
	public List<FinancialReviewKpiInfo> getProfitTables() {
		return profitTables;
	}
	
	public void setProfitTables(List<FinancialReviewKpiInfo> profitTables) {
		this.profitTables = profitTables;
	}
	
	public FinancialReviewKpiInfo getFlowHeader() {
		return flowHeader;
	}
	
	public void setFlowHeader(FinancialReviewKpiInfo flowHeader) {
		this.flowHeader = flowHeader;
	}
	
	public List<FinancialReviewKpiInfo> getFlowTables() {
		return flowTables;
	}
	
	public void setFlowTables(List<FinancialReviewKpiInfo> flowTables) {
		this.flowTables = flowTables;
	}
	
	public List<FInvestigationFinancialReviewKpiInfo> getAuditInfos() {
		return auditInfos;
	}
	
	public void setAuditInfos(List<FInvestigationFinancialReviewKpiInfo> auditInfos) {
		this.auditInfos = auditInfos;
	}
	
	public AmountUnitEnum getAmountUnit1() {
		return this.amountUnit1;
	}
	
	public void setAmountUnit1(AmountUnitEnum amountUnit1) {
		this.amountUnit1 = amountUnit1;
	}
	
	public AmountUnitEnum getAmountUnit2() {
		return this.amountUnit2;
	}
	
	public void setAmountUnit2(AmountUnitEnum amountUnit2) {
		this.amountUnit2 = amountUnit2;
	}
	
	public AmountUnitEnum getAmountUnit3() {
		return this.amountUnit3;
	}
	
	public void setAmountUnit3(AmountUnitEnum amountUnit3) {
		this.amountUnit3 = amountUnit3;
	}
	
}
