/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.pledgeasset;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 抵质押品分类Order
 *
 * @author jil
 *
 */
public class PledgeAssetQueryOrder extends QueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8233757401359846573L;
	
	private Long assetsId;
	
	private long typeId;
	
	private String assetType;
	
	private String assetTypeDesc;
	
	private String projectCode;
	
	private long ownershipId;
	
	private String ownershipName;
	
	private String isCustomer;
	
	private String warrantNo;
	
	private String assetRemarkInfo;
	
	private String status;
	
	private List<Long> assetsIdList;
	//用户ID
	protected Long userId;
	//用户账号
	protected String userAccount;
	//用户名称
	protected String userName;
	
	private String ralateVideo;//是否有视频监控设备配置
	
	public Long getAssetsId() {
		return assetsId;
	}
	
	public void setAssetsId(Long assetsId) {
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
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
	
	public List<Long> getAssetsIdList() {
		return assetsIdList;
	}
	
	public void setAssetsIdList(List<Long> assetsIdList) {
		this.assetsIdList = assetsIdList;
	}
	
	public String getRalateVideo() {
		return ralateVideo;
	}
	
	public void setRalateVideo(String ralateVideo) {
		this.ralateVideo = ralateVideo;
	}
	
	public String getAssetRemarkInfo() {
		return assetRemarkInfo;
	}
	
	public void setAssetRemarkInfo(String assetRemarkInfo) {
		this.assetRemarkInfo = assetRemarkInfo;
	}
	
}
