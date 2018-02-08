package com.bornsoft.utils.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.bornsoft.utils.enums.CommonResultEnum;

/**
 * @Description: 跳转页面通用结果
 * @author:      xiaohui@yiji.com
 * @date         2016-1-18 下午12:27:36
 * @version:     v1.0
 */
public class BornRedirectResult extends BornResultBase{

	/**
	 */
	private static final long serialVersionUID = 3200820193712309962L;

	/** 跳转的参数 **/
	private Map<String, String> paramMap = null;
	/** 跳转地址 **/
	private String requestUrl;
	/** 跳转地址**/
	private String toUrl;
	/** 退出地址**/
	private String logOutUrl;
	/** 跳转方式 **/
	private String method = "post";
	/** 构建是否成功 **/
	private boolean isSuccess = false;
	
	public BornRedirectResult() {
		this(true);
	}
	
	public BornRedirectResult(boolean install) {
		if (install) {
			paramMap = new HashMap<String, String>();
		}
	}
	
	public void setRedirectResult(boolean success,String requestUrl, Map<String, String> paramMap){
		this.isSuccess = success;
		if(StringUtils.isNotBlank(requestUrl)){
			this.requestUrl = requestUrl;
		}
		if(paramMap !=null){
			this.paramMap = paramMap;
		}
	}
	
	public void setRedirectResult(CommonResultEnum result,String requestUrl, Map<String, String> paramMap){
		this.setResultCode(result);
		this.setResultCode(result.message());
		this.setSuccess(result == CommonResultEnum.EXECUTE_SUCCESS);
		if(StringUtils.isNotBlank(requestUrl)){
			this.requestUrl = requestUrl;
		}
		if(paramMap !=null){
			this.paramMap = paramMap;
		}
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	

	public String getToUrl() {
		return toUrl;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}

	public String getLogOutUrl() {
		return logOutUrl;
	}

	public void setLogOutUrl(String logOutUrl) {
		this.logOutUrl = logOutUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
