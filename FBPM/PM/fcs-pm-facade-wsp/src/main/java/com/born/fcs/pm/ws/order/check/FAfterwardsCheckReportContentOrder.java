/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.check;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.born.fcs.pm.ws.order.financialkpi.FinancialKpiOrder;

/**
 * 
 * 监管内容order
 * 
 * @author lirz
 * 
 * 2016-6-4 下午3:26:47
 */
public class FAfterwardsCheckReportContentOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -5550038980011500193L;
	
	private long contentId;
	private String importExcel; //是否导入了财务报表
	private String loanLimitDataCommon; //贷款期限集中度(公共)
	private String loanLimitData; //贷款期限集中度
	private String loanAmountDataCommon; //贷款金额集中度(公共)
	private String loanAmountData; //贷款金额集中度
	private String date1;
	private String date2;
	private String date3;
	private String amountUnit1;
	private String amountUnit2;
	private String amountUnit3;
	
	private String useWayConditions;
	private String projectFinishDesc;
	private String incomeCheckDesc;
	private String managementMatters;
	private String decisionWay;
	private String counterCheck;
	private String relatedEnterprise;
	private String otherExplain;
	private String analysisConclusion;
	private String contentRemark1; //备注1
	private String contentRemark2; //备注2
	private String contentRemark3; //备注3
	
	private List<FinancialKpiOrder> financials; //资产负债
	private List<FinancialKpiOrder> profits; //利润分配
	private List<FinancialKpiOrder> flows; //现金流量
	
	private List<FAfterwardsCheckReportFinancialOrder> capitals; //资产
	private List<FAfterwardsCheckReportFinancialOrder> debts; //负债
	private List<FAfterwardsCheckReportFinancialOrder> fothers; //其它
	
	private List<FAfterwardsCheckLoanOrder> loans; //银行贷款及其他融资
	private List<FAfterwardsCheckReportCreditOrder> credits; //信用状况
	
	private List<FAfterwardsCheckReportItemOrder> incomes; //核实收入(企业收入核实情况工作底稿)
	private List<FAfterwardsCheckReportItemOrder> costs; //核实成本(企业成本核实情况工作底稿)
	private List<FAfterwardsCheckReportItemOrder> counters; //反担保措施
	private List<FAfterwardsCheckReportItemOrder> others; //其他重要事项检查
	private List<FAfterwardsCheckReportItemOrder> warnings; //预警信号或关注事项
	
	private List<FAfterwardsCheckReportIncomeOrder> checkIncomes; //企业收入核实情况工作底稿
	
	private List<FAfterwardsCheckReportProjectSimpleOrder> projects; //开发项目完成情况检查（项目较多的列举前五户）
	
	private List<FinancialKpiOrder> contractProjects; //主要承包项目结构
	private List<FAfterwardsCheckReportItemOrder> costConsists1; //成本机构及变动情况1
	private List<FAfterwardsCheckReportItemOrder> costConsists2; //成本机构及变动情况2
	private List<FAfterwardsCheckReportItemOrder> costConsists3; //成本机构及变动情况3
	private List<FAfterwardsCheckReportCapitalOrder> subContractors; //项目分包商
	private List<FAfterwardsCheckReportItemOrder> inventories1; //存货1
	private List<FAfterwardsCheckReportItemOrder> inventories2; //存货2
	private List<FAfterwardsCheckReportItemOrder> inventories3; //存货3
	private List<FAfterwardsCheckReportCapitalOrder> successfulProjects; //已中标未开工项目情况表
	
	private List<FinancialKpiOrder> holderBackgrounds; //股东背景
	private List<FinancialKpiOrder> loanIndusties; //贷款行业
	private List<FinancialKpiOrder> creditStructures; //贷款信用结构
	private List<FinancialKpiOrder> loanTimeLimits; //贷款期限集中度
	private List<FinancialKpiOrder> loanAmounts; //贷款金额集中度
	private List<FinancialKpiOrder> averageCapitals; //平均资产情况调查
	private List<FinancialKpiOrder> loanQualityLevels; //贷款质量调查-五级分类情况
	private List<FinancialKpiOrder> badLoans; //贷款质量调查-不良贷款情况
	
	private List<FAfterwardsCheckReportCapitalOrder> incomeDetails; //企业收入调查工作底稿
	private List<FAfterwardsCheckReportCapitalOrder> projectings; //在建项目情况表
	private List<FAfterwardsCheckReportCapitalOrder> debtDetails; //企业资产负债划转明细
	private List<FAfterwardsCheckReportCapitalOrder> creditDetails; //信用状况
	private List<FAfterwardsCheckReportCapitalOrder> tenCustomers; //前十大客户
	private List<FAfterwardsCheckReportCapitalOrder> holderLoans; //股东担保贷款（或关联企业贷款）明细
	private List<FAfterwardsCheckReportCapitalOrder> backOnTimes; //按期收回情况
	private List<FAfterwardsCheckReportFinancialOrder> subjects; //主要科目调查工作底稿
	
	private List<FAfterwardsNotCollectedDataOrder> notCollectes; //未收集数据说明
	
	public long getContentId() {
		return this.contentId;
	}
	
	public void setContentId(long contentId) {
		this.contentId = contentId;
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
	
	public String getContentRemark1() {
		return this.contentRemark1;
	}

	public void setContentRemark1(String contentRemark1) {
		this.contentRemark1 = contentRemark1;
	}

	public String getContentRemark2() {
		return this.contentRemark2;
	}

	public void setContentRemark2(String contentRemark2) {
		this.contentRemark2 = contentRemark2;
	}

	public String getContentRemark3() {
		return this.contentRemark3;
	}

	public void setContentRemark3(String contentRemark3) {
		this.contentRemark3 = contentRemark3;
	}

	public List<FinancialKpiOrder> getFinancials() {
		return financials;
	}
	
	public void setFinancials(List<FinancialKpiOrder> financials) {
		this.financials = financials;
	}
	
	public List<FinancialKpiOrder> getProfits() {
		return profits;
	}
	
	public void setProfits(List<FinancialKpiOrder> profits) {
		this.profits = profits;
	}
	
	public List<FinancialKpiOrder> getFlows() {
		return flows;
	}
	
	public void setFlows(List<FinancialKpiOrder> flows) {
		this.flows = flows;
	}
	
	public List<FAfterwardsCheckReportFinancialOrder> getCapitals() {
		return capitals;
	}
	
	public void setCapitals(List<FAfterwardsCheckReportFinancialOrder> capitals) {
		this.capitals = capitals;
	}
	
	public List<FAfterwardsCheckReportFinancialOrder> getDebts() {
		return debts;
	}
	
	public void setDebts(List<FAfterwardsCheckReportFinancialOrder> debts) {
		this.debts = debts;
	}
	
	public List<FAfterwardsCheckReportFinancialOrder> getFothers() {
		return fothers;
	}
	
	public void setFothers(List<FAfterwardsCheckReportFinancialOrder> fothers) {
		this.fothers = fothers;
	}
	
	public List<FAfterwardsCheckLoanOrder> getLoans() {
		return loans;
	}
	
	public void setLoans(List<FAfterwardsCheckLoanOrder> loans) {
		this.loans = loans;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getIncomes() {
		return incomes;
	}
	
	public void setIncomes(List<FAfterwardsCheckReportItemOrder> incomes) {
		this.incomes = incomes;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getCosts() {
		return costs;
	}
	
	public void setCosts(List<FAfterwardsCheckReportItemOrder> costs) {
		this.costs = costs;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getCounters() {
		return counters;
	}
	
	public void setCounters(List<FAfterwardsCheckReportItemOrder> counters) {
		this.counters = counters;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getOthers() {
		return others;
	}
	
	public void setOthers(List<FAfterwardsCheckReportItemOrder> others) {
		this.others = others;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getWarnings() {
		return warnings;
	}
	
	public void setWarnings(List<FAfterwardsCheckReportItemOrder> warnings) {
		this.warnings = warnings;
	}
	
	public List<FAfterwardsCheckReportIncomeOrder> getCheckIncomes() {
		return checkIncomes;
	}
	
	public void setCheckIncomes(List<FAfterwardsCheckReportIncomeOrder> checkIncomes) {
		this.checkIncomes = checkIncomes;
	}
	
	public List<FAfterwardsCheckReportProjectSimpleOrder> getProjects() {
		return projects;
	}
	
	public void setProjects(List<FAfterwardsCheckReportProjectSimpleOrder> projects) {
		this.projects = projects;
	}
	
	public List<FinancialKpiOrder> getContractProjects() {
		return contractProjects;
	}
	
	public void setContractProjects(List<FinancialKpiOrder> contractProjects) {
		this.contractProjects = contractProjects;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getCostConsists1() {
		return costConsists1;
	}
	
	public void setCostConsists1(List<FAfterwardsCheckReportItemOrder> costConsists1) {
		this.costConsists1 = costConsists1;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getCostConsists2() {
		return costConsists2;
	}
	
	public void setCostConsists2(List<FAfterwardsCheckReportItemOrder> costConsists2) {
		this.costConsists2 = costConsists2;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getCostConsists3() {
		return costConsists3;
	}
	
	public void setCostConsists3(List<FAfterwardsCheckReportItemOrder> costConsists3) {
		this.costConsists3 = costConsists3;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getInventories1() {
		return inventories1;
	}
	
	public void setInventories1(List<FAfterwardsCheckReportItemOrder> inventories1) {
		this.inventories1 = inventories1;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getInventories2() {
		return inventories2;
	}
	
	public void setInventories2(List<FAfterwardsCheckReportItemOrder> inventories2) {
		this.inventories2 = inventories2;
	}
	
	public List<FAfterwardsCheckReportItemOrder> getInventories3() {
		return inventories3;
	}
	
	public void setInventories3(List<FAfterwardsCheckReportItemOrder> inventories3) {
		this.inventories3 = inventories3;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getSuccessfulProjects() {
		return successfulProjects;
	}
	
	public void setSuccessfulProjects(List<FAfterwardsCheckReportCapitalOrder> successfulProjects) {
		this.successfulProjects = successfulProjects;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getSubContractors() {
		return subContractors;
	}
	
	public void setSubContractors(List<FAfterwardsCheckReportCapitalOrder> subContractors) {
		this.subContractors = subContractors;
	}
	
	public List<FinancialKpiOrder> getHolderBackgrounds() {
		return holderBackgrounds;
	}
	
	public void setHolderBackgrounds(List<FinancialKpiOrder> holderBackgrounds) {
		this.holderBackgrounds = holderBackgrounds;
	}
	
	public List<FinancialKpiOrder> getLoanIndusties() {
		return loanIndusties;
	}
	
	public void setLoanIndusties(List<FinancialKpiOrder> loanIndusties) {
		this.loanIndusties = loanIndusties;
	}
	
	public List<FinancialKpiOrder> getCreditStructures() {
		return creditStructures;
	}
	
	public void setCreditStructures(List<FinancialKpiOrder> creditStructures) {
		this.creditStructures = creditStructures;
	}
	
	public List<FinancialKpiOrder> getLoanTimeLimits() {
		return loanTimeLimits;
	}
	
	public void setLoanTimeLimits(List<FinancialKpiOrder> loanTimeLimits) {
		this.loanTimeLimits = loanTimeLimits;
	}
	
	public List<FinancialKpiOrder> getLoanAmounts() {
		return loanAmounts;
	}
	
	public void setLoanAmounts(List<FinancialKpiOrder> loanAmounts) {
		this.loanAmounts = loanAmounts;
	}
	
	public List<FinancialKpiOrder> getAverageCapitals() {
		return averageCapitals;
	}
	
	public void setAverageCapitals(List<FinancialKpiOrder> averageCapitals) {
		this.averageCapitals = averageCapitals;
	}
	
	public List<FinancialKpiOrder> getLoanQualityLevels() {
		return loanQualityLevels;
	}
	
	public void setLoanQualityLevels(List<FinancialKpiOrder> loanQualityLevels) {
		this.loanQualityLevels = loanQualityLevels;
	}
	
	public List<FinancialKpiOrder> getBadLoans() {
		return badLoans;
	}
	
	public void setBadLoans(List<FinancialKpiOrder> badLoans) {
		this.badLoans = badLoans;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getIncomeDetails() {
		return incomeDetails;
	}
	
	public void setIncomeDetails(List<FAfterwardsCheckReportCapitalOrder> incomeDetails) {
		this.incomeDetails = incomeDetails;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getProjectings() {
		return projectings;
	}
	
	public void setProjectings(List<FAfterwardsCheckReportCapitalOrder> projectings) {
		this.projectings = projectings;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getDebtDetails() {
		return debtDetails;
	}
	
	public void setDebtDetails(List<FAfterwardsCheckReportCapitalOrder> debtDetails) {
		this.debtDetails = debtDetails;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getCreditDetails() {
		return creditDetails;
	}
	
	public void setCreditDetails(List<FAfterwardsCheckReportCapitalOrder> creditDetails) {
		this.creditDetails = creditDetails;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getTenCustomers() {
		return tenCustomers;
	}
	
	public void setTenCustomers(List<FAfterwardsCheckReportCapitalOrder> tenCustomers) {
		this.tenCustomers = tenCustomers;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getHolderLoans() {
		return holderLoans;
	}
	
	public void setHolderLoans(List<FAfterwardsCheckReportCapitalOrder> holderLoans) {
		this.holderLoans = holderLoans;
	}
	
	public List<FAfterwardsCheckReportCapitalOrder> getBackOnTimes() {
		return backOnTimes;
	}
	
	public void setBackOnTimes(List<FAfterwardsCheckReportCapitalOrder> backOnTimes) {
		this.backOnTimes = backOnTimes;
	}

	public List<FAfterwardsCheckReportFinancialOrder> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<FAfterwardsCheckReportFinancialOrder> subjects) {
		this.subjects = subjects;
	}

	public String getImportExcel() {
		return importExcel;
	}

	public void setImportExcel(String importExcel) {
		this.importExcel = importExcel;
	}

	public String getLoanLimitData() {
		return loanLimitData;
	}

	public void setLoanLimitData(String loanLimitData) {
		this.loanLimitData = loanLimitData;
	}

	public String getLoanAmountData() {
		return loanAmountData;
	}

	public void setLoanAmountData(String loanAmountData) {
		this.loanAmountData = loanAmountData;
	}

	public String getLoanLimitDataCommon() {
		return loanLimitDataCommon;
	}

	public void setLoanLimitDataCommon(String loanLimitDataCommon) {
		this.loanLimitDataCommon = loanLimitDataCommon;
	}

	public String getLoanAmountDataCommon() {
		return loanAmountDataCommon;
	}

	public void setLoanAmountDataCommon(String loanAmountDataCommon) {
		this.loanAmountDataCommon = loanAmountDataCommon;
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

	public List<FAfterwardsNotCollectedDataOrder> getNotCollectes() {
		return this.notCollectes;
	}

	public void setNotCollectes(List<FAfterwardsNotCollectedDataOrder> notCollectes) {
		this.notCollectes = notCollectes;
	}

	public List<FAfterwardsCheckReportCreditOrder> getCredits() {
		return this.credits;
	}

	public void setCredits(List<FAfterwardsCheckReportCreditOrder> credits) {
		this.credits = credits;
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
