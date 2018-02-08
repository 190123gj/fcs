package com.born.fcs.pm.ws.info.counterguarantee;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 到期解保申请列表
 * 
 * @author lirz
 *
 * 2016-5-12 下午6:36:38
 */
public class CounterGuaranteeApplyInfo extends SimpleFormProjectInfo{

	private static final long serialVersionUID = 859401892778583760L;

	private long id;

	private String measure;

	private String isRelease;

	private String releaseReason;

	private String releaseBasis;

	private String releaseBasisUrl;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getMeasure() {
		return measure;
	}
	
	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getIsRelease() {
		return isRelease;
	}
	
	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getReleaseReason() {
		return releaseReason;
	}
	
	public void setReleaseReason(String releaseReason) {
		this.releaseReason = releaseReason;
	}

	public String getReleaseBasis() {
		return releaseBasis;
	}
	
	public void setReleaseBasis(String releaseBasis) {
		this.releaseBasis = releaseBasis;
	}

	public String getReleaseBasisUrl() {
		return releaseBasisUrl;
	}
	
	public void setReleaseBasisUrl(String releaseBasisUrl) {
		this.releaseBasisUrl = releaseBasisUrl;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
