package com.born.fcs.am.ws.order.pledgeasset;

import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 资产项目关系绑定Order
 * 
 * @author wuzj
 */
public class AssetRelationProjectBindOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 2705586536285981347L;
	
	// 项目编号
	private String projectCode;
	// 项目名称
	private String projectName;
	// 客户id
	private long customerId;
	// 客户名称
	private String customerName;
	// 业务类型
	private String busiType;
	// 业务类型名称
	private String busiTypeName;
	//资产ID对应状态
	List<AssetStatusOrder> assetList;
	//是否删除原来关联的资产
	private boolean delOld;
	
	@Override
	public void check() {
		validateNotNull(projectCode, "项目编号");
		if (delOld) {
			if (assetList != null && assetList.size() > 0) {
				for (AssetStatusOrder as : assetList) {
					as.check();
				}
			}
		} else {
			Assert.isTrue(assetList != null && assetList.size() > 0, "资产不能为空");
			for (AssetStatusOrder as : assetList) {
				as.check();
			}
		}
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
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	public boolean isDelOld() {
		return delOld;
	}
	
	public void setDelOld(boolean delOld) {
		this.delOld = delOld;
	}
	
	public List<AssetStatusOrder> getAssetList() {
		return assetList;
	}
	
	public void setAssetList(List<AssetStatusOrder> assetList) {
		this.assetList = assetList;
	}
	
}
