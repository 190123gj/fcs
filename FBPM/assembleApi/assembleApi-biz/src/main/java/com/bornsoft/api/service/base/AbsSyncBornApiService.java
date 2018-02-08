package com.bornsoft.api.service.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.constants.BornApiResponseConstants;
import com.bornsoft.utils.tool.ApiDigestUtil;
import com.bornsoft.utils.tool.BornApiResponseUtils;
import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;

/**
 * @Description: 同步服务-抽像类
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午4:16:47 
 * @version V1.0
 */
public abstract class AbsSyncBornApiService extends AbsBaseBornApiService {

	/**
	 * 核心处理方法
	 * @param orderNo
	 * @param request
	 * @param merchantInfo
	 * @return ModelAndView
	 */
	@Override
	public final ModelAndView process(String orderNo, HttpServletRequest request, MerchantInfo merchantInfo) {
		Map<String, String> data = doProcess(orderNo, request, merchantInfo);
		String sign = ApiDigestUtil.execute(data, merchantInfo.getMerchantKey(), DigestALGEnum.MD5, "UTF-8");
		data.put("sign", sign);
		data.put("signType", DigestALGEnum.MD5.name());
		return returnDataMap(data);
	}

	/**
	 * 处理BornAPI服务
	 * @param orderNo
	 * @param request
	 * @param merchantInfo
	 */
	protected abstract Map<String, String> doProcess(String orderNo, HttpServletRequest request, MerchantInfo merchantInfo);
	
	/**
	 * 构建含有基本应答参数
	 * @param orderNo
	 * @param serviceCode
	 * @param success
	 * @param resultMessage
	 * @param resultCode
	 * @return
	 */
	protected final Map<String, String> buildResultMap(String orderNo, String serviceCode, String resultMessage,
			String resultCode) {
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("orderNo", orderNo);
		dataMap.put(BornApiResponseConstants.RESULT_CODE, resultCode);
		dataMap.put(BornApiResponseConstants.RESULT_MESSAGE, resultMessage);
		dataMap.put(BornApiResponseConstants.SERVICE_CODE, serviceCode);
		return dataMap;
	}

	/**
	 * 构建结果MAP
	 * @param orderNo
	 * @param serviceCode
	 * @param result
	 * @return
	 */
	protected final Map<String, String> buildResultMap(String orderNo, String serviceCode, BornSynResultBase result) {
		return buildResultMap(orderNo, serviceCode, result.getResultMessage(), result.getResultCode().code());
	}
	
	/**
	 * （不签名）返回同步结果
	 */
	protected ModelAndView returnDataMap(Map<String, String> dataMap) {
		String resultJsonStr = BornApiResponseUtils.toJSONString(dataMap);
		logger.info("请求服务 {}.{} 返回结果：{}", getServiceNameSpace(), getServiceCode(), resultJsonStr);
		ModelAndView modelAndView = new ModelAndView("/dataResult.vm");
		modelAndView.addObject("json_data", resultJsonStr);
		return modelAndView;
	}
}
