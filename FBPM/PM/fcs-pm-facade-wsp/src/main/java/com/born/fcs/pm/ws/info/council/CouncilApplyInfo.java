package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.yjf.common.lang.util.money.Money;

public class CouncilApplyInfo extends ProjectInfo {
	
	private static final long serialVersionUID = 1418566933529832874L;
	
	private long applyId;
	
	private String projectCode;
	
	private String projectName;
	
	private long formId;
	
	private Money amount;
	
	private int timeLimit;
	
	private TimeUnitEnum timeUnit;
	
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
	
	private CouncilApplyStatusEnum councilApplyStatus;
	
	/** 公司名【目前用户信汇】 **/
	private CompanyNameEnum companyName;
	
	/** 是否上母公司会 YES/NO **/
	private BooleanEnum motherCompanyApply;
	
	/** 母公司会 会议类型 **/
	private String motherCouncilCode;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
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
	
	public void setMotherCouncilCode(String motherCouncilCode) {
		this.motherCouncilCode = motherCouncilCode;
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
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public TimeUnitEnum getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
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
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public CouncilApplyStatusEnum getCouncilApplyStatus() {
		return this.councilApplyStatus;
	}
	
	public void setCouncilApplyStatus(CouncilApplyStatusEnum councilApplyStatus) {
		this.councilApplyStatus = councilApplyStatus;
	}
	
	public String getCouncilCode() {
		return this.councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
}
