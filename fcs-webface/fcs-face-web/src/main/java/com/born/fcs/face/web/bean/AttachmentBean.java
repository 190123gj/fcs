package com.born.fcs.face.web.bean;

import java.io.Serializable;

import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;

/**
 * 
 * 保存附件
 * 
 * @author lirz
 * 
 * 2017-4-18 上午11:49:21
 */
public class AttachmentBean implements Serializable {
	
	private static final long serialVersionUID = 6444554852310118469L;
	private String bizNo;
	private String childId;
	private String projectCode;
	private String relatedProjectCode;
	private String pathValues;
	private String remark;
	private CommonAttachmentTypeEnum [] types;
	
	public void addTypes(CommonAttachmentTypeEnum... types) {
		this.types = types;
	}
	
	public String getBizNo() {
		return this.bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public String getChildId() {
		return this.childId;
	}
	
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getRelatedProjectCode() {
		return this.relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
	public String getPathValues() {
		return this.pathValues;
	}
	
	public void setPathValues(String pathValues) {
		this.pathValues = pathValues;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CommonAttachmentTypeEnum[] getTypes() {
		return this.types;
	}

	public void setTypes(CommonAttachmentTypeEnum[] types) {
		this.types = types;
	}

}
