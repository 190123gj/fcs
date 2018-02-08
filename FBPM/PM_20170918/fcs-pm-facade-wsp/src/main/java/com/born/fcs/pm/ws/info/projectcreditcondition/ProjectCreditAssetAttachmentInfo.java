package com.born.fcs.pm.ws.info.projectcreditcondition;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 项目授信条件 - 资产附件上传
 *
 * @author Ji
 *
 */
public class ProjectCreditAssetAttachmentInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5221086375508008747L;
	
	//主键id
	private long id;
	//资产id
	private long assetId;
	//授信落实表中对应的id
	private long creditId;
	//资产-图像信息对应key值
	private String imageKey;
	//资产-图像信息对应value值(文本)
	private String imageTextValue;
	//资产-图像信息对应value值
	private String imageValue;
	
	private Date rawAddTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getAssetId() {
		return assetId;
	}
	
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
	public long getCreditId() {
		return creditId;
	}
	
	public void setCreditId(long creditId) {
		this.creditId = creditId;
	}
	
	public String getImageKey() {
		return imageKey;
	}
	
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	
	public String getImageValue() {
		return imageValue;
	}
	
	public void setImageValue(String imageValue) {
		this.imageValue = imageValue;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getImageTextValue() {
		return imageTextValue;
	}
	
	public void setImageTextValue(String imageTextValue) {
		this.imageTextValue = imageTextValue;
	}
	
}
