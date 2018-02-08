package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.SiteStatusEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 其他信用信息
 * 
 * @author lirz
 *
 * 2016-3-10 下午3:46:41
 */
public class FInvestigationMainlyReviewCreditInfoOrder extends ValidateOrderBase{

	private static final long serialVersionUID = 1483611988090249492L;

	private long id;
	private long MReviewId;
	private String siteName; //网站名称
	private String site; //网址
	private SiteStatusEnum status; //查询状态
	private String remark; //异常备注
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(siteName)
				&& isNull(site)
				&& null == status
				&& isNull(remark)
				;
	}

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getMReviewId() {
		return MReviewId;
	}
	
	public void setMReviewId(long MReviewId) {
		this.MReviewId = MReviewId;
	}

	public String getSiteName() {
		return siteName;
	}
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}

	public SiteStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(SiteStatusEnum status) {
		this.status = status;
	}
	
	public String getStatusStr() {
		return (null == this.status) ? "" : this.status.code();
	}
	
	public void setStatusStr(String code) {
		this.status = SiteStatusEnum.getByCode(code);
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
