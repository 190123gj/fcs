/**
 * 
 */
package com.born.fcs.pm.ws.info.contract;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * @author jiajie
 * 
 */
public class ProjectContractExtraValueInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private long contractId;
	
	private long contractItemId;
	
	private String contractCode;
	
	private long templateId;
	
	private String documentName;
	private String documentDescribe;
	
	private String documentValue;
	
	private String documentType;
	
	private int documentModifyNum;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDocumentDescribe() {
		return this.documentDescribe;
	}
	
	public void setDocumentDescribe(String documentDescribe) {
		this.documentDescribe = documentDescribe;
	}
	
	public long getContractId() {
		return contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	public long getContractItemId() {
		return contractItemId;
	}
	
	public void setContractItemId(long contractItemId) {
		this.contractItemId = contractItemId;
	}
	
	public String getContractCode() {
		return contractCode;
	}
	
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	public long getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public String getDocumentValue() {
		return documentValue;
	}
	
	public void setDocumentValue(String documentValue) {
		this.documentValue = documentValue;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public int getDocumentModifyNum() {
		return documentModifyNum;
	}
	
	public void setDocumentModifyNum(int documentModifyNum) {
		this.documentModifyNum = documentModifyNum;
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
