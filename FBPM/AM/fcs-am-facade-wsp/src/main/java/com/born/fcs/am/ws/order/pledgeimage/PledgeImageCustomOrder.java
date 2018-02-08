package com.born.fcs.am.ws.order.pledgeimage;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 抵质押品-文字信息-自定义Order
 *
 * @author jil
 *
 */
public class PledgeImageCustomOrder extends ProcessOrder {

	private static final long serialVersionUID = 7909326623014702381L;

	private long imageId;

	private long typeId;

	private String fieldName;

	private String attachmentFormat;

	private String isRequired;

	private String latestEntryForm;

	private int sortOrder;

	private Date rawAddTime;

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
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

}
