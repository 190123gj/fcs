/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.pledgeimage;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 抵质押品-文字信息-自定义Order
 *
 * @author jil
 *
 */
public class PledgeImageCustomQueryOrder extends QueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6744020142207248192L;
	
	private Long imageId;
	
	private long typeId;
	
	private String fieldName;
	
	private String attachmentFormat;
	
	private String isRequired;
	
	private String latestEntryForm;
	
	private Date rawAddTime;
	
	public Long getImageId() {
		return imageId;
	}
	
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getAttachmentFormat() {
		return attachmentFormat;
	}
	
	public void setAttachmentFormat(String attachmentFormat) {
		this.attachmentFormat = attachmentFormat;
	}
	
	public String getIsRequired() {
		return isRequired;
	}
	
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	public String getLatestEntryForm() {
		return latestEntryForm;
	}
	
	public void setLatestEntryForm(String latestEntryForm) {
		this.latestEntryForm = latestEntryForm;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
