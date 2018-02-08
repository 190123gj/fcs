package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 会议纪要
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -4941110024730147300L;
	/** 会议纪要ID */
	protected Long summaryId;
	/** 表单ID */
	private Long formId;
	/** 会议ID */
	private Long councilId;
	/** 会议编号 */
	private String councilCode;
	/** 会议类型 */
	private String councilType;
	/** 会议召开人ID */
	private Long initiatorId;
	/** 会议召开人账号 */
	private String initiatorAccount;
	/** 会议召开人名称 */
	private String initiatorName;
	/** 概要 */
	private String overview;
	/** 修改的会议纪要编号 */
	private String summaryCode;
	
	@Override
	public void check() {
		validateNotNull(councilId, "会议ID");
		validateHasZore(councilId, "会议ID");
	}
	
	public Long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(Long summaryId) {
		this.summaryId = summaryId;
	}
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public Long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(Long councilId) {
		this.councilId = councilId;
	}
	
	public String getCouncilCode() {
		return this.councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
	public String getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(String councilType) {
		this.councilType = councilType;
	}
	
	public Long getInitiatorId() {
		return this.initiatorId;
	}
	
	public void setInitiatorId(Long initiatorId) {
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
	
	public String getSummaryCode() {
		return this.summaryCode;
	}
	
	public void setSummaryCode(String summaryCode) {
		this.summaryCode = summaryCode;
	}
	
}
