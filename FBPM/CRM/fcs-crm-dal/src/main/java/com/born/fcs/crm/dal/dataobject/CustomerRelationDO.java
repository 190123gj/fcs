/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.crm.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// auto generated imports
import java.util.Date;

/**
 * A data object class directly models database table <tt>customer_relation</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/customer_relation.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class CustomerRelationDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long relationId;

	private long userId;

	private long customerManagerId;

	private String customerManager;

	private long directorId;

	private String director;

	private long depId;

	private String depName;

	private String depPath;

	private long operId;

	private String operName;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getRelationId() {
		return relationId;
	}
	
	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCustomerManagerId() {
		return customerManagerId;
	}
	
	public void setCustomerManagerId(long customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getCustomerManager() {
		return customerManager;
	}
	
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}

	public long getDirectorId() {
		return directorId;
	}
	
	public void setDirectorId(long directorId) {
		this.directorId = directorId;
	}

	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}

	public long getDepId() {
		return depId;
	}
	
	public void setDepId(long depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}
	
	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepPath() {
		return depPath;
	}
	
	public void setDepPath(String depPath) {
		this.depPath = depPath;
	}

	public long getOperId() {
		return operId;
	}
	
	public void setOperId(long operId) {
		this.operId = operId;
	}

	public String getOperName() {
		return operName;
	}
	
	public void setOperName(String operName) {
		this.operName = operName;
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