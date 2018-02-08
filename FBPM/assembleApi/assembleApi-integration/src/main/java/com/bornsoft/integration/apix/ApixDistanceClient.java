package com.bornsoft.integration.apix;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bornsoft.integration.aspect.IntegrationLog;
import com.bornsoft.jck.dal.daointerface.ApixVeryLogDAO;
import com.bornsoft.jck.dal.dataobject.ApixVeryLogDO;
import com.bornsoft.pub.order.apix.ApixOrderBase;
import com.bornsoft.pub.result.apix.ApixResultBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.tool.CommonUtil;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.InstallCommonResultUtil;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.bornsoft.utils.tool.PoolHttpUtils;
import com.bornsoft.utils.tool.ReflectUtils;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.net.HttpUtil.HttpResult;

/**
 * @Description: APIX接口地址 
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:15:24
 * @version V1.0
 */
@Service("apixDistanceClient")
public class ApixDistanceClient {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApixVeryLogDAO apixVeryLogDAO;
	
	@IntegrationLog
	public <T extends ApixResultBase> T execute(ApixOrderBase order,Class<T> resultClass){
		T result = null;
		Map<String,String> dataMap = null;
		boolean mock = false;
		try{
			order.validateOrder();
			String url = buildUrl(order);
			dataMap = ReflectUtils.parseToMap(order,BornOrderBase.class);
			logger.info("请求参数={}",dataMap);
			String resultStr = get(url, dataMap);
			logger.info("响应参数={}",resultStr);
			JSONObject json = JSONObject.parseObject(resultStr);
			if(StringUtil.isBlank(json.getString("data"))){
				json.remove("data");
			}
			result = GsonUtil.create().fromJson(resultStr, resultClass);
			InstallCommonResultUtil.installDefaultSuccessResult(order, result);
		}catch(Exception e){
			logger.error("请求Apix接口失败",e);
			result = BeanUtils.instantiateClass(resultClass);
			result.setCode("999");
			result.setMsg(e.getMessage());
			InstallCommonResultUtil.installDefaultFailureResult(e.getMessage(), order, result);
		}finally{
			if(!mock){
				//保存调用记录
				saveRecord(dataMap,order,result);
			}
		}
		return result;
	}
	
	/**
	 * 保存调用记录
	 * @param reqMap
	 * @param order
	 * @param result
	 */
	private void saveRecord(Map<String,String> reqMap, ApixOrderBase order, ApixResultBase result) {
		try {
			ApixVeryLogDO apixLog = new ApixVeryLogDO();
			apixLog.setOrderNo(order.getOrderNo());
			Map<String,String> dataMap = new HashMap<>();
			for(Map.Entry<String,String> entry: reqMap.entrySet()){
				if(StringUtil.isNotBlank(entry.getValue()) || !"sign".equals(entry.getKey())){
					dataMap.put(entry.getKey(), entry.getValue());
				}
			}
			apixLog.setReqParam(CommonUtil.cutString(JsonParseUtil.toJSONString(dataMap), 200));
			apixLog.setRowAddTime(DateUtils.getCurrentDate());
			apixLog.setServiceName(order.getService());
			apixLog.setVeryCode(result.getCode());
			apixLog.setVeryMsg(CommonUtil.cutString(result.getMsg(), 50));
			apixVeryLogDAO.insert(apixLog);
		} catch (Exception e) {
			logger.error("保存APIX调用记录失败：",e);
		}
	}

	private String buildUrl(ApixOrderBase order) {
		return order.getUrl();
	}
	
	/**
	 * 发送请求
	 * @param url
	 * @param dataMap
	 * @return
	 */
	private String get(String url, Map<String, String> dataMap) {
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("apix-key", dataMap.remove("apix-key"));
		HttpResult result = PoolHttpUtils.getHttpUtil().
				get(url, dataMap, headerMap, "UTF-8");
		if (result.getStatusCode() == 200) {
			return result.getBody();
		} else {
			String msg = "征信接口响应失败!";
			if(StringUtil.isNotBlank(result.getBody())){
				try {
					msg = result.getBody().replaceAll("\\s+", " ");
					JSONObject err = JSONObject.parseObject(msg);
					String errMsg = err.getString("error");
					if(StringUtils.isNotBlank(errMsg)){
						if(errMsg.contains("exceeded")){
							msg = "查询失信记录失败,请检查余额或使用次数是否充足";
						}else{
							msg = errMsg;
						}
					}
				} catch (Exception e) {
					logger.info("调用apix查询失信名单error:",e);
				}
			}
			throw new IllegalArgumentException(msg);
		}
	}
}
