/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.bornsoft.utils.tool;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.bornsoft.utils.exception.BornApiException;

/**
 * @Description:  校验帮助类
 * @author:      xiaohui@yiji.com
 * @date         2016-1-13 下午3:01:17
 * @version:     v1.0
 */
public abstract class ValidateParamUtil {

	/**
	 * 校验String
	 * EzOrderCheckBase.hasText(null)      = true throws异常
	 * EzOrderCheckBase.hasText("")        = true throws异常
	 * EzOrderCheckBase.hasText(" ")       = false
	 * EzOrderCheckBase.hasText("bob")     = false
	 * EzOrderCheckBase.hasText("  bob  ") = false
	 * @param str
	 * @param message
	 * @throws BornApiException
	 */
	public static void hasText(String str, String message) throws BornApiException {
		if (StringUtils.isEmpty(str)) {
			throw new BornApiException(message);
		}
	}

	public static void hasTextV2(String str, String message) throws BornApiException {
		hasText(str, "请求字段[" + message + "]不能为空!");
	}
	
	/**
	 * 校验obecjt
	 * EzOrderCheckBase.notNull(null)      = true, throws异常
	 * EzOrderCheckBase.notNull(other)      = flase
	 * @param obj
	 * @param message
	 * @throws BornApiException
	 */
	public static void notNull(Object obj, String message) throws BornApiException {
		if (obj == null) {
			throw new BornApiException(message);
		}
	}

	/**
	 * 校验boolean
	 * EzOrderCheckBase.hasTrue(true)  无异常
	 * EzOrderCheckBase.hasTrue(false)  throws异常
	 * @param bl
	 * @param message
	 * @throws BornApiException
	 */
	public static void hasTrue(boolean bl, String message) throws BornApiException{
		if (! bl) {
			throw new BornApiException(message);
		}
	}

	/**
	 * 是否double
	 * 1-->true 
	 * w-->false , throws Exception
	 * @param value
	 * @param message
	 * @throws BornApiException
	 */
	public static void hasDouble(String value, String message) throws BornApiException{
		try {
			Double.parseDouble(value);
		} catch (Exception ex) {
			throw new BornApiException(message);
		}
	}

	/**
	 * 基础校验
	 * @param orderNo
	 * @param partnerId
	 */
	public static void checkBaseParameter(String orderNo, String partnerId) throws BornApiException{
		hasText(orderNo, "请求订单号[orderNo]不能为空!");
		hasText(partnerId, "平台商Id[partnerId]不能为空!");
	}

	/**
	 * 校验日期格式以及范围
	 * @param date1
	 * @param date2
	 * @param pattern
	 */
	public static void checkDateRange(String dateStr1, String dateStr2, String pattern) {
		Date date1 = checkDate(dateStr1, pattern);
		Date date2 = checkDate(dateStr2, pattern);
		if (date1.getTime() > date2.getTime()) {
			throw new BornApiException("开始时间不能大于结束时间!");
		}
	}

	/**
	 * 校验日期格式
	 * @param dateStr
	 * @param pattern
	 */
	public static Date checkDate(String dateStr, String pattern){
		try {
			return DateUtils.toDate(dateStr, pattern);
		} catch (Exception ex) {
			throw new BornApiException("时间格式错误[" + dateStr + "]!");
		}
	}
	
	/**
	 * 检查长度
	 * @param str
	 * @param len
	 * @param namePrefix
	 */
	public static void checkStrLength(String str, int len, String namePrefix) {
		if (str != null && str.length() > len) {
			throw new BornApiException(namePrefix + "的字符长度超过了" + len);
		}
	}
	
	/**
	 * 
	 * @param mobile
	 */
	public static void isMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			throw new BornApiException("手机号码不能为空!");
		} else if (StringUtils.isEmpty(mobile) || mobile.length() != 11) {
			throw new BornApiException("错误的手机号码格式[" + mobile + "]!");
		}
	}
}
