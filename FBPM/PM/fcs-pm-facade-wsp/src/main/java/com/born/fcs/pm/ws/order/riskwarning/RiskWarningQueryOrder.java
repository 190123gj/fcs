package com.born.fcs.pm.ws.order.riskwarning;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SignalLevelEnum;

/**
 * 查询风险预警信息order
 * 
 * @author lirz
 * 
 * 2016-4-16 上午10:38:11
 */
public class RiskWarningQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = -5821660720164507357L;
	
	private long warningId; //单据编号
	private long customerId; //客户ID
	private String customerName; //客户名称
	private String customerNameFull; //客户名称 用于全匹配查询
	private String signalLevel; //信号等级
	private String submitUserName; //提交人用户名
	private String formStatus; //单据状态
	
	private String warningBillType; //单据状态
	
	public long getWarningId() {
		return warningId;
	}
	
	public void setWarningId(long warningId) {
		this.warningId = warningId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return (null != customerName) ? customerName.trim() : customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerNameFull() {
		return customerNameFull;
	}
	
	public void setCustomerNameFull(String customerNameFull) {
		this.customerNameFull = customerNameFull;
	}
	
	public String getSignalLevel() {
		SignalLevelEnum level = SignalLevelEnum.getByCode(this.signalLevel);
		return (null == level) ? "" : level.code();
	}
	
	public void setSignalLevel(String signalLevel) {
		this.signalLevel = signalLevel;
	}
	
	public String getSubmitUserName() {
		return (null != submitUserName) ? submitUserName.trim() : submitUserName;
	}
	
	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	
	public String getFormStatus() {
		FormStatusEnum status = FormStatusEnum.getByCode(this.formStatus);
		return (null == status) ? "" : status.code();
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getWarningBillType() {
		return this.warningBillType;
	}
	
	public void setWarningBillType(String warningBillType) {
		this.warningBillType = warningBillType;
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
