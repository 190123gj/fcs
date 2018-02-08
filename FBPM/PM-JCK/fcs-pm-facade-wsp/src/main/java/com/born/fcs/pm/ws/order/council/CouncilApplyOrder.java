package com.born.fcs.pm.ws.order.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class CouncilApplyOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 3350968797904641475L;
	
	private long applyId;
	
	private Long childId;
	
	private String projectCode;
	
	private String projectName;
	
	private long formId;
	
	private Money amount;
	
	private int timeLimit;
	
	private String timeUnit;
	
	private long customerId;
	
	private String customerName;
	
	private long applyManId;
	
	private String applyManName;
	
	private long applyDeptId;
	
	private String applyDeptName;
	
	private String councilCode;
	
	private long councilType;
	
	private String councilTypeDesc;
	
	private Date applyTime;
	
	private String status;
	
	/** 公司名【目前用户信汇】 **/
	private CompanyNameEnum companyName = CompanyNameEnum.NORMAL;
	
	/** 是否上母公司会 YES/NO **/
	private BooleanEnum motherCompanyApply;
	
	/** 母公司会 会议类型 **/
	private String motherCouncilCode;
	
	/** 是否以order传入的companyName为准进行存库操作 */
	private boolean hasCompanyName;
	
	public boolean isHasCompanyName() {
		return this.hasCompanyName;
	}
	
	public void setHasCompanyName(boolean hasCompanyName) {
		this.hasCompanyName = hasCompanyName;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public CompanyNameEnum getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(CompanyNameEnum companyName) {
		this.companyName = companyName;
	}
	
	public BooleanEnum getMotherCompanyApply() {
		return this.motherCompanyApply;
	}
	
	public void setMotherCompanyApply(BooleanEnum motherCompanyApply) {
		this.motherCompanyApply = motherCompanyApply;
	}
	
	public String getMotherCouncilCode() {
		return this.motherCouncilCode;
	}
	
	public Long getChildId() {
		return this.childId;
	}
	
	public void setChildId(Long childId) {
		this.childId = childId;
	}
	
	public void setMotherCouncilCode(String motherCouncilCode) {
		this.motherCouncilCode = motherCouncilCode;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public long getApplyManId() {
		return this.applyManId;
	}
	
	public void setApplyManId(long applyManId) {
		this.applyManId = applyManId;
	}
	
	public String getApplyManName() {
		return this.applyManName;
	}
	
	public void setApplyManName(String applyManName) {
		this.applyManName = applyManName;
	}
	
	public long getApplyDeptId() {
		return this.applyDeptId;
	}
	
	public void setApplyDeptId(long applyDeptId) {
		this.applyDeptId = applyDeptId;
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
	
	public Date getApplyTime() {
		return this.applyTime;
	}
	
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCouncilCode() {
		return this.councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
}
