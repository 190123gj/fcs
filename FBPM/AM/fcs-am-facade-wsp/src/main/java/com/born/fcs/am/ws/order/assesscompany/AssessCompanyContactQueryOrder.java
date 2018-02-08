/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.assesscompany;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 评估公司Order
 *
 * @author jil
 *
 */
public class AssessCompanyContactQueryOrder extends QueryPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9197977649827339459L;

	private long id;

	private long assessCompanyId;

	private String contactName;

	private String contactNumber;

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

}
