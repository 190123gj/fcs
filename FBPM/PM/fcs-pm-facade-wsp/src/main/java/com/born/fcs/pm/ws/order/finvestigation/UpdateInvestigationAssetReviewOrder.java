package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 尽调-资产复评
 * 
 * @author lirz
 * 
 * 2016-9-19 下午4:42:52
 * 
 */
public class UpdateInvestigationAssetReviewOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 7493852776899193827L;
	
	private long id;
	
	private long reviewerId;
	
	private String reviewerAccount;
	
	private String reviewerName;
	
	private Date reviewTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getReviewerId() {
		return this.reviewerId;
	}
	
	public void setReviewerId(long reviewerId) {
		this.reviewerId = reviewerId;
	}
	
	public String getReviewerAccount() {
		return this.reviewerAccount;
	}
	
	public void setReviewerAccount(String reviewerAccount) {
		this.reviewerAccount = reviewerAccount;
	}
	
	public String getReviewerName() {
		return this.reviewerName;
	}
	
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	
	public Date getReviewTime() {
		return this.reviewTime;
	}
	
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
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
