package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 会议信息 - 会议评委
 * 
 * 
 * @author Fei
 * 
 */
public class CouncilJudgeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -2368288752705454448L;
	
	private long id;
	
	private long councilId;
	
	private String councilCode;
	
	private long judgeId;
	
	private String judgeName;
	
	private BooleanEnum compere;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public BooleanEnum getCompere() {
		return this.compere;
	}
	
	public void setCompere(BooleanEnum compere) {
		this.compere = compere;
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
	
	public long getJudgeId() {
		return this.judgeId;
	}
	
	public void setJudgeId(long judgeId) {
		this.judgeId = judgeId;
	}
	
	public String getJudgeName() {
		return this.judgeName;
	}
	
	public void setJudgeName(String judgeName) {
		this.judgeName = judgeName;
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
	
}
