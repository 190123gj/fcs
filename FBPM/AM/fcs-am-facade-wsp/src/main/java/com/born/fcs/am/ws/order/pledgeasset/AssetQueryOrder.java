/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.pledgeasset;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 资产查询
 * 
 * @author lirz
 * 
 */
public class AssetQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -4798051167612649269L;
	
	private long typeId; // 资产类型
	private String ownershipName; // 所有权人
	private java.util.List<Long> exclusives; // 需要排除的id
	private java.util.List<Long> assetsIdList; // 资产id集合
	
	private String assetType;
	//用户ID
	private Long userId;
	//用户账号
	private String userAccount;
	//用户名称
	private String userName;
	
	private String ralateVideo;//是否有视频监控设备配置
	
	private String assetRemarkInfo;
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public String getOwnershipName() {
		return ownershipName;
	}
	
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}
	
	public java.util.List<Long> getExclusives() {
		return exclusives;
	}
	
	public void setExclusives(java.util.List<Long> exclusives) {
		this.exclusives = exclusives;
	}
	
	public java.util.List<Long> getAssetsIdList() {
		return assetsIdList;
	}
	
	public void setAssetsIdList(java.util.List<Long> assetsIdList) {
		this.assetsIdList = assetsIdList;
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
	
	public String getAssetType() {
		return assetType;
	}
	
	public void setAssetType(String assetType) {
		this.assetType = assetType;
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
