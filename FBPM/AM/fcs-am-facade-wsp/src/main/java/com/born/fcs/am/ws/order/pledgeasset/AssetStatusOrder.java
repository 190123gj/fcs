package com.born.fcs.am.ws.order.pledgeasset;

import org.springframework.util.Assert;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 资产对应状态
 * @author wuzj
 */
public class AssetStatusOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4132721441688568957L;
	
	//资产ID
	private long assetId;
	//资产状态
	private AssetStatusEnum status;
	
	public long getAssetId() {
		return assetId;
	}
	
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
	public AssetStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(AssetStatusEnum status) {
		this.status = status;
	}
	
	@Override
	public void check() {
		validateNotNull(status, "资产状态");
		Assert.isTrue(assetId > 0, "资产ID不能为空");
	}
}
