package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class CouncilParticipantInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 3778453025376150946L;
	
	private long id;
	
	private long councilId;
	
	private String councilCode;
	
	private long participantId;
	
	private String participantName;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public String getCouncilCode() {
		return this.councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
	public long getParticipantId() {
		return this.participantId;
	}
	
	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}
	
	public String getParticipantName() {
		return this.participantName;
	}
	
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (obj instanceof CouncilParticipantInfo) {
			CouncilParticipantInfo user = (CouncilParticipantInfo) obj;
			if (user.participantId == this.participantId
				&& user.participantName.equals(this.participantName))
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(participantId).hashCode() * participantName.hashCode();
	}
	
}
