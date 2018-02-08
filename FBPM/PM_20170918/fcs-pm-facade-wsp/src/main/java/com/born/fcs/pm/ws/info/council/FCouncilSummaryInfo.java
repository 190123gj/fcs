package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 会议纪要
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -4777616993875450817L;
	/** 会议信息 */
	private CouncilInfo council;
	/** 会议纪要ID */
	private long summaryId;
	/** 会议纪要编号 */
	private String summaryCode;
	/** 表单ID */
	private long formId;
	/** 会议ID */
	private long councilId;
	/** 会议编号 */
	private String councilCode;
	/** 会议类型 */
	private CouncilTypeEnum councilType;
	/** 会议召开人ID */
	private long initiatorId;
	/** 会议召开人账号 */
	private String initiatorAccount;
	/** 会议召开人名称 */
	private String initiatorName;
	/** 概要 */
	private String overview;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public CouncilInfo getCouncil() {
		return this.council;
	}
	
	public void setCouncil(CouncilInfo council) {
		this.council = council;
	}
	
	public CouncilTypeEnum getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(CouncilTypeEnum councilType) {
		this.councilType = councilType;
	}
	
	public long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(long summaryId) {
		this.summaryId = summaryId;
	}
	
	public String getSummaryCode() {
		return this.summaryCode;
	}
	
	public void setSummaryCode(String summaryCode) {
		this.summaryCode = summaryCode;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public long getInitiatorId() {
		return this.initiatorId;
	}
	
	public void setInitiatorId(long initiatorId) {
		this.initiatorId = initiatorId;
	}
	
	public String getInitiatorAccount() {
		return this.initiatorAccount;
	}
	
	public void setInitiatorAccount(String initiatorAccount) {
		this.initiatorAccount = initiatorAccount;
	}
	
	public String getInitiatorName() {
		return this.initiatorName;
	}
	
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
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
}
