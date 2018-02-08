package com.born.fcs.crm.ws.service.order;

import java.util.List;

import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.crm.ws.service.info.EvaluetingInfo;
import com.born.fcs.crm.ws.service.info.FinancialSetInfo;
import com.born.fcs.crm.ws.service.info.ListDataInfo;

/**
 * 批量更新与添加
 * **/
public class ListOrder {
	/** 旗下公司信息 */
	List<PersonalCompanyOrder> reqList;
	/** 获得的资质证书 */
	List<CompanyQualificationInfo> companyQualification;
	/** 公司股权结构 */
	List<CompanyOwnershipStructureInfo> companyOwnershipStructure;
	/** 一般企业评价配置 */
	List<EvaluatingBaseSetOrder> evaluatingBaseSet;
	/** 集合类信息通用存储 */
	List<ListDataInfo> listData;
	/** 集合类信息通用存储 */
	List<ListDataInfo> listData1;
	/** 集合类信息通用存储 */
	List<ListDataInfo> listData2;
	/** 指标评分提交 */
	List<EvaluetingInfo> evaluetingInfo;
	/** 财务指标配置 */
	List<FinancialSetInfo> financialSetInfo;
	/** 财务指标配置-特殊配置 */
	List<FinancialSetInfo> financialSetInfoTs;
	
	public List<CompanyQualificationInfo> getCompanyQualification() {
		return this.companyQualification;
	}
	
	public void setCompanyQualification(List<CompanyQualificationInfo> companyQualification) {
		this.companyQualification = companyQualification;
	}
	
	public List<CompanyOwnershipStructureInfo> getCompanyOwnershipStructure() {
		return this.companyOwnershipStructure;
	}
	
	public void setCompanyOwnershipStructure(	List<CompanyOwnershipStructureInfo> companyOwnershipStructure) {
		this.companyOwnershipStructure = companyOwnershipStructure;
	}
	
	public List<EvaluatingBaseSetOrder> getEvaluatingBaseSet() {
		return this.evaluatingBaseSet;
	}
	
	public void setEvaluatingBaseSet(List<EvaluatingBaseSetOrder> evaluatingBaseSet) {
		this.evaluatingBaseSet = evaluatingBaseSet;
	}
	
	public List<ListDataInfo> getListData() {
		return this.listData;
	}
	
	public void setListData(List<ListDataInfo> listData) {
		this.listData = listData;
	}
	
	public List<ListDataInfo> getListData1() {
		return this.listData1;
	}
	
	public void setListData1(List<ListDataInfo> listData1) {
		this.listData1 = listData1;
	}
	
	public List<ListDataInfo> getListData2() {
		return this.listData2;
	}
	
	public void setListData2(List<ListDataInfo> listData2) {
		this.listData2 = listData2;
	}
	
	public List<EvaluetingInfo> getEvaluetingInfo() {
		return this.evaluetingInfo;
	}
	
	public void setEvaluetingInfo(List<EvaluetingInfo> evaluetingInfo) {
		this.evaluetingInfo = evaluetingInfo;
	}
	
	public List<FinancialSetInfo> getFinancialSetInfo() {
		return this.financialSetInfo;
	}
	
	public void setFinancialSetInfo(List<FinancialSetInfo> financialSetInfo) {
		this.financialSetInfo = financialSetInfo;
	}
	
	public List<FinancialSetInfo> getFinancialSetInfoTs() {
		return this.financialSetInfoTs;
	}
	
	public void setFinancialSetInfoTs(List<FinancialSetInfo> financialSetInfoTs) {
		this.financialSetInfoTs = financialSetInfoTs;
	}
	
	public List<PersonalCompanyOrder> getReqList() {
		return this.reqList;
	}
	
	public void setReqList(List<PersonalCompanyOrder> reqList) {
		this.reqList = reqList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListOrder [reqList=");
		builder.append(reqList);
		builder.append(", companyQualification=");
		builder.append(companyQualification);
		builder.append(", companyOwnershipStructure=");
		builder.append(companyOwnershipStructure);
		builder.append(", evaluatingBaseSet=");
		builder.append(evaluatingBaseSet);
		builder.append(", listData=");
		builder.append(listData);
		builder.append(", listData1=");
		builder.append(listData1);
		builder.append(", listData2=");
		builder.append(listData2);
		builder.append(", evaluetingInfo=");
		builder.append(evaluetingInfo);
		builder.append(", financialSetInfo=");
		builder.append(financialSetInfo);
		builder.append(", financialSetInfoTs=");
		builder.append(financialSetInfoTs);
		builder.append("]");
		return builder.toString();
	}
	
}
