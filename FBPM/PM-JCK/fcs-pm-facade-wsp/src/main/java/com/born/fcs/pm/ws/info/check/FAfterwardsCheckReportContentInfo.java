/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.check;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.AmountUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;

/**
 * 
 * 监管内容
 * 
 * @author lirz
 * 
 * 2016-6-4 下午4:13:24
 */
public class FAfterwardsCheckReportContentInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8421711783796581066L;
	
	private long contentId;
	private long formId;
	private String projectCode;
	private String importExcel; //是否导入了财务报表
	private String loanLimitDataCommon;
	private String loanLimitData;
	private String loanAmountDataCommon;
	private String loanAmountData;
	private String date1;
	private String date2;
	private String date3;
	private AmountUnitEnum amountUnit1;
	private AmountUnitEnum amountUnit2;
	private AmountUnitEnum amountUnit3;
	
	//（一）授信的用途、还息及纳税检查
	private String useWayConditions;
	//(二)开发项目完成情况检查|银行贷款及其他融资
	private String projectFinishDesc;
	//(二)企业收入调查工作底稿|企业成本核实情况工作底稿|业务流程
	private String incomeCheckDesc;
	//(三)重大经营管理事项检查|业务流程
	private String managementMatters;
	//(三)审贷会的组成及决议方式
	private String decisionWay;
	//(四)反担保措施检查
	private String counterCheck;
	//(五)关联企业检查情况
	private String relatedEnterprise;
	//(八)其他需说明的事项
	private String otherExplain;
	//(九)分析及结论（客户对外担保风险较大的需对被担保方进行分析）
	private String analysisConclusion;
	
	private List<FinancialKpiInfo> financials; //资产负债
	private List<FinancialKpiInfo> profits; //利润分配
	private List<FinancialKpiInfo> flows; //现金流量
	
	private List<AfterwardsCheckReportFinancialInfo> capitals; //资产
	private List<AfterwardsCheckReportFinancialInfo> debts; //负债
	private List<AfterwardsCheckReportFinancialInfo> fothers; //其它
	
	private List<FAfterwardsCheckLoanInfo> loans; //银行贷款及其他融资
	
	private List<FAfterwardsCheckReportItemInfo> incomes; //核实收入(企业收入核实情况工作底稿)
	private List<FAfterwardsCheckReportItemInfo> costs; //核实成本(企业成本核实情况工作底稿)
	private List<AfterwardsCheckReportItemInfo> costInfos; //核实成本(企业成本核实情况工作底稿)
	private List<FAfterwardsCheckReportItemInfo> counters; //反担保措施
	private List<FAfterwardsCheckReportItemInfo> others; //其他重要事项检查
	private List<AfterwardsCheckReportItemInfo> otherInfos; //其他重要事项检查
	private List<FAfterwardsCheckReportItemInfo> warnings; //预警信号或关注事项
	
	private List<FAfterwardsCheckReportIncomeInfo> checkIncomes; //企业收入核实情况工作底稿
	
	private List<FAfterwardsCheckReportProjectSimpleInfo> projects; //开发项目完成情况检查（项目较多的列举前五户）
	
	private List<FinancialKpiInfo> contractProjects; //主要承包项目结构
	private List<FAfterwardsCheckReportItemInfo> costConsists1; //成本机构及变动情况1
	private List<FAfterwardsCheckReportItemInfo> costConsists2; //成本机构及变动情况2
	private List<FAfterwardsCheckReportItemInfo> costConsists3; //成本机构及变动情况3
	private List<FAfterwardsCheckReportCapitalInfo> subContractors; //项目分包商
	private List<FAfterwardsCheckReportItemInfo> inventories1; //存货1
	private List<FAfterwardsCheckReportItemInfo> inventories2; //存货2
	private List<FAfterwardsCheckReportItemInfo> inventories3; //存货3
	private List<FAfterwardsCheckReportCapitalInfo> successfulProjects; //已中标未开工项目情况表

	private List<FinancialKpiInfo> holderBackgrounds; //股东背景
	private List<FinancialKpiInfo> loanIndusties; //贷款行业
	private List<FinancialKpiInfo> creditStructures; //贷款信用结构
	private List<FinancialKpiInfo> loanTimeLimits; //贷款期限集中度
	private List<FinancialKpiInfo> loanAmounts; //贷款金额集中度
	private List<FinancialKpiInfo> averageCapitals; //平均资产情况调查
	private List<FinancialKpiInfo> loanQualityLevels; //贷款质量调查-五级分类情况
	private List<FinancialKpiInfo> badLoans; //贷款质量调查-不良贷款情况
	
	private List<FAfterwardsCheckReportCapitalInfo> incomeDetails; //企业收入调查工作底稿
	private List<FAfterwardsCheckReportCapitalInfo> projectings; //在建项目情况表
	private List<FAfterwardsCheckReportCapitalInfo> debtDetails; //企业资产负债划转明细
	private List<FAfterwardsCheckReportCapitalInfo> creditDetails; //信用状况
	private List<FAfterwardsCheckReportCapitalInfo> tenCustomers; //前十大客户
	private List<FAfterwardsCheckReportCapitalInfo> holderLoans; //股东担保贷款（或关联企业贷款）明细
	private List<FAfterwardsCheckReportCapitalInfo> backOnTimes; //按期收回情况
	private List<AfterwardsCheckReportFinancialInfo> subjects; //科目工作稿底

	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getContentId() {
		return contentId;
	}
	
	public void setContentId(long contentId) {
		this.contentId = contentId;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getUseWayConditions() {
		return useWayConditions;
	}
	
	public void setUseWayConditions(String useWayConditions) {
		this.useWayConditions = useWayConditions;
	}
	
	public String getProjectFinishDesc() {
		return projectFinishDesc;
	}
	
	public void setProjectFinishDesc(String projectFinishDesc) {
		this.projectFinishDesc = projectFinishDesc;
	}
	
	public String getIncomeCheckDesc() {
		return incomeCheckDesc;
	}
	
	public void setIncomeCheckDesc(String incomeCheckDesc) {
		this.incomeCheckDesc = incomeCheckDesc;
	}
	
	public String getManagementMatters() {
		return managementMatters;
	}
	
	public void setManagementMatters(String managementMatters) {
		this.managementMatters = managementMatters;
	}
	
	public String getDecisionWay() {
		return decisionWay;
	}
	
	public void setDecisionWay(String decisionWay) {
		this.decisionWay = decisionWay;
	}
	
	public String getCounterCheck() {
		return counterCheck;
	}
	
	public void setCounterCheck(String counterCheck) {
		this.counterCheck = counterCheck;
	}
	
	public String getRelatedEnterprise() {
		return relatedEnterprise;
	}
	
	public void setRelatedEnterprise(String relatedEnterprise) {
		this.relatedEnterprise = relatedEnterprise;
	}
	
	public String getOtherExplain() {
		return otherExplain;
	}
	
	public void setOtherExplain(String otherExplain) {
		this.otherExplain = otherExplain;
	}
	
	public String getAnalysisConclusion() {
		return analysisConclusion;
	}
	
	public void setAnalysisConclusion(String analysisConclusion) {
		this.analysisConclusion = analysisConclusion;
	}
	
	public List<FinancialKpiInfo> getFinancials() {
		return financials;
	}
	
	public void setFinancials(List<FinancialKpiInfo> financials) {
		this.financials = financials;
	}
	
	public List<FinancialKpiInfo> getProfits() {
		return profits;
	}
	
	public void setProfits(List<FinancialKpiInfo> profits) {
		this.profits = profits;
	}
	
	public List<FinancialKpiInfo> getFlows() {
		return flows;
	}
	
	public void setFlows(List<FinancialKpiInfo> flows) {
		this.flows = flows;
	}
	
	public List<AfterwardsCheckReportFinancialInfo> getCapitals() {
		return capitals;
	}
	
	public void setCapitals(List<AfterwardsCheckReportFinancialInfo> capitals) {
		this.capitals = capitals;
	}
	
	public List<AfterwardsCheckReportFinancialInfo> getDebts() {
		return debts;
	}
	
	public void setDebts(List<AfterwardsCheckReportFinancialInfo> debts) {
		this.debts = debts;
	}
	
	public List<AfterwardsCheckReportFinancialInfo> getFothers() {
		return fothers;
	}
	
	public void setFothers(List<AfterwardsCheckReportFinancialInfo> fothers) {
		this.fothers = fothers;
	}
	
	public List<FAfterwardsCheckLoanInfo> getLoans() {
		return loans;
	}
	
	public void setLoans(List<FAfterwardsCheckLoanInfo> loans) {
		this.loans = loans;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getIncomes() {
		return incomes;
	}
	
	public void setIncomes(List<FAfterwardsCheckReportItemInfo> incomes) {
		this.incomes = incomes;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getCosts() {
		return costs;
	}
	
	public void setCosts(List<FAfterwardsCheckReportItemInfo> costs) {
		this.costs = costs;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getCounters() {
		return counters;
	}
	
	public void setCounters(List<FAfterwardsCheckReportItemInfo> counters) {
		this.counters = counters;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getOthers() {
		return others;
	}
	
	public void setOthers(List<FAfterwardsCheckReportItemInfo> others) {
		this.others = others;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getWarnings() {
		return warnings;
	}
	
	public void setWarnings(List<FAfterwardsCheckReportItemInfo> warnings) {
		this.warnings = warnings;
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
	
	public List<FAfterwardsCheckReportIncomeInfo> getCheckIncomes() {
		return checkIncomes;
	}

	public void setCheckIncomes(List<FAfterwardsCheckReportIncomeInfo> checkIncomes) {
		this.checkIncomes = checkIncomes;
	}

	public List<AfterwardsCheckReportItemInfo> getCostInfos() {
		return costInfos;
	}

	public void setCostInfos(List<AfterwardsCheckReportItemInfo> costInfos) {
		this.costInfos = costInfos;
	}

	public List<AfterwardsCheckReportItemInfo> getOtherInfos() {
		return otherInfos;
	}

	public void setOtherInfos(List<AfterwardsCheckReportItemInfo> otherInfos) {
		this.otherInfos = otherInfos;
	}

	public List<FAfterwardsCheckReportProjectSimpleInfo> getProjects() {
		return projects;
	}

	public void setProjects(List<FAfterwardsCheckReportProjectSimpleInfo> projects) {
		this.projects = projects;
	}

	public List<FinancialKpiInfo> getContractProjects() {
		return contractProjects;
	}

	public void setContractProjects(List<FinancialKpiInfo> contractProjects) {
		this.contractProjects = contractProjects;
	}

	public List<FAfterwardsCheckReportItemInfo> getCostConsists1() {
		return costConsists1;
	}

	public void setCostConsists1(List<FAfterwardsCheckReportItemInfo> costConsists1) {
		this.costConsists1 = costConsists1;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getCostConsists2() {
		return costConsists2;
	}
	
	public void setCostConsists2(List<FAfterwardsCheckReportItemInfo> costConsists2) {
		this.costConsists2 = costConsists2;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getCostConsists3() {
		return costConsists3;
	}
	
	public void setCostConsists3(List<FAfterwardsCheckReportItemInfo> costConsists3) {
		this.costConsists3 = costConsists3;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getSubContractors() {
		return subContractors;
	}

	public void setSubContractors(List<FAfterwardsCheckReportCapitalInfo> subContractors) {
		this.subContractors = subContractors;
	}

	public List<FAfterwardsCheckReportItemInfo> getInventories1() {
		return inventories1;
	}

	public void setInventories1(List<FAfterwardsCheckReportItemInfo> inventories1) {
		this.inventories1 = inventories1;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getInventories2() {
		return inventories2;
	}
	
	public void setInventories2(List<FAfterwardsCheckReportItemInfo> inventories2) {
		this.inventories2 = inventories2;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getInventories3() {
		return inventories3;
	}
	
	public void setInventories3(List<FAfterwardsCheckReportItemInfo> inventories3) {
		this.inventories3 = inventories3;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getSuccessfulProjects() {
		return successfulProjects;
	}

	public void setSuccessfulProjects(List<FAfterwardsCheckReportCapitalInfo> successfulProjects) {
		this.successfulProjects = successfulProjects;
	}

	public List<FinancialKpiInfo> getHolderBackgrounds() {
		return holderBackgrounds;
	}

	public void setHolderBackgrounds(List<FinancialKpiInfo> holderBackgrounds) {
		this.holderBackgrounds = holderBackgrounds;
	}

	public List<FinancialKpiInfo> getLoanIndusties() {
		return loanIndusties;
	}

	public void setLoanIndusties(List<FinancialKpiInfo> loanIndusties) {
		this.loanIndusties = loanIndusties;
	}

	public List<FinancialKpiInfo> getCreditStructures() {
		return creditStructures;
	}

	public void setCreditStructures(List<FinancialKpiInfo> creditStructures) {
		this.creditStructures = creditStructures;
	}

	public List<FinancialKpiInfo> getLoanTimeLimits() {
		return loanTimeLimits;
	}

	public void setLoanTimeLimits(List<FinancialKpiInfo> loanTimeLimits) {
		this.loanTimeLimits = loanTimeLimits;
	}

	public List<FinancialKpiInfo> getLoanAmounts() {
		return loanAmounts;
	}

	public void setLoanAmounts(List<FinancialKpiInfo> loanAmounts) {
		this.loanAmounts = loanAmounts;
	}

	public List<FinancialKpiInfo> getAverageCapitals() {
		return averageCapitals;
	}

	public void setAverageCapitals(List<FinancialKpiInfo> averageCapitals) {
		this.averageCapitals = averageCapitals;
	}

	public List<FinancialKpiInfo> getLoanQualityLevels() {
		return loanQualityLevels;
	}

	public void setLoanQualityLevels(List<FinancialKpiInfo> loanQualityLevels) {
		this.loanQualityLevels = loanQualityLevels;
	}

	public List<FinancialKpiInfo> getBadLoans() {
		return badLoans;
	}

	public void setBadLoans(List<FinancialKpiInfo> badLoans) {
		this.badLoans = badLoans;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getIncomeDetails() {
		return incomeDetails;
	}

	public void setIncomeDetails(List<FAfterwardsCheckReportCapitalInfo> incomeDetails) {
		this.incomeDetails = incomeDetails;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getProjectings() {
		return projectings;
	}

	public void setProjectings(List<FAfterwardsCheckReportCapitalInfo> projectings) {
		this.projectings = projectings;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getDebtDetails() {
		return debtDetails;
	}

	public void setDebtDetails(List<FAfterwardsCheckReportCapitalInfo> debtDetails) {
		this.debtDetails = debtDetails;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getCreditDetails() {
		return creditDetails;
	}

	public void setCreditDetails(List<FAfterwardsCheckReportCapitalInfo> creditDetails) {
		this.creditDetails = creditDetails;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getTenCustomers() {
		return tenCustomers;
	}

	public void setTenCustomers(List<FAfterwardsCheckReportCapitalInfo> tenCustomers) {
		this.tenCustomers = tenCustomers;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getHolderLoans() {
		return holderLoans;
	}

	public void setHolderLoans(List<FAfterwardsCheckReportCapitalInfo> holderLoans) {
		this.holderLoans = holderLoans;
	}

	public List<FAfterwardsCheckReportCapitalInfo> getBackOnTimes() {
		return backOnTimes;
	}

	public void setBackOnTimes(List<FAfterwardsCheckReportCapitalInfo> backOnTimes) {
		this.backOnTimes = backOnTimes;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public List<AfterwardsCheckReportFinancialInfo> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<AfterwardsCheckReportFinancialInfo> subjects) {
		this.subjects = subjects;
	}

	public String getImportExcel() {
		return importExcel;
	}

	public void setImportExcel(String importExcel) {
		this.importExcel = importExcel;
	}

	public String getLoanLimitDataCommon() {
		return loanLimitDataCommon;
	}

	public void setLoanLimitDataCommon(String loanLimitDataCommon) {
		this.loanLimitDataCommon = loanLimitDataCommon;
	}

	public String getLoanLimitData() {
		return loanLimitData;
	}

	public void setLoanLimitData(String loanLimitData) {
		this.loanLimitData = loanLimitData;
	}

	public String getLoanAmountDataCommon() {
		return loanAmountDataCommon;
	}

	public void setLoanAmountDataCommon(String loanAmountDataCommon) {
		this.loanAmountDataCommon = loanAmountDataCommon;
	}

	public String getLoanAmountData() {
		return loanAmountData;
	}

	public void setLoanAmountData(String loanAmountData) {
		this.loanAmountData = loanAmountData;
	}

	public String getDate1() {
		return this.date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return this.date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getDate3() {
		return this.date3;
	}

	public void setDate3(String date3) {
		this.date3 = date3;
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
