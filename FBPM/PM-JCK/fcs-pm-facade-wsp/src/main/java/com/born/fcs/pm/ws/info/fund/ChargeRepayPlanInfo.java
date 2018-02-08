package com.born.fcs.pm.ws.info.fund;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 收费及 还款计划 *
 *
 * @author wuzj
 *
 */
public class ChargeRepayPlanInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -2741737445294636120L;
	
	/** 计划ID */
	private long planId;
	/** 对应项目编号 */
	private String projectCode;
	/** 对应项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 业务品种 */
	private String busiType;
	/** 业务品种 */
	private String busiTypeName;
	/** 是否收费计划 */
	private BooleanEnum isChargePlan;
	/** 是否还款计划 */
	private BooleanEnum isRepayPlan;
	/** 创建人ID */
	private long userId;
	/** 创建人账号 */
	private String userAccount;
	/** 创建人名字 */
	private String userName;
	/** 创建部门ID */
	private long deptId;
	/** 部门编号 */
	private String deptCode;
	/** 部门名称 */
	private String deptName;
	/** 部门路径 */
	private String deptPath;
	/** 部门路径名称 */
	private String deptPathName;
	/** 备注 */
	private String remark;
	/***/
	private Date rawAddTime;
	/***/
	private Date rawUpdateTime;
	
	/**
	 * 还款计划列表
	 */
	List<ChargeRepayPlanDetailInfo> repayPlanList;
	
	/**
	 * 收费计划列表
	 */
	List<ChargeRepayPlanDetailInfo> chargePlanList;
	
	public long getPlanId() {
		return this.planId;
	}
	
	public void setPlanId(long planId) {
		this.planId = planId;
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
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public BooleanEnum getIsChargePlan() {
		return this.isChargePlan;
	}
	
	public void setIsChargePlan(BooleanEnum isChargePlan) {
		this.isChargePlan = isChargePlan;
	}
	
	public BooleanEnum getIsRepayPlan() {
		return this.isRepayPlan;
	}
	
	public void setIsRepayPlan(BooleanEnum isRepayPlan) {
		this.isRepayPlan = isRepayPlan;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public List<ChargeRepayPlanDetailInfo> getRepayPlanList() {
		return this.repayPlanList;
	}
	
	public void setRepayPlanList(List<ChargeRepayPlanDetailInfo> repayPlanList) {
		this.repayPlanList = repayPlanList;
	}
	
	public List<ChargeRepayPlanDetailInfo> getChargePlanList() {
		return this.chargePlanList;
	}
	
	public void setChargePlanList(List<ChargeRepayPlanDetailInfo> chargePlanList) {
		this.chargePlanList = chargePlanList;
	}
	
}
