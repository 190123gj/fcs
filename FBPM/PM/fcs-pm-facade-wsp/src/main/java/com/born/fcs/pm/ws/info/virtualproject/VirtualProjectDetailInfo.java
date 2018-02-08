package com.born.fcs.pm.ws.info.virtualproject;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 虚拟项目-选择的项目
 *
 * @author wuzj
 */
public class VirtualProjectDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -3792180740642357005L;
	/** 主键 */
	private long detailId;
	/** 虚拟项目ID */
	private long virtualId;
	/** 虚拟项目编号 */
	private String virtualProjectCode;
	/** 所选项目编号 */
	private String projectCode;
	/** 所选项目名称 */
	private String projectName;
	/** 所选项目客户 */
	private long customerId;
	private String customerName;
	/** 所选项目授信金额 */
	private Money amount = new Money(0, 0);
	/** 所选项目在保余额 */
	private Money balance = new Money(0, 0);
	/** 所选项目业务类型 */
	private String busiType;
	private String busiTypeName;
	/** 所选项目客户经理 */
	private long busiManagerId;
	private String busiManagerAccount;
	private String busiManagerName;
	/** 所选项目所属部门 */
	private long deptId;
	private String deptName;
	/** 备注 */
	private String remark;
	/** 附件 */
	private String attach;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public long getDetailId() {
		return this.detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public long getVirtualId() {
		return this.virtualId;
	}
	
	public void setVirtualId(long virtualId) {
		this.virtualId = virtualId;
	}
	
	public String getVirtualProjectCode() {
		return this.virtualProjectCode;
	}
	
	public void setVirtualProjectCode(String virtualProjectCode) {
		this.virtualProjectCode = virtualProjectCode;
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
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Money getBalance() {
		return this.balance;
	}
	
	public void setBalance(Money balance) {
		this.balance = balance;
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
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return this.busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
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
	
}
