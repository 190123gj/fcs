package com.born.fcs.am.ws.info.assesscompany;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class AssessCompanyContactInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1172654882010502077L;

	private long id;

	private long assessCompanyId;

	private String contactName;

	private String contactNumber;

	private Date rawAddTime;

	private Date rawUpdateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAssessCompanyId() {
		return assessCompanyId;
	}

	public void setAssessCompanyId(long assessCompanyId) {
		this.assessCompanyId = assessCompanyId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

}
