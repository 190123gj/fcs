package com.born.fcs.pm.ws.order.common;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 项目批复查询Order
 *
 *
 * @author wuzj
 *
 */
public class ProjectApprovalQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -1075193530991589464L;
	
	private BooleanEnum isDel; //是否作废
	
	private BooleanEnum isApproval; //是否通过
	
	private String startTimeStr;
	
	private String endTimeStr;
	
	private Date startTime;
	
	private Date endTime;
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public BooleanEnum getIsApproval() {
		return this.isApproval;
	}
	
	public void setIsApproval(BooleanEnum isApproval) {
		this.isApproval = isApproval;
	}
	
	public String getStartTimeStr() {
		return this.startTimeStr;
	}
	
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	
	public String getEndTimeStr() {
		return this.endTimeStr;
	}
	
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
