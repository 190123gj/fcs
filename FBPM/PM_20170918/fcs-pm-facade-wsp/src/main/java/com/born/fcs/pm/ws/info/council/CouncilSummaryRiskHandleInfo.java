package com.born.fcs.pm.ws.info.council;

import java.util.Date;

/**
 * 会议纪要 - 风险处置会 - 处置方案
 *
 * @author wuzj
 *
 */
public class CouncilSummaryRiskHandleInfo extends FCouncilSummaryRiskHandleInfo {
	
	private static final long serialVersionUID = -8548936956181289649L;
	/** 会议纪要FormID */
	private long summaryFormId;
	/** 会议ID */
	private long councilId;
	/** 会议纪要编号 */
	private String summaryCode;
	/** 会议开始时间 */
	private Date councilBeginTime;
	/** 会议主题 */
	private String councilSubject;
	
	public long getSummaryFormId() {
		return this.summaryFormId;
	}
	
	public void setSummaryFormId(long summaryFormId) {
		this.summaryFormId = summaryFormId;
	}
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public String getSummaryCode() {
		return this.summaryCode;
	}
	
	public void setSummaryCode(String summaryCode) {
		this.summaryCode = summaryCode;
	}
	
	public Date getCouncilBeginTime() {
		return this.councilBeginTime;
	}
	
	public void setCouncilBeginTime(Date councilBeginTime) {
		this.councilBeginTime = councilBeginTime;
	}
	
	public String getCouncilSubject() {
		return this.councilSubject;
	}
	
	public void setCouncilSubject(String councilSubject) {
		this.councilSubject = councilSubject;
	}
}
