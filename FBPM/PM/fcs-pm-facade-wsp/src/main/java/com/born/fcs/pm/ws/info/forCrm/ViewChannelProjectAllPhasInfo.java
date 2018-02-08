package com.born.fcs.pm.ws.info.forCrm;

import java.io.Serializable;

/**
 * 项目关联渠道信息info
 * @author ji 2016-9-27
 */
public class ViewChannelProjectAllPhasInfo implements Serializable {
	
	private static final long serialVersionUID = 1035409134126999677L;
	/** 项目编号 */
	private String projectCode;
	/** 渠道id */
	private long capitalChannelId;
	/** 渠道名称 */
	private String capitalChannelName;
	/** 阶段 */
	private long phases;
	/** 阶段名称 */
	private String phasesName;
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getCapitalChannelId() {
		return capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public long getPhases() {
		return phases;
	}
	
	public void setPhases(long phases) {
		this.phases = phases;
	}
	
	public String getPhasesName() {
		return phasesName;
	}
	
	public void setPhasesName(String phasesName) {
		this.phasesName = phasesName;
	}
	
}
