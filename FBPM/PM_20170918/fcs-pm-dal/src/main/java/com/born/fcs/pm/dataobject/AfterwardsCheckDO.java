package com.born.fcs.pm.dataobject;

/**
 * 
 * 保后检查报告列表DO
 * 
 * @author lirz
 * 
 * 2016-6-13 下午5:41:49
 */
public class AfterwardsCheckDO extends SimpleFormProjectDO {
	
	private static final long serialVersionUID = 2188136285775576577L;
	
	private long busiManagerId;
	private String busiManagerName;
	
	private long checkId;
	private String edition;
	private int roundYear;
	private int roundTime;
	
	public long getBusiManagerId() {
		return busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public long getCheckId() {
		return checkId;
	}
	
	public void setCheckId(long checkId) {
		this.checkId = checkId;
	}
	
	public String getEdition() {
		return edition;
	}
	
	public void setEdition(String edition) {
		this.edition = edition;
	}

	public int getRoundYear() {
		return roundYear;
	}

	public void setRoundYear(int roundYear) {
		this.roundYear = roundYear;
	}

	public int getRoundTime() {
		return roundTime;
	}

	public void setRoundTime(int roundTime) {
		this.roundTime = roundTime;
	}
	
	
}
