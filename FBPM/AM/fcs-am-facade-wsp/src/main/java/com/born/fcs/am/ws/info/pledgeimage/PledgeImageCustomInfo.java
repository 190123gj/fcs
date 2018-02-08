package com.born.fcs.am.ws.info.pledgeimage;

import java.util.Date;

import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 抵质押品-文字信息-自定义Info
 *
 * @author jil
 *
 */
public class PledgeImageCustomInfo extends BaseToStringInfo {

	private static final long serialVersionUID = 4707698711824974520L;

	private long imageId;

	private long typeId;

	private String fieldName;

	private String attachmentFormat;

	private BooleanEnum isRequired;

	private LatestEntryFormEnum latestEntryForm;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

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

	public BooleanEnum getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(BooleanEnum isRequired) {
		this.isRequired = isRequired;
	}

	public LatestEntryFormEnum getLatestEntryForm() {
		return latestEntryForm;
	}

	public void setLatestEntryForm(LatestEntryFormEnum latestEntryForm) {
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

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

}
