package com.born.fcs.am.ws.info.transfer;

import java.util.Date;

import com.born.fcs.am.ws.enums.LiquidaterStatusEnum;
import com.born.fcs.am.ws.enums.MeetTypeEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

public class FAssetsTransferApplicationInfo extends FormVOInfo {
	
	/**
	 * 资产转让
	 */
	private static final long serialVersionUID = -6991489383740384259L;
	
	private long id;
	
	private long formId;
	
	private String projectCode;
	
	private String projectName;
	
	private Money transferPrice = new Money(0, 0);
	
	private Date transferTime;
	
	private String transfereeCompany;
	
	private BooleanEnum isToMeet;
	
	private MeetTypeEnum meetType;
	
	private BooleanEnum isTrusteeLiquidate;
	
	private Date liquidateTime;
	
	private Money liquidaterPrice = new Money(0, 0);
	
	private String remark;
	
	private String directorsAttach;
	
	private String attach;
	
	private int sortOrder;
	
	private BooleanEnum isCloseMessage;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private long councilApplyId;
	
	private ProjectCouncilStatusEnum councilStatus;
	
	private LiquidaterStatusEnum liquidaterStatus;
	
	private BooleanEnum isCharge;
	
	private BooleanEnum councilBack;
	
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
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Money getTransferPrice() {
		return transferPrice;
	}
	
	public void setTransferPrice(Money transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public Date getTransferTime() {
		return transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public BooleanEnum getIsTrusteeLiquidate() {
		return isTrusteeLiquidate;
	}
	
	public void setIsTrusteeLiquidate(BooleanEnum isTrusteeLiquidate) {
		this.isTrusteeLiquidate = isTrusteeLiquidate;
	}
	
	public Date getLiquidateTime() {
		return liquidateTime;
	}
	
	public void setLiquidateTime(Date liquidateTime) {
		this.liquidateTime = liquidateTime;
	}
	
	public Money getLiquidaterPrice() {
		return liquidaterPrice;
	}
	
	public void setLiquidaterPrice(Money liquidaterPrice) {
		this.liquidaterPrice = liquidaterPrice;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
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
	
	public BooleanEnum getIsCloseMessage() {
		return isCloseMessage;
	}
	
	public void setIsCloseMessage(BooleanEnum isCloseMessage) {
		this.isCloseMessage = isCloseMessage;
	}
	
	public String getTransfereeCompany() {
		return transfereeCompany;
	}
	
	public void setTransfereeCompany(String transfereeCompany) {
		this.transfereeCompany = transfereeCompany;
	}
	
	public BooleanEnum getIsToMeet() {
		return isToMeet;
	}
	
	public void setIsToMeet(BooleanEnum isToMeet) {
		this.isToMeet = isToMeet;
	}
	
	public MeetTypeEnum getMeetType() {
		return meetType;
	}
	
	public void setMeetType(MeetTypeEnum meetType) {
		this.meetType = meetType;
	}
	
	public String getDirectorsAttach() {
		return directorsAttach;
	}
	
	public void setDirectorsAttach(String directorsAttach) {
		this.directorsAttach = directorsAttach;
	}
	
	public long getCouncilApplyId() {
		return councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}
	
	public ProjectCouncilStatusEnum getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(ProjectCouncilStatusEnum councilStatus) {
		this.councilStatus = councilStatus;
	}
	
	public LiquidaterStatusEnum getLiquidaterStatus() {
		return liquidaterStatus;
	}
	
	public void setLiquidaterStatus(LiquidaterStatusEnum liquidaterStatus) {
		this.liquidaterStatus = liquidaterStatus;
	}
	
	public BooleanEnum getIsCharge() {
		return isCharge;
	}
	
	public void setIsCharge(BooleanEnum isCharge) {
		this.isCharge = isCharge;
	}
	
	public BooleanEnum getCouncilBack() {
		return councilBack;
	}
	
	public void setCouncilBack(BooleanEnum councilBack) {
		this.councilBack = councilBack;
	}
	
}
