package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 复制尽调order
 * 
 * @author lirz
 * 
 * 2016-10-11 上午11:33:40
 * 
 */
public class FInvestigationCopyOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 1294361587551969408L;
	
	private long formId;
	private String review = BooleanEnum.NO.code(); //复议标识 
	private String councilBack  = BooleanEnum.NO.code(); //上会退回标识
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
