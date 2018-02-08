package com.bornsoft.integration.risk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bornsoft.integration.aspect.IntegrationLog;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.base.BornRedirectResult;
import com.bornsoft.utils.base.BornResultBase;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.tool.ApiDigestUtil;
import com.bornsoft.utils.tool.ReflectUtils;
import com.yjf.common.env.Env;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.net.HttpUtil;
import com.yjf.common.net.HttpUtil.HttpResult;

/**
  * @Description: 风险监控系统远程客户端 
  * @author taibai@yiji.com 
  * @date  2016-8-5 下午2:43:44
  * @version V1.0
 */
@Service("riskSystemClient")
public class RiskSystemDistanceClient {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SystemParamCacheHolder	systemParamCacheHolder;
	
	
	/**
	 * 执行请求风险监控系统接口
	 * @param <O>
	 * @param order
	 * @param clazz
	 * @return 指定的响应结果类型
	 */
	@IntegrationLog
	public <R extends BornResultBase, O extends BornOrderBase> R execute(O order, Class<R> clazz) {
		R result = BeanUtils.instantiateClass(clazz);
		result.setOrderNo(order.getOrderNo());
		try {
			//校验表单
			order.validateOrder();
			if(Env.isTest() && order.getClass().getName().toLowerCase().contains("syn") 
					&& StringUtil.notEquals(systemParamCacheHolder.getConfig(ApiSystemParamEnum.Risk_System_Switch), "on")){
				result.setOrderNo(order.getOrderNo());
				result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
				result.setResultMessage("当前环境,模拟同步成功");
			
			}else{
				//拼接请求参数[同时执行签名]
				Map<String, String> paramMap = builerOrder(order);
				String requestUrl = makeRequestUrl(order);
				if(order.isRedirect()){
					BornRedirectResult resultTmp = (BornRedirectResult) result;
					resultTmp.setParamMap(paramMap);
					resultTmp.setRequestUrl(requestUrl);
					resultTmp.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
					resultTmp.setResultMessage(CommonResultEnum.EXECUTE_SUCCESS.message());
				}else{
					//执行请求
					logger.info("请求风控系统,原始请求={}",paramMap);
					String responseText = this.post(requestUrl, paramMap);
					responseText = new String(responseText.getBytes("UTF-8"), "UTF-8");
					logger.info("原始响应报文={}",responseText);
					//把结果转化为MAP
					Map<String, String> resultMap = this.toMap(responseText);
					//构建返回结果[同时执行验签]
					buileResult(resultMap, result, order);
				}
			}
			
		} catch (Throwable ex) {
			logger.error("请求风险监控系统接口[" + order.getService() + "]失败", ex);
			result.setOrderNo(order.getOrderNo());
			result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
			String description = ex.getMessage();
			if (description == null) {
				description = "请求接口失败!";
			} else {
				if (description.length() > 25) {
					description = description.substring(0, 25);
				}
			}
			result.setResultMessage(description);
		}
		return result;
	}
	
	private String makeRequestUrl(BornOrderBase order) {
		String url = systemParamCacheHolder.getRiskSystemUrl() + "business/{serviceName}.json";
		return url.replace("{serviceName}", order.getService());
	}

	/**
	 * 对象转化为Map同时执行签名
	 * @param order
	 * @return
	 */
	private Map<String, String> builerOrder(BornOrderBase order) {
		//添加请求参数
		Map<String, String> paramMap = ReflectUtils.parseToMap(order);
		paramMap.put("partnerId", systemParamCacheHolder.getRiskMerchantCode());
		//放置签名
		ApiDigestUtil.digest(paramMap, systemParamCacheHolder.getRiskSecurityKey());
		return paramMap;
	}
	
	/**
	 * 发送请求
	 * @param url
	 * @param dataMap
	 * @return
	 */
	private String post(String url, Map<String, String> dataMap) {
		//超时时间与读写时间各设为一分钟
		HttpResult result = HttpUtil.getInstance().readTimeout(60).connectTimeout(60)
			.post(url, dataMap, "UTF-8");
		if (result.getStatusCode() == 200) {
			return result.getBody();
		} else {
			throw new IllegalArgumentException("响应失败!");
		}
	}
	
	/**
	 * json-->Map<String, String>
	 * @param str
	 * @return
	 */
	private Map<String, String> toMap(String str) {
		HashMap<String, String> data = new HashMap<String, String>();
		JSONObject jsonObject = JSONObject.parseObject(str);
		for (Entry<String, Object> entry : jsonObject.entrySet()) {
			if(entry.getValue() != null){
				data.put(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return data;
	}
	
	/**
	 * 将 Map转化为Bean、校验签名等操作
	 * @param resultMap
	 * @param object
	 * @param order
	 */
	private <T extends BornResultBase> void buileResult(Map<String, String> resultMap,
															T object, BornOrderBase order) {
		//执行签名
		ApiDigestUtil.check(resultMap, systemParamCacheHolder.getRiskSecurityKey());
		//Map转化为Bean
		ReflectUtils.parseToBean(resultMap, object);
	}
	
}
