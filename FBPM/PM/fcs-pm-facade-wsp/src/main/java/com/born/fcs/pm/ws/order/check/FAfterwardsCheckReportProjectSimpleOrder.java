package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 开发项目完成情况检查
 * 
 * @author lirz
 * 
 * 2016-6-7 下午2:02:16
 */
public class FAfterwardsCheckReportProjectSimpleOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -2479623738910003428L;
	
	private long id;
	private long formId;
	private String projectName;
	private String projectType;
	private String attachmentInfo;
	
	private int sortOrder;
	
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
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
