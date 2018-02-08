package com.bornsoft.api.service.app.orders;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.face.integration.bpm.service.order.LoginOrder;

/**
 * @Description: app 登录order
 * @author taibai@yiji.com
 * @date 2016-9-22 下午3:32:18
 * @version V1.0
 */
public class AppLoginOrder extends LoginOrder {

	/**
	 */
	private static final long serialVersionUID = -5952234851561961973L;
	
	private String deviceNo;
	private String token;
	private boolean createToken = true;
	private String umDeviceNo;

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	public boolean isCreateToken() {
		return createToken;
	}

	public void setCreateToken(boolean createToken) {
		this.createToken = createToken;
	}

	public String getUmDeviceNo() {
		return umDeviceNo;
	}

	public void setUmDeviceNo(String umDeviceNo) {
		this.umDeviceNo = umDeviceNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
