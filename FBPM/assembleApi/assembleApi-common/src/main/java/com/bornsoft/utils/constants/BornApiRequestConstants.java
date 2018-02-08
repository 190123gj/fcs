package com.bornsoft.utils.constants;

/**
 * @Description: 请求常量类
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午1:57:34 
 * @version V1.0
 */
public interface BornApiRequestConstants {
	/**
	 * 服务名称空间
	 */
	String SERVICE_NAMESPACE = "snas";
	
	/**
	 * 服务名
	 */
	String SERVICE = "service";

	/**
	 * 合作伙伴id
	 */
	String PARTNER_ID = "partnerId";

	/**
	 * 用户ID
	 */
	String USER_ID = "userId";

	/**
	 * 接入渠道订单标识号
	 */
	String ORDER_NO = "orderNo";

	/**
	 * 服务器异步通知页面路径key。
	 * 易极付服务器主动通知商户网站里指定的页面 http 路径。
	 */
	String NOTIFY_URL = "notifyUrl";

	/**
	 * 跳转后返回的页面
	 */
	String RETURN_URL = "returnUrl";

	/**
	 * 请求处理错误后调用地址key
	 */
	String ERROR_NOTIFY_URL = "errorNotifyUrl";

	/**
	 * 签字类型
	 */
	String SIGN_TYPE = "signType";

	/**
	 * 签字值
	 */
	String SIGN = "sign";

}
