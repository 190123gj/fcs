package com.bornsoft.api.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.bornsoft.utils.base.MerchantInfo;

/**
 * @Description:
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午4:10:48 
 * @version V1.0
 */
public interface IBornApiService {
	
	/**
	 * 服务代码
	 */
	public String getServiceCode();

	/**
	 * 得到服务名称
	 */
	public String getServiceName();

	/**
	 * 核心处理方法
	 * @param orderNo
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	ModelAndView process(String orderNo, HttpServletRequest request, MerchantInfo merchantInfo);
}
