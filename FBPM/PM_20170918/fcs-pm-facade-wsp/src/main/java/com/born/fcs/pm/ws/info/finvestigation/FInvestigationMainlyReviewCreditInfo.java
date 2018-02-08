package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.SiteStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 其他信用信息
 * 
 * @author lirz
 *
 * 2016-3-10 下午3:46:41
 */
public class FInvestigationMainlyReviewCreditInfo extends BaseToStringInfo{

	private static final long serialVersionUID = 6460369702201120522L;
	
	private long id;
	private long MReviewId;
	private String siteName; //网站名称
	private String site; //网址
	private SiteStatusEnum status; //查询状态
	private String remark; //异常备注
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
