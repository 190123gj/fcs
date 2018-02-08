package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 
 * @author lirz
 *
 * 2016-3-8 下午2:00:09
 */
public class FInvestigationInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -6117022357268664973L;
	
	private long investigateId; //自增主键
	private String busiType; //业务类型
	private String busiTypeName; //业务类型名称
	private String declares; //申明条件(YNYYY...)
	private String investigatePlace; //调查地点
	private Date investigateDate; //调查日期
	private String investigatePersion; //调查人员
	private String investigatePersionId; //调查人员ID(多个以逗号分隔)
	private String receptionPersion; //客户接待的人员
	private String receptionDuty; //客户接待的人员的职务
	private String review; //复议标识
	private BooleanEnum councilBack; //上会退回
	private ProjectCouncilEnum councilType; //上会类型
	private ProjectCouncilStatusEnum councilStatus; //上会状态
	
	private List<FInvestigationPersonInfo> persons; //调查情况人员
	
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getInvestigateId() {
		return investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getDeclares() {
		return declares;
	}
	
	public void setDeclares(String declares) {
		this.declares = declares;
	}
	
	public String getInvestigatePlace() {
		return investigatePlace;
	}
	
	public void setInvestigatePlace(String investigatePlace) {
		this.investigatePlace = investigatePlace;
	}
	
	public Date getInvestigateDate() {
		return investigateDate;
	}
	
	public void setInvestigateDate(Date investigateDate) {
		this.investigateDate = investigateDate;
	}
	
	public String getInvestigatePersion() {
		return investigatePersion;
	}
	
	public void setInvestigatePersion(String investigatePersion) {
		this.investigatePersion = investigatePersion;
	}
	
	public String getInvestigatePersionId() {
		return investigatePersionId;
	}
	
	public void setInvestigatePersionId(String investigatePersionId) {
		this.investigatePersionId = investigatePersionId;
	}
	
	public String getReceptionPersion() {
		return receptionPersion;
	}
	
	public void setReceptionPersion(String receptionPersion) {
		this.receptionPersion = receptionPersion;
	}
	
	public String getReceptionDuty() {
		return receptionDuty;
	}
	
	public void setReceptionDuty(String receptionDuty) {
		this.receptionDuty = receptionDuty;
	}
	
	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public BooleanEnum getCouncilBack() {
		return this.councilBack;
	}

	public void setCouncilBack(BooleanEnum councilBack) {
		this.councilBack = councilBack;
	}

	public ProjectCouncilEnum getCouncilType() {
		return councilType;
	}
	
	public void setCouncilType(ProjectCouncilEnum councilType) {
		this.councilType = councilType;
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
	
	public ProjectCouncilStatusEnum getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(ProjectCouncilStatusEnum councilStatus) {
		this.councilStatus = councilStatus;
	}

	public List<FInvestigationPersonInfo> getPersons() {
		return this.persons;
	}

	public void setPersons(List<FInvestigationPersonInfo> persons) {
		this.persons = persons;
	}
	
}
