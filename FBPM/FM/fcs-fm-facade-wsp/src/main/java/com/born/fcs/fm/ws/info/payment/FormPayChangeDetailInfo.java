/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:12:14 创建
 */
package com.born.fcs.fm.ws.info.payment;

import java.util.Date;

import com.born.fcs.fm.ws.enums.ChangeSourceTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FormPayChangeDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private long sourceId;
	
	private ChangeSourceTypeEnum sourceType;
	
	private long userId;
	
	private String userName;
	
	private long sort;
	
	private String documentName;
	
	private String documentDescribe;
	
	private String documentValueOld;
	
	private String documentValueNew;
	
	private String documentType;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSourceId() {
		return this.sourceId;
	}
	
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	
	public ChangeSourceTypeEnum getSourceType() {
		return this.sourceType;
	}
	
	public void setSourceType(ChangeSourceTypeEnum sourceType) {
		this.sourceType = sourceType;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getSort() {
		return this.sort;
	}
	
	public void setSort(long sort) {
		this.sort = sort;
	}
	
	public String getDocumentName() {
		return this.documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public String getDocumentDescribe() {
		return this.documentDescribe;
	}
	
	public void setDocumentDescribe(String documentDescribe) {
		this.documentDescribe = documentDescribe;
	}
	
	public String getDocumentValueOld() {
		return this.documentValueOld;
	}
	
	public void setDocumentValueOld(String documentValueOld) {
		this.documentValueOld = documentValueOld;
	}
	
	public String getDocumentValueNew() {
		return this.documentValueNew;
	}
	
	public void setDocumentValueNew(String documentValueNew) {
		this.documentValueNew = documentValueNew;
	}
	
	public String getDocumentType() {
		return this.documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
