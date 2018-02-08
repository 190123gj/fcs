package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;

/**
 * 复议申请表单信息
 *
 * @author wuzj
 *
 */
public class ReCouncilApplyFormInfo extends SimpleFormProjectVOInfo {
	
	private static final long serialVersionUID = 8332023284398988165L;
	
	private long applyId;
	
	private long oldSpId;
	
	private String oldSpCode;
	
	private String contentReason;
	
	private String overview;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private long investigateId; //尽职调查ID
	
	private long investigateFormId;
	
	//IS : 尽职复议但未填写 ，  YES：复议  ， NO：非复议
	private String investigateReview;
	
	//上会申请ID
	private long councilApplyId;
	
	//是否上会退回
	private BooleanEnum councilBack;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getOldSpId() {
		return this.oldSpId;
	}
	
	public void setOldSpId(long oldSpId) {
		this.oldSpId = oldSpId;
	}
	
	public String getOldSpCode() {
		return this.oldSpCode;
	}
	
	public void setOldSpCode(String oldSpCode) {
		this.oldSpCode = oldSpCode;
	}
	
	public String getContentReason() {
		return this.contentReason;
	}
	
	public void setContentReason(String contentReason) {
		this.contentReason = contentReason;
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
	
	public long getInvestigateId() {
		return this.investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
	}
	
	public long getInvestigateFormId() {
		return this.investigateFormId;
	}
	
	public void setInvestigateFormId(long investigateFormId) {
		this.investigateFormId = investigateFormId;
	}
	
	public String getInvestigateReview() {
		return this.investigateReview;
	}
	
	public void setInvestigateReview(String investigateReview) {
		this.investigateReview = investigateReview;
	}
	
	public long getCouncilApplyId() {
		return this.councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}
	
	public BooleanEnum getCouncilBack() {
		return this.councilBack;
	}
	
	public void setCouncilBack(BooleanEnum councilBack) {
		this.councilBack = councilBack;
	}
	
}
