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
 * A data object class directly models database table <tt>project_recovery_litigation_adjourned_procedure</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/project_recovery_litigation_adjourned_procedure.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class ProjectRecoveryLitigationAdjournedProcedureDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long id;

	private long projectRecoveryId;

	private String procedureType;

	private Date openingTime;

	private String witnessAttorney;

	private String weLitigationDemand;

	private String otherSideLitigationDemand;

	private String controversyFocus;

	private String additionalEvidence;

	private String memo;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}

	public String getProcedureType() {
		return procedureType;
	}
	
	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public Date getOpeningTime() {
		return openingTime;
	}
	
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	public String getWitnessAttorney() {
		return witnessAttorney;
	}
	
	public void setWitnessAttorney(String witnessAttorney) {
		this.witnessAttorney = witnessAttorney;
	}

	public String getWeLitigationDemand() {
		return weLitigationDemand;
	}
	
	public void setWeLitigationDemand(String weLitigationDemand) {
		this.weLitigationDemand = weLitigationDemand;
	}

	public String getOtherSideLitigationDemand() {
		return otherSideLitigationDemand;
	}
	
	public void setOtherSideLitigationDemand(String otherSideLitigationDemand) {
		this.otherSideLitigationDemand = otherSideLitigationDemand;
	}

	public String getControversyFocus() {
		return controversyFocus;
	}
	
	public void setControversyFocus(String controversyFocus) {
		this.controversyFocus = controversyFocus;
	}

	public String getAdditionalEvidence() {
		return additionalEvidence;
	}
	
	public void setAdditionalEvidence(String additionalEvidence) {
		this.additionalEvidence = additionalEvidence;
	}

	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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