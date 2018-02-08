package com.born.fcs.pm.ws.order.project;

import java.util.Date;

/**
 * 
 * 更新项目复议
 * 
 * @author lirz
 * 
 * 2016-9-26 下午4:23:18
 * 
 */
public class UpdateProjectRecouncilOrder extends UpdateProjectBaseOrder {
	
	private static final long serialVersionUID = -4137328235512061227L;
	
	private String isRecouncil;
	private Date lastRecouncilTime;
	private String phases;
	private String phasesStatus;
	private String status;
	
	public String getIsRecouncil() {
		return this.isRecouncil;
	}
	
	public void setIsRecouncil(String isRecouncil) {
		this.isRecouncil = isRecouncil;
	}
	
	public Date getLastRecouncilTime() {
		return this.lastRecouncilTime;
	}
	
	public void setLastRecouncilTime(Date lastRecouncilTime) {
		this.lastRecouncilTime = lastRecouncilTime;
	}
	
	public String getPhases() {
		return this.phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}
	
	public String getPhasesStatus() {
		return this.phasesStatus;
	}
	
	public void setPhasesStatus(String phasesStatus) {
		this.phasesStatus = phasesStatus;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
