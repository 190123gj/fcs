/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.assesscompany;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 评估公司 业务范围QueryOrder
 *
 * @author jil
 *
 */
public class AssessCompanyBusinessScopeQueryOrder extends QueryPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3396979402389925976L;

	private long id;

	private long assessCompanyId;

	private String businessScopeRegion;

	private String code;

	private int sort;

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

	public String getBusinessScopeRegion() {
		return businessScopeRegion;
	}

	public void setBusinessScopeRegion(String businessScopeRegion) {
		this.businessScopeRegion = businessScopeRegion;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
