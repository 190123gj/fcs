package com.born.fcs.am.ws.order.assesscompany;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 评估公司 业务范围Order
 *
 * @author jil
 *
 */
public class AssessCompanyBusinessScopeOrder extends ProcessOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5452339586060119248L;

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
