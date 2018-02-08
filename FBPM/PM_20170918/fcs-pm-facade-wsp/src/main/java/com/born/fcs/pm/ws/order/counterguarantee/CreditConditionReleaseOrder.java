package com.born.fcs.pm.ws.order.counterguarantee;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ReleaseStatusEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 授信条件解保
 * 
 * @author lirz
 * 
 * 2016-5-13 下午1:44:20
 */
public class CreditConditionReleaseOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 3357263140452322237L;
	
	private long id;
	private String releaseStatus = ReleaseStatusEnum.WAITING.code();
	private String isCounter;
	private long releaseId;
	private String releaseReason;
	private String releaseGist;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getReleaseStatus() {
		return releaseStatus;
	}
	
	public void String(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	
	public String getIsCounter() {
		return isCounter;
	}

	public void setIsCounter(String isCounter) {
		this.isCounter = isCounter;
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
