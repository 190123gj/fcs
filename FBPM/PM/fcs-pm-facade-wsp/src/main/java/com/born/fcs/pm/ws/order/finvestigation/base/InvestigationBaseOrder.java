package com.born.fcs.pm.ws.order.finvestigation.base;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * @author lirz
 * 
 * 2016-3-8 下午2:00:20
 */
public class InvestigationBaseOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 8458702288636458922L;
	
	private long formId;
	private String projectCode;
	private String review = BooleanEnum.NO.code();
	private String councilBack = BooleanEnum.NO.code();
	
	@Override
	public void check() {
		super.check();
		validateGreaterThan(formId, "表单ID");
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getReview() {
		return this.review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getCouncilBack() {
		return this.councilBack;
	}

	public void setCouncilBack(String councilBack) {
		this.councilBack = councilBack;
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
