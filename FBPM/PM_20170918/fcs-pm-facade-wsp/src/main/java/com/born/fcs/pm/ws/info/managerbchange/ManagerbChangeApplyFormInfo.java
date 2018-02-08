package com.born.fcs.pm.ws.info.managerbchange;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbStatusEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbWayEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * B角更换表单INFO
 * @author wuzj
 */
public class ManagerbChangeApplyFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 670100292269279881L;
	
	/** 申请ID */
	private long applId;
	/** 项目编号 */
	protected String projectCode;
	/** 项目名称 */
	protected String projectName;
	/** 客户ID */
	protected long customerId;
	/** 客户名称 */
	protected String customerName;
	/** 金额 */
	protected Money amount = new Money(0, 0);
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 原B角ID */
	private long oldBid;
	/** 原B角账号 */
	private String oldBaccount;
	/** 原B名 */
	private String oldBname;
	/** 新B角ID */
	private long newBid;
	/** 新B角账号 */
	private String newBaccount;
	/** 新B角名 */
	private String newBname;
	/** 客户经理ID */
	private long managerId;
	/** 客户经理账号 */
	private String managerAccount;
	/** 客户经理名称 */
	private String managerName;
	/** 变更方式 */
	private ChangeManagerbWayEnum changeWay;
	/** 变更阶段，多个,分开 */
	private String changePhases;
	/** 变更阶段 */
	private List<ChangeManagerbPhaseEnum> changePhaseList;
	/** 变更开始时间 */
	private Date changeStartTime;
	/** 变更结束时间 */
	private Date changeEndTime;
	/** 备注 */
	private String remark;
	/** 变更状态 */
	private ChangeManagerbStatusEnum status;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public long getApplId() {
		return this.applId;
	}
	
	public void setApplId(long applId) {
		this.applId = applId;
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
	
	public long getOldBid() {
		return this.oldBid;
	}
	
	public void setOldBid(long oldBid) {
		this.oldBid = oldBid;
	}
	
	public String getOldBaccount() {
		return this.oldBaccount;
	}
	
	public void setOldBaccount(String oldBaccount) {
		this.oldBaccount = oldBaccount;
	}
	
	public String getOldBname() {
		return this.oldBname;
	}
	
	public void setOldBname(String oldBname) {
		this.oldBname = oldBname;
	}
	
	public long getNewBid() {
		return this.newBid;
	}
	
	public void setNewBid(long newBid) {
		this.newBid = newBid;
	}
	
	public String getNewBaccount() {
		return this.newBaccount;
	}
	
	public void setNewBaccount(String newBaccount) {
		this.newBaccount = newBaccount;
	}
	
	public String getNewBname() {
		return this.newBname;
	}
	
	public void setNewBname(String newBname) {
		this.newBname = newBname;
	}
	
	public long getManagerId() {
		return this.managerId;
	}
	
	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}
	
	public String getManagerAccount() {
		return this.managerAccount;
	}
	
	public void setManagerAccount(String managerAccount) {
		this.managerAccount = managerAccount;
	}
	
	public String getManagerName() {
		return this.managerName;
	}
	
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	public ChangeManagerbWayEnum getChangeWay() {
		return this.changeWay;
	}
	
	public void setChangeWay(ChangeManagerbWayEnum changeWay) {
		this.changeWay = changeWay;
	}
	
	public String getChangePhases() {
		return this.changePhases;
	}
	
	public void setChangePhases(String changePhases) {
		this.changePhases = changePhases;
	}
	
	public List<ChangeManagerbPhaseEnum> getChangePhaseList() {
		return this.changePhaseList;
	}
	
	public void setChangePhaseList(List<ChangeManagerbPhaseEnum> changePhaseList) {
		this.changePhaseList = changePhaseList;
	}
	
	public Date getChangeStartTime() {
		return this.changeStartTime;
	}
	
	public void setChangeStartTime(Date changeStartTime) {
		this.changeStartTime = changeStartTime;
	}
	
	public Date getChangeEndTime() {
		return this.changeEndTime;
	}
	
	public void setChangeEndTime(Date changeEndTime) {
		this.changeEndTime = changeEndTime;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public ChangeManagerbStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(ChangeManagerbStatusEnum status) {
		this.status = status;
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
