package com.born.fcs.pm.ws.info.common;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;

/**
 * 项目相关人员Info
 *
 * @author wuzj
 * 
 * 2016年4月29日
 */
public class ProjectRelatedUserInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5178098461305292200L;
	
	private long relatedId; //主键
	
	private String projectCode; //项目编号/签报编号等
	
	private ProjectRelatedUserTypeEnum userType; //人员类型
	
	/**
	 * 当前人员
	 */
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private String userMobile;
	
	private String userEmail;
	
	//相关部门
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	//移交时间
	private Date transferTime;
	
	private long transferRelatedId;
	
	private String remark;
	
	//是否当前
	private BooleanEnum isCurrent;
	//是否已删除
	private BooleanEnum isDel;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public SimpleUserInfo toSimpleUserInfo() {
		SimpleUserInfo user = new SimpleUserInfo();
		user.setUserId(userId);
		user.setUserName(userName);
		user.setUserAccount(userAccount);
		user.setEmail(userEmail);
		user.setDeptId(deptId);
		user.setDeptCode(deptCode);
		user.setDeptName(deptName);
		user.setDeptPath(deptPath);
		user.setDeptPathName(deptPathName);
		return user;
	}
	
	public long getRelatedId() {
		return this.relatedId;
	}
	
	public void setRelatedId(long relatedId) {
		this.relatedId = relatedId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public ProjectRelatedUserTypeEnum getUserType() {
		return this.userType;
	}
	
	public void setUserType(ProjectRelatedUserTypeEnum userType) {
		this.userType = userType;
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
	
	public Date getTransferTime() {
		return this.transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public BooleanEnum getIsCurrent() {
		return this.isCurrent;
	}
	
	public void setIsCurrent(BooleanEnum isCurrent) {
		this.isCurrent = isCurrent;
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
	
	public String getUserMobile() {
		return this.userMobile;
	}
	
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
	
	public long getTransferRelatedId() {
		return this.transferRelatedId;
	}
	
	public void setTransferRelatedId(long transferRelatedId) {
		this.transferRelatedId = transferRelatedId;
	}
	
}
