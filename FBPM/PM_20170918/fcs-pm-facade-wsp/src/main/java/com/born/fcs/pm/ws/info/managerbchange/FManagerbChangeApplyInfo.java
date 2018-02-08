package com.born.fcs.pm.ws.info.managerbchange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbStatusEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbWayEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * B角更换INFO
 * @author wuzj
 */
public class FManagerbChangeApplyInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = 2056863504988539076L;
	/** 申请ID */
	private long applyId;
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
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
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
		if (changePhases != null && !"".equals(changePhases)) {
			String phases[] = changePhases.split(",");
			if (phases != null && phases.length > 0) {
				if (changePhaseList == null)
					changePhaseList = new ArrayList<ChangeManagerbPhaseEnum>();
				changePhaseList.clear();
				for (String phase : phases) {
					ChangeManagerbPhaseEnum p = ChangeManagerbPhaseEnum.getByCode(phase);
					if (p != null)
						changePhaseList.add(p);
				}
			}
		}
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
