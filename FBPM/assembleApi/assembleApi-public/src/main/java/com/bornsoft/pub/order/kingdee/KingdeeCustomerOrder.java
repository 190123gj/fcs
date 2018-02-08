package com.bornsoft.pub.order.kingdee;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 同步客户信息
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午10:50:20
 * @version:     v1.0
 */
public class KingdeeCustomerOrder extends BornOrderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 客户编号 **/
	private String customerCode;
	/** 客户名称 **/
	private String customerName;
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(customerCode, "customerCode");
		ValidateParamUtil.hasTextV2(customerName, "customerName");
	}
	
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
