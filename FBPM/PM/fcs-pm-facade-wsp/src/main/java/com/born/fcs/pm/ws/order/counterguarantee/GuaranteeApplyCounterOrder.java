package com.born.fcs.pm.ws.order.counterguarantee;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 解保选择的授信条件
 *
 * @author lirz
 * 
 * 2016-12-20 下午4:04:26
 *
 */
public class GuaranteeApplyCounterOrder extends ValidateOrderBase{

	private static final long serialVersionUID = -1841495695356485004L;

	private long id;

	private String projectCode;

	private long formId;

	private String itemDesc;

	private long releaseId;

	private String releaseReason;

	private String releaseGist;

	private String releaseStatus;

	private String releaseRemark;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public long getReleaseId() {
		return releaseId;
	}
	
	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
	}

	public String getReleaseReason() {
		return releaseReason;
	}
	
	public void setReleaseReason(String releaseReason) {
		this.releaseReason = releaseReason;
	}

	public String getReleaseGist() {
		return releaseGist;
	}
	
	public void setReleaseGist(String releaseGist) {
		this.releaseGist = releaseGist;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}
	
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getReleaseRemark() {
		return releaseRemark;
	}
	
	public void setReleaseRemark(String releaseRemark) {
		this.releaseRemark = releaseRemark;
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
