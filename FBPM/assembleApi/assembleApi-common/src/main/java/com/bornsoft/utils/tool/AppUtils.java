package com.bornsoft.utils.tool;

import javax.servlet.http.HttpServletRequest;

import com.bornsoft.utils.constants.BornApiConstants;
import com.yjf.common.lang.util.money.Money;

public class AppUtils {
	
	public static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	
	/**
	 * 版本号相关格式化金额
	 * @param m
	 * @return
	 */
	public static String toString(Money m){
		if(m==null){
			return "0";
		}
		if(getAppVersion()>=9){
			return m.toStandardString();
		}else{
			return m.toString();
		}
	}
	
	/**
	 * 获取当前请求接口版本号,默认为8
	 * @return
	 */
	public static int getAppVersion(){
		if(request.get()!=null){
			return BornApiRequestUtils.getParamterInteger(request.get(), BornApiConstants.VERSION,8);
		}else{
			//默认为8
			return 8;
		}
	}
}
