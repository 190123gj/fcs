package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 履历
 *
 * @author lirz
 * 
 * 2016-9-21 上午10:01:51
 *
 */
public class FInvestigationMabilityReviewLeadingTeamResumeOrder extends ValidateOrderBase{

    private static final long serialVersionUID = -3226497282042687484L;

	private long id;

	private long maReviewId;

	private long tid;

	private String startDate;

	private String endDate;

	private String companyName;

	private String title;

	private int sortOrder;

	public boolean isNull() {
		return isNull(startDate)
				&& isNull(endDate)
				&& isNull(companyName)
				&& isNull(title)
				;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getMaReviewId() {
		return maReviewId;
	}
	
	public void setMaReviewId(long maReviewId) {
		this.maReviewId = maReviewId;
	}

	public long getTid() {
		return tid;
	}
	
	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
