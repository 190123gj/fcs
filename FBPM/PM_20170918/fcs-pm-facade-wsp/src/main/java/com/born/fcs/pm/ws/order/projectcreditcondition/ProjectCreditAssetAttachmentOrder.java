package com.born.fcs.pm.ws.order.projectcreditcondition;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 项目授信条件-资产附件上传Order
 *
 *
 * @author Ji
 *
 */
public class ProjectCreditAssetAttachmentOrder extends ProcessOrder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4013417760456792614L;
	//主键id
	private long id;
	//资产id
	private long assetId;
	//授信落实表中对应的id
	private long creditId;
	//资产-图像信息对应key值
	private String imageKey;
	//资产-图像信息对应value值
	private String imageValue;
	//资产-图像信息对应value值(文本)
	private String imageTextValue;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getAssetId() {
		return assetId;
	}
	
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
	public long getCreditId() {
		return creditId;
	}
	
	public void setCreditId(long creditId) {
		this.creditId = creditId;
	}
	
	public String getImageKey() {
		return imageKey;
	}
	
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	
	public String getImageValue() {
		return imageValue;
	}
	
	public void setImageValue(String imageValue) {
		this.imageValue = imageValue;
	}
	
	public String getImageTextValue() {
		return imageTextValue;
	}
	
	public void setImageTextValue(String imageTextValue) {
		this.imageTextValue = imageTextValue;
	}
	
}
