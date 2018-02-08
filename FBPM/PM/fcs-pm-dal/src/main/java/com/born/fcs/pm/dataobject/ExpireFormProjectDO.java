package com.born.fcs.pm.dataobject;

import java.util.Date;

/**
 * 项目到期列表
 * 
 * @author lirz
 * 
 * 2016-4-21 下午6:12:19
 */
public class ExpireFormProjectDO extends com.born.fcs.pm.dal.dataobject.ProjectDO {
	
	private static final long serialVersionUID = 2728104889345818442L;
	
	private long expireProjectId;
	private Date expireDate;
	private String expireStatus;
	private String repayCertificate;

	private String templateProjectCode;

	private long templateId;

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateProjectCode() {
		return templateProjectCode;
	}

	public void setTemplateProjectCode(String templateProjectCode) {
		this.templateProjectCode = templateProjectCode;
	}

	public String getRepayCertificate() {
		return repayCertificate;
	}

	public void setRepayCertificate(String repayCertificate) {
		this.repayCertificate = repayCertificate;
	}

	public long getExpireProjectId() {
		return expireProjectId;
	}
	
	public void setExpireProjectId(long expireProjectId) {
		this.expireProjectId = expireProjectId;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public String getExpireStatus() {
		return expireStatus;
	}
	
	public void setExpireStatus(String expireStatus) {
		this.expireStatus = expireStatus;
	}
	
}
