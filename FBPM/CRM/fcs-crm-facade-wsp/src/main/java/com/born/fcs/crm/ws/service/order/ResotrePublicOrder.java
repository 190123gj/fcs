package com.born.fcs.crm.ws.service.order;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 还原成公海客户
 * 
 * @author wuzj
 */
public class ResotrePublicOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1855714925521951230L;
	
	/** 客户户Id */
	private long userId;
	
	@Override
	public void check() {
		validateHasZore(userId, "客户Id");
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
