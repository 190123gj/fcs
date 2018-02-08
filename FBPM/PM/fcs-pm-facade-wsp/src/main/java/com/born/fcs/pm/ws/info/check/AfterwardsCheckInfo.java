package com.born.fcs.pm.ws.info.check;

import com.born.fcs.pm.ws.enums.CheckReportEditionEnums;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;

/**
 * 
 * 保后检查报告列表
 * 
 * @author lirz
 * 
 * 2016-6-15 下午1:59:32
 */
public class AfterwardsCheckInfo extends SimpleFormProjectVOInfo {
	
	private static final long serialVersionUID = 6618033514134454287L;
	
	private long busiManagerId;
	private String busiManagerName;
	
	private long checkId;
	private CheckReportEditionEnums edition;
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
	
	public CheckReportEditionEnums getEdition() {
		return edition;
	}
	
	public void setEdition(CheckReportEditionEnums edition) {
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
