package com.bornsoft.api.service.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.bornsoft.utils.base.BornRedirectResult;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ApiDigestUtil;
import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;

/**
 * @Description:重定向服务-抽像类
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午2:17:00 
 * @version V1.0
 */
public abstract class AbsRedirectBornApiService extends AbsBaseBornApiService {

//	@Value("${internetplus.appointed.signature.des.key}")
	private String internetplus_signature_key;		//internetplus与api约定的DES签名安全码

	/**
	 * 核心处理方法
	 */
	@Override
	public final ModelAndView process(String orderNo, HttpServletRequest request, MerchantInfo merchantInfo) {
		
		BornRedirectResult result = doProcess(orderNo, request, merchantInfo);
		if(result.getResultCode()!=CommonResultEnum.EXECUTE_SUCCESS){
			throw new BornApiException(result.getResultMessage());
		}
		//是否执行加密
		if (signature()) {
			String sign = ApiDigestUtil.execute(result.getParamMap(), getSignatureKey(), DigestALGEnum.MD5, "UTF-8");
			result.getParamMap().put("sign", sign);
		}
		if ("post".equals(result.getMethod())) {
			return returnPostRedirectView(result, result.getParamMap());
		} else {
			return returnGetRedirectView(request, result.getRequestUrl(), result.getParamMap());
		}
	}

	/**
	 * 处理BornAPI服务
	 * @param orderNo
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	protected abstract BornRedirectResult doProcess(String orderNo, HttpServletRequest request,MerchantInfo merchantInfo);

	/**
	 * POST 跳转
	 * @param url
	 * @param dataMap
	 * @return
	 */
	protected ModelAndView returnPostRedirectView(BornRedirectResult result, Map<String, ?> dataMap) {
		logger.info("服务：{}.{} POST 重定向到新地址:{}, 参数：{}", getServiceNameSpace(), getServiceCode() ,result.getRequestUrl(), dataMap);
		ModelAndView view = new ModelAndView("/postRedirect.vm", dataMap);
		view.addObject("__POST_MESSAGE", "正在跳转，请稍等 ...");
		view.addObject("__POST_NEW_URL", result.getRequestUrl());
		view.addObject("__POST_PARA_MAP", dataMap);
		view.addObject("__POST_XSS", Boolean.TRUE);
		view.addObject("_TO_URL", result.getToUrl());
		view.addObject("_LOGOUT_URL", result.getLogOutUrl());
		return view;
	}

	/**
	 * Get 跳转
	 * @param request
	 * @param url
	 * @param dataMap
	 * @return
	 */
	protected ModelAndView returnGetRedirectView(HttpServletRequest request, String url, Map<String, String> dataMap) {
		logger.info("服务：{}.{} GET 重定向到新地址:{}, 参数：{}", getServiceNameSpace(), getServiceCode(), url, dataMap);
		RedirectView redirectView = new RedirectView(url);
		redirectView.setEncodingScheme(request.getCharacterEncoding());
		return new ModelAndView(redirectView, dataMap);
	}

	/**
	 * 跳转时是否需要签名
	 * @return
	 */
	public boolean signature() {
		return false;
	}

	/**
	 * 签名私钥
	 * @return
	 */
	public String getSignatureKey() {
		return internetplus_signature_key;
	}
}
