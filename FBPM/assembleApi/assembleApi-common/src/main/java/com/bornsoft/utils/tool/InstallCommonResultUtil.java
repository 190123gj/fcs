package com.bornsoft.utils.tool;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.base.BornRedirectResult;
import com.bornsoft.utils.base.BornResultBase;
import com.bornsoft.utils.constants.BornApiConstants;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;

/**
 * @Description: 构建公共结果返回
 * @author xiaohui@yiji.com
 * @date 2015-8-27 下午3:07:44 
 * @version V1.0
 */
public class InstallCommonResultUtil {
	
	
	public static final String CODE = "code";
	public static final String MESSAGE = "message";

	/**
	 * 构建结果返回基本参数
	 * @param result
	 * @param order
	 * @param success
	 * @param msg
	 * @param resultCode
	 */
	public static void installCommonResult(String msg, CommonResultEnum resultCode, BornResultBase result, BornOrderBase order) {
		result.setOrderNo(order.getOrderNo());
		result.setResultCode(resultCode);
		result.setResultMessage(msg);
	}


	/**
	 * 构建一个默认的成功结果
	 * @param <R>
	 * @param <O>
	 * @param order
	 * @param result
	 */
	public static  void installDefaultSuccessResult(BornOrderBase order, BornResultBase result) {
		result.setOrderNo(order.getOrderNo());
		result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
		result.setResultMessage("执行成功!");
	}

	/**
	 * 构建一个默认的失败结果[没有对应的支付渠道]
	 * @param orderNo
	 * @param result
	 */
	public static void installDefaultFailureResult(BornOrderBase order, BornResultBase result, Exception e) {
		String msg = "";
		if(e!=null && e instanceof BornApiException){
			msg = e.getMessage();
		}
		installDefaultFailureResult(msg,order,result);
	}
	
	/**
	 * 构建一个默认的失败结果[没有对应的支付渠道]
	 * @param orderNo
	 * @param result
	 */
	public static void installDefaultFailureResult(String msg, BornOrderBase order, BornResultBase result) {
		result.setOrderNo(order.getOrderNo());
		result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
		result.setResultMessage(StringUtils.defaultIfBlank(msg, "执行失败"));
	}

	/**
	 * 构建一个跳转失败结果
	 * @param orderNo
	 * @param description
	 * @return
	 */
	public static BornRedirectResult installRedirectFailureResult(String orderNo, String description) {
		BornRedirectResult result = new BornRedirectResult();
		result.setRequestUrl(BornApiConstants.getProjectPath() + "system/failure.html");
		result.setSuccess(true);
		result.getParamMap().put("orderNo", orderNo);
		result.getParamMap().put("description", description);
		result.setResultMessage(description);
		result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
		return result;
	}
	
	/**
	 * 构建一个跳转失败结果
	 * @param orderNo
	 * @param description
	 * @return
	 */
	public static BornRedirectResult installRedirectFailureResult(String orderNo, String description, Exception ex) {
		if (ex instanceof BornApiException) {
			description = ex.getMessage();
		}
		return installRedirectFailureResult(orderNo, description);
	}
	
	/**
	 * APP构建通用响应结果
	 * @param result
	 * @param resultCode
	 * @param message
	 * @return
	 */
	public static JSONObject setAppResult(final JSONObject result ,AppResultCodeEnum resultCode, String message){
		result.put(CODE, resultCode.code());
		result.put(MESSAGE, StringUtils.defaultIfBlank(message, resultCode.message()));
		return result;
	}
	
	/**
	 * APP构建通用响应结果
	 * @param result
	 * @param resultCode
	 * @param message
	 * @return
	 */
	public static JSONObject setAppResult(AppResultCodeEnum resultCode, String message){
		return setAppResult(new JSONObject(), resultCode, message);
	}
}
