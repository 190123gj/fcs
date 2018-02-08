package com.born.fcs.am.ws.info.assesscompany;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class AssessCompanyBusinessScopeInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8740813094471394384L;

	private long id;

	private long assessCompanyId;

	private String businessScopeRegion;

	private String code;

	private int sort;

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
