package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 已获的资格证书
 * 
 * @author lirz
 *
 * 2016-3-10 下午1:56:04
 */
public class FInvestigationMainlyReviewCertificateInfo extends BaseToStringInfo {

	private static final long serialVersionUID = -1159667420440672702L;
	
	private long id; //主键
	private long MReviewId; //对应客户主体评价ID
	private String certificateName; //资格证书名称
	private String certificateCode; //编码
	private Date validDate; //有效期yyyyMMdd
	private String certificateUrl; //拍照地址
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;

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

	public String getCertificateName() {
		return certificateName;
	}
	
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateCode() {
		return certificateCode;
	}
	
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	public Date getValidDate() {
		return validDate;
	}
	
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getCertificateUrl() {
		return certificateUrl;
	}
	
	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
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
