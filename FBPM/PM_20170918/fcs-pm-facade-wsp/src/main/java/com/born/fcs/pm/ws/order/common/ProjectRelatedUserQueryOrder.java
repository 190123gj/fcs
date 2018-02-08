package com.born.fcs.pm.ws.order.common;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;

/**
 * 项目相关人员查询Order
 *
 * @author wuzj
 * 
 * 2016年8月6日
 */
public class ProjectRelatedUserQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -887598381415719320L;
	
	private long relatedId; //主键
	
	private long transferRelatedId; //主键
	
	private String projectCode; //项目编号
	
	private ProjectRelatedUserTypeEnum userType; //人员类型
	
	/**
	 * 当前人员
	 */
	private long userId;
	
	private String userAccount;
	
	/**
	 * 是否当前
	 */
	private BooleanEnum isCurrent;
	/**
	 * 是否删除
	 */
	private BooleanEnum isDel;
	
	/**
	 * 部门ID
	 */
	private List<Long> deptIdList;
	
	/** 部门编号 */
	private String deptCode;
	
	/** 部门路径 */
	private String deptPath;
	
	/** 部门名称 */
	private String deptName;
	
	/** 备注 */
	private String remark;
	
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
	
	public BooleanEnum getIsCurrent() {
		return this.isCurrent;
	}
	
	public void setIsCurrent(BooleanEnum isCurrent) {
		this.isCurrent = isCurrent;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public long getTransferRelatedId() {
		return this.transferRelatedId;
	}
	
	public void setTransferRelatedId(long transferRelatedId) {
		this.transferRelatedId = transferRelatedId;
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
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
