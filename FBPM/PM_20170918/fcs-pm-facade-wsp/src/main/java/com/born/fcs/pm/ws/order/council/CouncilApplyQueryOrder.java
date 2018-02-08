package com.born.fcs.pm.ws.order.council;

import java.util.List;

import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class CouncilApplyQueryOrder extends FcsQueryPageBase {
	
	private static final long serialVersionUID = -1682497010477888001L;
	
	private long applyId;
	
	private String projectCode;
	
	private String customerName;
	
	private String applyManName;
	
	private long applyDeptId;
	
	private String applyDeptName;
	
	private String councilCode;
	
	private long councilType;
	
	private String councilTypeDesc;
	
	private CouncilApplyStatusEnum status;
	
	/**
	 * 用于判定查询哪些类型的数据
	 */
	private List<String> councilTypeCodes;
	
	/**
	 * 哪些公司【目前用作对信汇的操作】
	 */
	private List<String> companyNames;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public List<String> getCompanyNames() {
		return this.companyNames;
	}
	
	public void setCompanyNames(List<String> companyNames) {
		this.companyNames = companyNames;
	}
	
	public List<String> getCouncilTypeCodes() {
		return this.councilTypeCodes;
	}
	
	public void setCouncilTypeCodes(List<String> councilTypeCodes) {
		this.councilTypeCodes = councilTypeCodes;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getApplyManName() {
		return this.applyManName;
	}
	
	public void setApplyManName(String applyManName) {
		this.applyManName = applyManName;
	}
	
	public String getApplyDeptName() {
		return this.applyDeptName;
	}
	
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	
	public long getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(long councilType) {
		this.councilType = councilType;
	}
	
	public String getCouncilTypeDesc() {
		return this.councilTypeDesc;
	}
	
	public void setCouncilTypeDesc(String councilTypeDesc) {
		this.councilTypeDesc = councilTypeDesc;
	}
	
	public long getApplyDeptId() {
		return this.applyDeptId;
	}
	
	public void setApplyDeptId(long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
	public CouncilApplyStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(CouncilApplyStatusEnum status) {
		this.status = status;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public String getCouncilCode() {
		return this.councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
}
