package com.bornsoft.utils.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 基础订单[get方法返回类型必须是String]
 * @author: xiaohui@yiji.com
 * @date 2016-1-13 下午3:01:52
 * @version: v1.0
 */
public abstract class BornOrderBase implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 6582008834373791346L;
	/***************************************************** 基础字段 ************************************************/
	/** 流水号 **/
	private String orderNo;
	/** 异步通知地址[预留] **/
	private String notifyUrl;
	/** 同步通知地址 **/
	private String returnUrl;
	
	public  boolean isRedirect(){
		return false;
	}
	
	public  String getService(){
		return "";
	}
	
	/**
	 * 公共校验方法,校验失败则抛出异常
	 * 
	 * @throws Exception
	 */
	public void validateOrder() throws BornApiException {
		ValidateParamUtil.hasTextV2(orderNo, "orderNo");
	}
	
	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the notifyUrl
	 */
	public String getNotifyUrl() {
		return notifyUrl;
	}

	/**
	 * @param notifyUrl
	 *            the notifyUrl to set
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	/**
	 * @return the returnUrl
	 */
	public String getReturnUrl() {
		return returnUrl;
	}

	/**
	 * @param returnUrl
	 *            the returnUrl to set
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
