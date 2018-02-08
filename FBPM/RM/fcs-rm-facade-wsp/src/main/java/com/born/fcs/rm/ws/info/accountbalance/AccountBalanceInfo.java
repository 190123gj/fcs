package com.born.fcs.rm.ws.info.accountbalance;

import java.util.Date;
import java.util.List;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 科目余额列表
 * 
 * @author lirz
 * 
 * 2016-8-4 下午3:33:55
 */
public class AccountBalanceInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -2633037338341120440L;
	
	private long accountBalanceId;
	private int reportYear;
	private int reportMonth;
	private String version;
	private long operatorId;
	private String operatorAccount;
	private String operatorName;
	
	List<AccountBalanceDataInfo> datas;

	private String status;
	
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getAccountBalanceId() {
		return accountBalanceId;
	}
	
	public void setAccountBalanceId(long accountBalanceId) {
		this.accountBalanceId = accountBalanceId;
	}
	
	public int getReportYear() {
		return reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public long getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorAccount() {
		return operatorAccount;
	}
	
	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public List<AccountBalanceDataInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<AccountBalanceDataInfo> datas) {
		this.datas = datas;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
