package com.born.fcs.am.ws.info.pledgeasset;

import java.util.Date;
import java.util.List;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeInfo;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class PledgeAssetInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793393213347472054L;
	
	private long assetsId;
	
	private long typeId;
	
	private String assetType;
	
	private String assetTypeDesc;
	
	private String projectCode;
	
	private String projectName;
	
	private long ownershipId;
	
	private String ownershipName;
	
	private String isCustomer;
	
	private String warrantNo;
	
	private Money evaluationPrice = new Money(0, 0);
	
	private AssetStatusEnum status;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String longitude;
	
	private String latitude;
	
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private String searchKey;
	
	private String ralateVideo;
	
	private List<PledgeTypeAttributeInfo> pledgeTypeAttributeInfos;
	
	private String isHasApproval;
	
	private String assetRemarkInfo;//资产关键信息
	
	private String remark;

	private String attach;
	
	public long getAssetsId() {
		return assetsId;
	}
	
	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public String getAssetType() {
		return assetType;
	}
	
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	
	public String getAssetTypeDesc() {
		return assetTypeDesc;
	}
	
	public void setAssetTypeDesc(String assetTypeDesc) {
		this.assetTypeDesc = assetTypeDesc;
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
	
	public long getOwnershipId() {
		return ownershipId;
	}
	
	public void setOwnershipId(long ownershipId) {
		this.ownershipId = ownershipId;
	}
	
	public String getOwnershipName() {
		return ownershipName;
	}
	
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}
	
	public String getIsCustomer() {
		return isCustomer;
	}
	
	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}
	
	public String getWarrantNo() {
		return warrantNo;
	}
	
	public void setWarrantNo(String warrantNo) {
		this.warrantNo = warrantNo;
	}
	
	public Money getEvaluationPrice() {
		return evaluationPrice;
	}
	
	public void setEvaluationPrice(Money evaluationPrice) {
		this.evaluationPrice = evaluationPrice;
	}
	
	public AssetStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(AssetStatusEnum status) {
		this.status = status;
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
	
	public List<PledgeTypeAttributeInfo> getPledgeTypeAttributeInfos() {
		return pledgeTypeAttributeInfos;
	}
	
	public void setPledgeTypeAttributeInfos(List<PledgeTypeAttributeInfo> pledgeTypeAttributeInfos) {
		this.pledgeTypeAttributeInfos = pledgeTypeAttributeInfos;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getSearchKey() {
		return searchKey;
	}
	
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public String getRalateVideo() {
		return ralateVideo;
	}
	
	public void setRalateVideo(String ralateVideo) {
		this.ralateVideo = ralateVideo;
	}
	
	public String getIsHasApproval() {
		return isHasApproval;
	}
	
	public void setIsHasApproval(String isHasApproval) {
		this.isHasApproval = isHasApproval;
	}
	
	public String getAssetRemarkInfo() {
		return assetRemarkInfo;
	}
	
	public void setAssetRemarkInfo(String assetRemarkInfo) {
		this.assetRemarkInfo = assetRemarkInfo;
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
}
