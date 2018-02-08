package com.born.fcs.pm.ws.info.check;

import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;

/**
 * 开发项目完成情况检查
 * 
 * @author lirz
 * 
 * 2016-6-7 下午2:02:16
 */
public class FAfterwardsCheckReportProjectSimpleInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1270615499815478304L;
	
	private long id;
	private long formId;
	private String projectName;
	private String projectType;
	private String attachmentInfo;
	private String delAble;
	private int checkStatus;
	//附件列表
	private List<CommonAttachmentInfo> attachments;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectType() {
		return projectType;
	}
	
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	public String getAttachmentInfo() {
		return this.attachmentInfo;
	}
	
	public void setAttachmentInfo(String attachmentInfo) {
		this.attachmentInfo = attachmentInfo;
	}
	
	public String getDelAble() {
		return this.delAble;
	}
	
	public void setDelAble(String delAble) {
		this.delAble = delAble;
	}
	
	public int getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public List<CommonAttachmentInfo> getAttachments() {
		return this.attachments;
	}
	
	public void setAttachments(List<CommonAttachmentInfo> attachments) {
		this.attachments = attachments;
	}
	
}
