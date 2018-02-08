package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 复议申请信息
 *
 * @author wuzj
 *
 */
public class FReCouncilApplyInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1954328572434889073L;
	
	private long id;
	
	private long formId;
	
	private String projectCode;
	
	private long oldSpId;
	
	private String oldSpCode;
	
	private String contentReason;
	
	private String overview;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getOldSpId() {
		return this.oldSpId;
	}
	
	public void setOldSpId(long oldSpId) {
		this.oldSpId = oldSpId;
	}
	
	public String getOldSpCode() {
		return this.oldSpCode;
	}
	
	public void setOldSpCode(String oldSpCode) {
		this.oldSpCode = oldSpCode;
	}
	
	public String getContentReason() {
		return this.contentReason;
	}
	
	public void setContentReason(String contentReason) {
		this.contentReason = contentReason;
	}
	
	public String getOverview() {
		return this.overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
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
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
