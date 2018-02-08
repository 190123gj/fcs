package com.bornsoft.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bornsoft.api.service.IBornApiService;
import com.bornsoft.api.service.IBornApiServiceProvider;
import com.bornsoft.integration.aspect.IntegrationLog;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.constants.BornApiRequestConstants;
import com.bornsoft.utils.enums.BornApiExceptionEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ApiDigestUtil;
import com.yjf.common.env.Env;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;
import com.yjf.common.web.XSSRequestWrapper;

/**
 * @Description: 总入口点
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午1:56:29 
 * @version V1.0
 */
@Controller
public class BornApiController {

	private Logger logger = LoggerFactory.getLogger(BornApiController.class);

	private static final String DEFAULT_PROVIDER_NAME_SPACE = "born";
	
	@Autowired
	protected SystemParamCacheHolder systemParamCacheHolder;
	
	@Autowired
	private IBornApiServiceProvider defaultApiServiceProvider = null;
	
	private Map<String,String> replaceServiceMap = new HashMap<String, String>();

	
	public BornApiController() {

	}
	
	/**
	 * Process the given request, generating a response.
	 *
	 * @param request  current HTTP request
	 * @param response current HTTP response
	 */
	@RequestMapping(value = "/gateway")
	public ModelAndView serviceApi(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String born_namespace = StringUtils.defaultIfBlank(request.getParameter(BornApiRequestConstants.SERVICE_NAMESPACE), DEFAULT_PROVIDER_NAME_SPACE);
		String service = getServiceName(request);
		
		//打印请求参数
		logRequestParameter(request, born_namespace, service);

		if (service == null || service.length() <= 0) {
			throw new BornApiException("service值为空");
		}

		//检查xss攻击
		checkXSS(request);

		//检查订单号签名参数
		String orderNo = checkOrderNoAndSign(request);

		//检查签名是否正确
		MerchantInfo merchantInfo = checkMerchantSign(request);
		
		orderNo = handleOrderNo(request,service, merchantInfo);
		
		//获取到处理服务类
		IBornApiServiceProvider bornApiServiceProvider = getBornApiServiceProvider(born_namespace);

		//执行调用
		return callBornApiServcie(bornApiServiceProvider, service, orderNo, request, response, merchantInfo);
	}
	
	private String handleOrderNo(HttpServletRequest request, String service,
									MerchantInfo merchantInfo) {
		String orderNo = request.getParameter(BornApiRequestConstants.ORDER_NO);
		//设置属性
		request.setAttribute(BornApiRequestConstants.ORDER_NO, orderNo);
		return orderNo;
	}

	/**
	 * 获取服务名称
	 * @param request
	 * @return
	 */
	private String getServiceName(HttpServletRequest request) {
		String serviceName = request.getParameter(BornApiRequestConstants.SERVICE);
		if(replaceServiceMap.containsKey(serviceName)){
			return replaceServiceMap.get(serviceName);
		}
		return serviceName;
	}


	/**
	 * 调用服务
	 * @param provider
	 * @param service
	 * @param orderNo
	 * @param request
	 * @param response
	 * @param merchantInfo
	 * @return
	 */
	private ModelAndView callBornApiServcie(IBornApiServiceProvider provider, String service, 
			String orderNo, HttpServletRequest request, HttpServletResponse response, MerchantInfo merchantInfo) {
		IBornApiService apiService = getBornApiService(provider, service);
		ModelAndView mv = apiService.process(orderNo, request, merchantInfo);
		Boolean x = (Boolean) mv.getModel().remove("__POST_XSS");
		if (x != null) {
			response.setHeader("X-XSS-Protection", "0");
		}
		return mv;
	}


	/**
	 * 打印请求参数
	 * @param request
	 * @param name_space
	 * @param service
	 */
	private void logRequestParameter(HttpServletRequest request, String name_space, String service) {
		if (Env.isTest()||Env.isLocal()) {
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String f = headerNames.nextElement();
				String v = request.getHeader(f);
				logger.info("request.header[ {} ] = {} ", f, v);
			}
		}

		StringBuilder sb = new StringBuilder(128);
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			String key = entry.getKey();
			String[] xx = entry.getValue();
			if (xx == null)
				sb.append(key).append('=');
			else
				sb.append(key).append('=').append(xx[0]);
			sb.append(',');
		}
		if (sb.length() > 0) sb.setLength(sb.length() - 1);// 删除最后一点

		logger.info("用户IP: {} 请求服务：{}.{} 请求参数：{}", IPUtil.getIpAddr(request), name_space, service, sb.toString());
	
	}

	/**
	 * 检查是否存在xss攻击
	 * @param request
	 */
	private void checkXSS(HttpServletRequest request) {
		if (request instanceof XSSRequestWrapper) {
			try {
				((XSSRequestWrapper) request).checkXSS();
			} catch (Exception e) {
				logger.error("发现xss攻击：", e);
				throw new BornApiException(BornApiExceptionEnum.ILLEGAL_PARAMETER);
			}
		}
	}

	/**
	 * 检查订单号签名参数是否存在
	 * @param request
	 * @return
	 */
	private String checkOrderNoAndSign(HttpServletRequest request) {
		String orderNo = request.getParameter(BornApiRequestConstants.ORDER_NO);

		if (StringUtils.isEmpty(orderNo)) {
			throw new BornApiException("orderNo不能为空!");
		} else if (orderNo.length() > 20) {
			throw new BornApiException("orderNo长度不能超过20!");
		}
		String sign = request.getParameter(BornApiRequestConstants.SIGN);
		if (StringUtils.isEmpty(sign)) {
			throw new BornApiException("缺少必填参数sign!");
		}
		return orderNo;
	}

	/**
	 * 检查签名
	 * @param request
	 */
	private MerchantInfo checkMerchantSign(HttpServletRequest request) {
		String merchantNo = request.getParameter(BornApiRequestConstants.PARTNER_ID);
		MerchantInfo merchantInfo = getMerchantInfo(merchantNo);
		if (merchantInfo!=null) {
			ApiDigestUtil.check(request, merchantInfo.getMerchantKey(), DigestALGEnum.MD5, "utf-8");
			return merchantInfo;
		} else {
			throw new BornApiException("未找到商户["+merchantNo+"]");
		}
	}

	/**
	 * 获取到服务Provider
	 * @param namespace
	 * @return
	 */
	private IBornApiServiceProvider getBornApiServiceProvider(String namespace) {
		if (defaultApiServiceProvider.getServiceNameSpace().equals(namespace)) {
			return defaultApiServiceProvider;
		}
		throw new BornApiException(BornApiExceptionEnum.NON_EXISTS_SERVICE_PROVIDER);
	}

	/**
	 * 从Provider中提取具体的实现类
	 * @param provider
	 * @param service_name
	 * @return
	 */
	private IBornApiService getBornApiService(IBornApiServiceProvider provider, String service_name) {
		IBornApiService bornApiService = provider.findBornApiService(service_name);
		if (bornApiService == null) throw new BornApiException(BornApiExceptionEnum.NON_EXISTS_SERVICE);
		return bornApiService;
	}
	
	@IntegrationLog
	public MerchantInfo getMerchantInfo(String merchantNo) {
		MerchantInfo info = systemParamCacheHolder.getMerchantInfo(merchantNo);
		/*if(info == null || StringUtil.isBlank(info.getMerchantKey())){
			info.setMerchantNo(merchantNo);
			info.setMerchantKey("be7c713d08194fac983036924586be09");
		}*/
		return info;
	}
}
