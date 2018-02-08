package com.born.fcs.pm.ws.order.managerbchange;

import java.util.Date;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbWayEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.StringUtil;

/**
 * B角变更Order
 * @author wuzj
 */
public class FManagerbChangeApplyOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -3241155024467067675L;
	/** 申请ID */
	private Long applyId;
	/** 项目编号 */
	protected String projectCode;
	/** 原B角ID */
	private Long oldBid;
	/** 原B角账号 */
	private String oldBaccount;
	/** 原B名 */
	private String oldBname;
	/** 新B角ID */
	private Long newBid;
	/** 新B角账号 */
	private String newBaccount;
	/** 新B角名 */
	private String newBname;
	/** 变更方式 ChangeManagerbWayEnum */
	private String changeWay;
	/** 变更阶段，多个,分开 */
	private String changePhases;
	/** 变更开始时间 */
	private Date changeStartTime;
	/** 变更结束时间 */
	private Date changeEndTime;
	/** 备注 */
	private String remark;
	
	@Override
	public void check() {
		validateNotNull(projectCode, "项目编号 ");
		Assert.isTrue(oldBid != newBid, "更换后B角不能和原B角相同");
		if (checkStatus != null && checkStatus == 1) {
			Assert.isTrue(newBid != null && newBid > 0, "更换的B角不能为空");
			if (StringUtil.equals(ChangeManagerbWayEnum.PERIOD.code(), changeWay)) {
				validateNotNull(changeStartTime, "变更开始时间不能为空");
				validateNotNull(changeEndTime, "变更结束时间不能为空");
				Assert.isTrue(changeEndTime.after(changeStartTime), "开始时间不能大于结束时间 ");
			} else if (StringUtil.equals(ChangeManagerbWayEnum.PHASES.code(), changeWay)) {
				validateNotNull(changePhases, "变更阶段");
			}
		}
	}
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Long getOldBid() {
		return this.oldBid;
	}
	
	public void setOldBid(Long oldBid) {
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
	
	public Long getNewBid() {
		return this.newBid;
	}
	
	public void setNewBid(Long newBid) {
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
	
	public String getChangeWay() {
		return this.changeWay;
	}
	
	public void setChangeWay(String changeWay) {
		this.changeWay = changeWay;
	}
	
	public String getChangePhases() {
		return this.changePhases;
	}
	
	public void setChangePhases(String changePhases) {
		this.changePhases = changePhases;
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
	
}
