package com.born.fcs.am.ws.order.pledgeasset;

import java.util.Date;
import java.util.List;

import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeOrder;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 资产Order
 *
 * @author jil
 *
 */
public class PledgeAssetOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 7909326623014702381L;
	
	private Long assetsId;
	
	private long typeId;
	
	private String assetType;
	
	private String assetTypeDesc;
	
	private String projectCode;
	
	private String projectName;
	
	private long ownershipId;
	
	private String ownershipName;
	
	private String isCustomer;
	
	private String warrantNo;
	
	private double evaluationPrice;
	
	private String status;
	
	private String remark;

	private String attach;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String longitude;// 经度
	
	private String latitude;// 纬度
	
	private String searchKey;
	
	private String ralateVideo;
	private List<PledgeTypeAttributeOrder> pledgeTypeAttributeOrders;
	
	private String isView;//用来标示是否从详情页面来的编辑
	
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
	
	public double getEvaluationPrice() {
		return evaluationPrice;
	}
	
	public void setEvaluationPrice(double evaluationPrice) {
		this.evaluationPrice = evaluationPrice;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
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
	
	public List<PledgeTypeAttributeOrder> getPledgeTypeAttributeOrders() {
		return pledgeTypeAttributeOrders;
	}
	
	public void setPledgeTypeAttributeOrders(	List<PledgeTypeAttributeOrder> pledgeTypeAttributeOrders) {
		this.pledgeTypeAttributeOrders = pledgeTypeAttributeOrders;
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
	
	public String getIsView() {
		return isView;
	}
	
	public void setIsView(String isView) {
		this.isView = isView;
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
