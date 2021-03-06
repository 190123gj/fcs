/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.dataobject;

import java.io.Serializable;
// auto generated imports
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A data object class directly models database table <tt>decision_member</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access
 * Layer) code generation utility specially developed for <tt>paygw</tt>
 * project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may be
 * OVERWRITTEN by someone else. To modify the file, you should go to directory
 * <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and find the corresponding
 * configuration file (<tt>tables/decision_member.xml</tt>). Modify the
 * configuration file according to your needs, then run
 * <tt>specialmer-dalgen</tt> to generate this file.
 *
 * @author peigen
 */
public class DecisionMemberDO implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long memberId;
	
	private long institutionId;
	
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private int sortOrder;
	
	private String deleteMark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	
	public long getInstitutionId() {
		return institutionId;
	}
	
	public void setInstitutionId(long institutionId) {
		this.institutionId = institutionId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(String deleteMark) {
		this.deleteMark = deleteMark;
	}
}
