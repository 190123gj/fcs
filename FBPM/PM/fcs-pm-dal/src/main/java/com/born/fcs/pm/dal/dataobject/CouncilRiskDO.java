/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// auto generated imports
import java.util.Date;

/**
 * A data object class directly models database table <tt>council_risk</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/council_risk.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class CouncilRiskDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long councilId;

	private String councilCode;

	private String councilType;

	private String councilPlace;

	private String councilSubject;

	private Date beginTime;

	private long customerId;

	private String customerName;

	private String projectCode;

	private String projectName;

	private long applyManId;

	private String applyManName;

	private String councilStatus;

	private String participantIds;

	private String participantNames;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getCouncilId() {
		return councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}

	public String getCouncilCode() {
		return councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}

	public String getCouncilType() {
		return councilType;
	}
	
	public void setCouncilType(String councilType) {
		this.councilType = councilType;
	}

	public String getCouncilPlace() {
		return councilPlace;
	}
	
	public void setCouncilPlace(String councilPlace) {
		this.councilPlace = councilPlace;
	}

	public String getCouncilSubject() {
		return councilSubject;
	}
	
	public void setCouncilSubject(String councilSubject) {
		this.councilSubject = councilSubject;
	}

	public Date getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getApplyManId() {
		return applyManId;
	}
	
	public void setApplyManId(long applyManId) {
		this.applyManId = applyManId;
	}

	public String getApplyManName() {
		return applyManName;
	}
	
	public void setApplyManName(String applyManName) {
		this.applyManName = applyManName;
	}

	public String getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(String councilStatus) {
		this.councilStatus = councilStatus;
	}

	public String getParticipantIds() {
		return participantIds;
	}
	
	public void setParticipantIds(String participantIds) {
		this.participantIds = participantIds;
	}

	public String getParticipantNames() {
		return participantNames;
	}
	
	public void setParticipantNames(String participantNames) {
		this.participantNames = participantNames;
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
}
