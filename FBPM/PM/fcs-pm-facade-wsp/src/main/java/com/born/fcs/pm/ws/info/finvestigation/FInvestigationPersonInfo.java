package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 尽调申明-调查人员
 * 
 * @author lirz
 * 
 * 2016-9-14 下午5:12:22
 * 
 */
public class FInvestigationPersonInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1859805751496733232L;
	
	//========== properties ==========
	
	private long id;
	
	private long formId;
	
	private Date investigateDate;
	
	private String investigatePlace;
	
	private long mainInvestigatorId;
	
	private String mainInvestigatorAccount;
	
	private String mainInvestigatorName;
	
	private String assistInvestigatorId;
	
	private String assistInvestigatorName;
	
	private String receptionPersion;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public Date getInvestigateDate() {
		return investigateDate;
	}
	
	public void setInvestigateDate(Date investigateDate) {
		this.investigateDate = investigateDate;
	}
	
	public String getInvestigatePlace() {
		return investigatePlace;
	}
	
	public void setInvestigatePlace(String investigatePlace) {
		this.investigatePlace = investigatePlace;
	}
	
	public long getMainInvestigatorId() {
		return mainInvestigatorId;
	}
	
	public void setMainInvestigatorId(long mainInvestigatorId) {
		this.mainInvestigatorId = mainInvestigatorId;
	}
	
	public String getMainInvestigatorAccount() {
		return mainInvestigatorAccount;
	}
	
	public void setMainInvestigatorAccount(String mainInvestigatorAccount) {
		this.mainInvestigatorAccount = mainInvestigatorAccount;
	}
	
	public String getMainInvestigatorName() {
		return mainInvestigatorName;
	}
	
	public void setMainInvestigatorName(String mainInvestigatorName) {
		this.mainInvestigatorName = mainInvestigatorName;
	}
	
	public String getAssistInvestigatorId() {
		return assistInvestigatorId;
	}
	
	public void setAssistInvestigatorId(String assistInvestigatorId) {
		this.assistInvestigatorId = assistInvestigatorId;
	}
	
	public String getAssistInvestigatorName() {
		return assistInvestigatorName;
	}
	
	public void setAssistInvestigatorName(String assistInvestigatorName) {
		this.assistInvestigatorName = assistInvestigatorName;
	}
	
	public String getReceptionPersion() {
		return receptionPersion;
	}
	
	public void setReceptionPersion(String receptionPersion) {
		this.receptionPersion = receptionPersion;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	
}
