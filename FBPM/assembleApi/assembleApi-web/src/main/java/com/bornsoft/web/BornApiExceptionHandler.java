package com.bornsoft.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.utils.constants.BornApiRequestConstants;
import com.bornsoft.utils.constants.BornApiResponseConstants;
import com.bornsoft.utils.enums.BornApiExceptionEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.yjf.common.util.StringUtils;

/**
 * @Description: 异常处理器
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午4:06:27 
 * @version V1.0
 */
@Component("bornApiExceptionHandler")
public class BornApiExceptionHandler implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(BornApiExceptionHandler.class);

	/**
	 * 统一异常处理，返回NOTIFY_ERROR_CODE_KEY和ORDER_NO_KEY
	 *
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		String orderNo = request.getParameter(BornApiRequestConstants.ORDER_NO);
		
		ModelMap mmap = new ModelMap();

		if (ex instanceof BornApiException) {
			logger.error(" 处理 api 请求服务【O:{}】出现错误：", orderNo, ex);

			mmap.put(BornApiResponseConstants.RESULT_MESSAGE, ex.getMessage());
		} else {
			logger.error(" 处理 api 请求服务【O:{}】出现严重异常：", orderNo, ex);

			mmap.put(BornApiResponseConstants.RESULT_CODE, BornApiExceptionEnum.EXECUTE_FAILURE);
			mmap.put(BornApiResponseConstants.RESULT_MESSAGE, ex.getMessage());
		}
		mmap.put("orderNo", orderNo);
		mmap.put("success", "F");
		String serviceName = request.getParameter(BornApiRequestConstants.SERVICE);
		mmap.put("service", serviceName);

		// 没有这个参数
		String errorNotifyUrl = request.getParameter(BornApiRequestConstants.ERROR_NOTIFY_URL);
		if (StringUtils.isEmpty(errorNotifyUrl)) {
			if (isRedirect(serviceName)) {
				logger.info("请求处理异常，返回 JSON 异常结果：{}", mmap);
				ModelAndView modelAndView = new ModelAndView("/bornapi_error.vm");
				modelAndView.addObject("orderNo", orderNo);
				modelAndView.addObject("description", mmap.get(BornApiResponseConstants.RESULT_MESSAGE));
				return modelAndView;
			} else {
				String json = JSON.toJSONString(mmap);
				logger.info("请求处理异常，返回 JSON 异常结果：{}", json);

				ModelAndView modelAndView = new ModelAndView("/dataResult.vm");
				modelAndView.addObject("json_data", json);
				return modelAndView;
			}
		} else {
			// 跳转回请求所指定的errorNotifyUrl地址.
			RedirectView redirectView = new RedirectView(errorNotifyUrl);
			redirectView.setEncodingScheme(request.getCharacterEncoding());

			logger.info("处理 bornapi 请求服务异常，将回传结果：{} 到请求的errorNotifyUrl：{}", mmap, errorNotifyUrl);
			return new ModelAndView(redirectView, mmap);
		}
	}

	private boolean isRedirect(String serviceName) {
		boolean redirect = false;
		if (StringUtils.isNotEmpty(serviceName)) {
			BornApiServiceEnum serviceEnum = BornApiServiceEnum.getByCode(serviceName);
			if (serviceEnum != null) {
				redirect = serviceEnum.isRedirect();
			}
		}
		return redirect;
	}
}
