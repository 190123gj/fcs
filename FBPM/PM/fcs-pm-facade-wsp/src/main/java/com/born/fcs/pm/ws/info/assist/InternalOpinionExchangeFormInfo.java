package com.born.fcs.pm.ws.info.assist;

import java.util.Date;

import com.born.fcs.pm.ws.enums.InternalOpinionExTypeEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

/**
 * 内审意见交换
 *
 * @author wuzj
 */
public class InternalOpinionExchangeFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = -6674931551892042879L;
	
	private long id;
	
	/** 编码 */
	private String exCode;
	
	/** 类型 */
	private InternalOpinionExTypeEnum exType;
	
	/** 审计部门ID */
	private String deptIds;
	
	/** 审计部门名称 */
	private String deptNames;
	
	/** 意见反馈时间 */
	private Date feedbackTime;
	
	/** 审计人员 */
	private String users;
	
	/** 说明 */
	private String remark;
	
	/** 新增时间 */
	private Date rawAddTime;
	
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getExCode() {
		return this.exCode;
	}
	
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	
	public InternalOpinionExTypeEnum getExType() {
		return this.exType;
	}
	
	public void setExType(InternalOpinionExTypeEnum exType) {
		this.exType = exType;
	}
	
	public String getDeptIds() {
		return this.deptIds;
	}
	
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	
	public String getDeptNames() {
		return this.deptNames;
	}
	
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
	public Date getFeedbackTime() {
		return this.feedbackTime;
	}
	
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	public String getUsers() {
		return this.users;
	}
	
	public void setUsers(String users) {
		this.users = users;
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
	
}
