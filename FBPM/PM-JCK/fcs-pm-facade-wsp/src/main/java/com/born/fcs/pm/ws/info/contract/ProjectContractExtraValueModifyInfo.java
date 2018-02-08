/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:00:51 创建
 */
package com.born.fcs.pm.ws.info.contract;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectContractExtraValueModifyInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long contractId;
	
	private Long contractItemId;
	
	private String contractCode;
	
	private Long templateId;
	
	private Long userId;
	
	private String userName;
	
	private Long valueId;
	
	private String documentName;
	
	private String documentDescribe;
	private String documentValueOld;
	
	private String documentValueNew;
	
	private String documentType;
	
	private Date rawAddTime;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDocumentDescribe() {
		return this.documentDescribe;
	}
	
	public void setDocumentDescribe(String documentDescribe) {
		this.documentDescribe = documentDescribe;
	}
	
	public Long getContractId() {
		return this.contractId;
	}
	
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	
	public Long getContractItemId() {
		return this.contractItemId;
	}
	
	public void setContractItemId(Long contractItemId) {
		this.contractItemId = contractItemId;
	}
	
	public String getContractCode() {
		return this.contractCode;
	}
	
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	public Long getTemplateId() {
		return this.templateId;
	}
	
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getValueId() {
		return this.valueId;
	}
	
	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}
	
	public String getDocumentName() {
		return this.documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
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
	
}
