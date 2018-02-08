
package com.born.fcs.pm.ws.order.expireproject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 保后及等级评定
 *
 * @author lirz
 * 
 * 2016-7-29 上午10:41:46
 *
 */
public class MessageAlertOrder extends ValidateOrderBase{

	private static final long serialVersionUID = -6926015489455561499L;

	private long alertId;
	private String projectCode;
	private String customerName;
	private String alertPhrase;
	

	@Override
	public void check() {
		super.check();
		validateHasText(projectCode, "项目编号");
//		validateHasText(customerName, "客户名称");
		validateHasText(alertPhrase, "提醒阶段");
	}

	public long getAlertId() {
		return alertId;
	}
	
	public void setAlertId(long alertId) {
		this.alertId = alertId;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAlertPhrase() {
		return alertPhrase;
	}
	
	public void setAlertPhrase(String alertPhrase) {
		this.alertPhrase = alertPhrase;
	}

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
