package com.bornsoft.integration.kingdee;

import java.security.MessageDigest;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.bornsoft.integration.aspect.IntegrationLog;
import com.bornsoft.pub.enums.KingdeeServiceEnum;
import com.bornsoft.pub.result.kingdee.KingdeeQueryAccountResult;
import com.bornsoft.pub.result.kingdee.KingdeeQueryGuaranteeResult;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.yjf.common.env.Env;
import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;
import com.yjf.common.lang.util.StringUtil;

/**
 * @Description: 金蝶远程客户端
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午11:41:50
 * @version:     v1.0
 */
@Service("kingdeeDistanceClient")
public class KingdeeDistanceClient {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	public SysParameterServiceClient sysParameterServiceClient;
	
	/**
	 * 执行远程post请求
	 * @param clazz
	 * @param order
	 * @param serviceEnum
	 * @return
	 */
	@IntegrationLog
	public <T extends BornSynResultBase> T execute(Class<T> clazz, BornOrderBase order, KingdeeServiceEnum serviceEnum) {
		T result = null;
		try {
			//订单校验
			order.validateOrder();
			//获取商户配置
			MerchantInfo merchant = getKingMerchant();
			if(Env.isTest()){
				result = BeanUtils.instantiate(clazz);
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
				result.setOrderNo(order.getOrderNo());
				result.setResultMessage("模拟成功");
			}else{
				//拼接访问地址
				String requestUrl = makeRequestUrl(order,serviceEnum);
				//构造数据
				String json = makeData(order,merchant);
				//发送数据[同时执行签名]
				logger.info("请求金蝶系统,原始请求={}",json);
				//发送请求
				String responseText = send(requestUrl,json);
				responseText = new String(responseText.getBytes("UTF-8"), "UTF-8");
				logger.info("原始响应报文={}",responseText);
				//把结果转化为MAP
				@SuppressWarnings("unchecked")
				Map<String, String> resultMap = JsonParseUtil.parseObject(responseText, Map.class);
				String sign = digest(resultMap.get("data"), merchant.getMerchantKey());
				if(!StringUtil.equals(sign, resultMap.get("sign"))){
					throw new BornApiException("返回报文签名校验失败");
				}
				if(KingdeeQueryAccountResult.class == clazz|| KingdeeQueryGuaranteeResult.class == clazz){
					result = JsonParseUtil.parseObject(resultMap.get("data"), clazz);
				}else{
					resultMap.remove("data");
					result = JsonParseUtil.parseObject(JsonParseUtil.toJSONString(resultMap), clazz);
				}
				result.setOrderNo(order.getOrderNo());
			}
			
		} catch (Throwable ex) {
			logger.info("请求[" + serviceEnum.getCode() + "]接口异常：", ex);
			result = BeanUtils.instantiate(clazz);
			result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
			result.setOrderNo(order.getOrderNo());
			if (ex instanceof BornApiException) {
				result.setResultMessage(ex.getMessage());
			} else {
				result.setResultMessage("请求异常!");
			}
		}
		return result;
	}
	
	private String makeData(BornOrderBase order, MerchantInfo merchant) {
		JSONObject param = new JSONObject();
		String json = GsonUtil.toJson(order);
		param.put("data", json);
		param.put("sign", digest(json,merchant.getMerchantKey()));
		param.put("partnerId", merchant.getMerchantNo());
		param.put("orderNo", order.getOrderNo());
		return param.toJSONString();
	}
	
	/**
	 * 获取金蝶API访问密钥
	 * @return
	 */
	public MerchantInfo getKingMerchant(){
		MerchantInfo info = null;
		String key =  sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.Kingdee_Api_Partner_Key.code());
		if(StringUtil.isBlank(key)){
			throw new BornApiException("未找到金蝶密钥配置");
		}else{
			String partnerId =  sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.Kingdee_Api_Partner_Id.code());
			if(StringUtil.isBlank(partnerId)){
				throw new BornApiException("未找到金蝶商户配置");
			}
			info = new MerchantInfo(partnerId, key);
		}
		return info;
	}

	public String digest(String message,String key){
		message+=key;
		/**执行签名**/
		byte[] toDigest;
		try {
			logger.info("待签名字符串==>{}", message);
			toDigest = message.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance(DigestALGEnum.MD5.getName());
			md.update(toDigest);
			return new String (Hex.encodeHex(md.digest()));
		} catch (Exception e) {
			throw new RuntimeException("签名失败", e);
		}
	}
	
	private String makeRequestUrl(BornOrderBase order, KingdeeServiceEnum serviceEnum) {
		String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.Kingdee_Api_System_Url.code());
		if(StringUtil.isBlank(url)){
			throw new BornApiException("金蝶服务器访问地址未配置");
		}else{
			url+="/finance/{serviceName}";
		}
		return url.replace("{serviceName}", serviceEnum.code());
	}
	
	/**
	 * 发送请求
	 * @param url
	 * @param dataMap
	 * @return
	 * @throws Exception 
	 */
	private String send(String reqUrl, String json) throws Exception {
		//超时时间与读写时间各设为一分钟
		logger.info("请求url={},json={}",reqUrl,json);
		String result = HttpRequestUtil.sendJsonWithHttps(reqUrl, json);
		logger.info("响应={}",result);
		return result;
	}
}
