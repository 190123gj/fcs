package com.born.fcs.pm.ws.info.expireproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;

/**
 * 项目到期列表
 * 
 * @author lirz
 *
 * 2016-4-21 下午6:12:24
 */
public class ExpireFormProjectInfo extends ProjectInfo {
	
	private static final long serialVersionUID = -8537953880311594685L;
	
	private long expireProjectId;
	private Date expireDate;
	private ExpireProjectStatusEnum expireStatus;

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
	
	public ExpireProjectStatusEnum getExpireStatus() {
		return expireStatus;
	}
	
	public void setExpireStatus(ExpireProjectStatusEnum expireStatus) {
		this.expireStatus = expireStatus;
	}
	
}
