package com.bornsoft.integration.umeng;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.integration.umeng.order.Aps;
import com.bornsoft.integration.umeng.order.Body;
import com.bornsoft.integration.umeng.order.Payload;
import com.bornsoft.integration.umeng.order.Policy;
import com.bornsoft.integration.umeng.order.UmengSendOrder;
import com.bornsoft.pub.enums.MobileSystemEnum;
import com.bornsoft.pub.order.umeng.UMengOrder;
import com.bornsoft.pub.result.umeng.UmengSendResult;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.enums.MimeType;
import com.bornsoft.utils.tool.CommonUtil;
import com.bornsoft.utils.tool.PoolHttpUtils;
import com.yjf.common.env.Env;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.net.HttpUtil.HttpResult;

@Service("uMenMessageSendClient")
public class UmenMessageSendClient {
	
	private static final Logger logger = LoggerFactory
			.getLogger(UmenMessageSendClient.class);
	
	@Autowired
	private SystemParamCacheHolder systemParamCacheHolder;
	
	private final String sendUrl = "http://msg.umeng.com/api/send";
	@SuppressWarnings("unused")
	private final String cancelUrl = "http://msg.umeng.com/api/cancel";
	/** 推送用户名推送字段名称 */
	private final String userName = "userName";
	/* 特殊参数替换 */
	private final static Map<String, String> androidParamMap = new HashMap<String, String>();
	private final static Map<String, String> iosParamMap = new HashMap<String, String>();
	static {
		iosParamMap.put("contentAvailable", "content-available");
	}
	/**
	 * 发送消息
	 * @param order
	 * @return
	 */
	public UmengSendResult send(UMengOrder order) {
		UmengSendResult result = new UmengSendResult();
		try {
			order.validateOrder();
			//构造友盟发送order
			UmengSendOrder sendOrder = new UmengSendOrder();
			initOrder(sendOrder, order);
			
			//发送ANDROID客户端
			String appMasterSecret = initKey(order.getMessageType().code(), sendOrder,
					MobileSystemEnum.Android.code());
			String jsonStr = JSONObject.toJSONString(sendOrder);
			/* 特殊参数替换 */
			for (Map.Entry<String, String> entry : androidParamMap.entrySet()) {
				jsonStr = jsonStr.replace("\"" + entry.getKey() + "\":", "\""
						+ entry.getValue() + "\":");
			}
			//发送请求
			sendMessage(MobileSystemEnum.Android,result, sendUrl, jsonStr,
					appMasterSecret);
			
			//发送IOS客户端
			appMasterSecret = initKey(order.getMessageType().code(), sendOrder, MobileSystemEnum.Ios.code());
			jsonStr = JSONObject.toJSONString(sendOrder);
			/* 特殊参数替换 */
			for (Map.Entry<String, String> entry : iosParamMap.entrySet()) {
				jsonStr = jsonStr.replace("\"" + entry.getKey() + "\":", "\""
						+ entry.getValue() + "\":");
			}
			sendMessage(MobileSystemEnum.Ios,result, sendUrl, jsonStr,
					appMasterSecret);
			
			if(result.isAndroidSend()||result.isIosSend()){
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
			}else{
				result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
			}
		} catch (Exception e) {
			logger.error("发送异常", e);
			result.setResultCode(CommonResultEnum.UN_KNOWN_EXCEPTION);
			result.setResultMessage(e.getMessage()+";"+StringUtils.defaultIfBlank(result.getResultMessage(), ""));
		}

		return result;
	}


	private String initKey(String msgType, UmengSendOrder sendOrder, String types) {
		String appkey = "";
		String appMasterSecret = "";
		if (StringUtil.equals("android", types)) {
			String[] arrStr = StringUtil.split(systemParamCacheHolder
					.getConfig(ApiSystemParamEnum.UMeng_Android_Key), "&");
			appkey = arrStr[0];
			appMasterSecret = arrStr[1];
		} else {
			String[] arrStr = StringUtil.split(systemParamCacheHolder
					.getConfig(ApiSystemParamEnum.UMeng_Ios_Key), "&");
			appkey = arrStr[0];
			appMasterSecret = arrStr[1];
			String alert = sendOrder.getPayload().getBody().getText();
			Aps aps = new Aps();
			aps.setAlert(alert);
			sendOrder.getPayload().setAps(aps);
			sendOrder.getPayload().setMessageType(msgType);
			sendOrder.getPayload().setBody(null);
			sendOrder.getPayload().setDisplay_type(null);
		}

		if (sendOrder != null) {
			sendOrder.setAppkey(appkey);
		}
		return appMasterSecret;
	}

	private void initOrder(UmengSendOrder sendOrder, UMengOrder order) {
		Map<String, String> custom = new HashMap<>();
		custom.put(userName, order.getUserName());
		custom.put("messageType", order.getMessageType().code());

		Policy policy = new Policy();
		policy.setOut_biz_no("" + (new Date()).getTime());
		policy.setStart_time(order.getStart_time());
		policy.setExpire_time(order.getExpire_time());
		sendOrder.setPolicy(policy);

		Payload payload = new Payload();
		Body body = new Body();
		body.setTitle(order.getTitle());
		body.setText(CommonUtil.getTextMsg(order.getText()));
		body.setTicker(order.getTicker());
		body.setCustom(custom);
		payload.setBody(body);
		if (StringUtil.equals(order.getDisplayType(), "message")) {
			payload.setDisplay_type("message");
		}
		sendOrder.setPayload(payload);
		String production_mode = (Env.isOnline())?"true":"false";
		sendOrder.setAlias(order.getUserName());
		sendOrder.setAlias_type("customId");
		sendOrder.setProduction_mode(production_mode);
		sendOrder.setDevice_tokens(order.getDevice_tokens());
		sendOrder.setTimestamp(Integer.toString((int) (System
				.currentTimeMillis() / 1000)));
		sendOrder.setDescription("cguarantee消息推送");

	}

	/**
	 * 通过友盟发送消息
	 * @param mobileSys
	 * @param sendResult
	 * @param url
	 * @param jsonStr
	 * @param appMasterSecret
	 * @return
	 * @throws Exception
	 */
	public void sendMessage(MobileSystemEnum mobileSys, final UmengSendResult sendResult, String url,
				String jsonStr, String appMasterSecret) throws Exception {

		String sign = DigestUtils
				.md5Hex(("POST" + url + jsonStr + appMasterSecret)
						.getBytes("utf8"));
		url = url + "?sign=" + sign;

		logger.info("调用友盟接口开始：url={},jsonStr={}", url, jsonStr);
		HttpResult httpResult = PoolHttpUtils.sendPostString(url, jsonStr,
				MimeType.ApplicationJson);
		int status = httpResult.getStatusCode();
		String result = httpResult.getBody();
		logger.info("调用友盟接口结束：result={},status={}", result.toString(), status);

		JSONObject json = JSONObject.parseObject(result.toString());
		JSONObject dataJson = (JSONObject) json.get("data");
		if (status == 200) {
			String taskId = (String) dataJson.get("task_id");
			sendResult
					.setTask_id(StringUtil.isBlank(sendResult.getTask_id()) ? taskId
							: sendResult.getTask_id() + "&" + taskId);
			if (StringUtils.equals(json.getString("ret"), "SUCCESS")) {
				setSendResult(mobileSys, sendResult, dataJson, true);
			} else {
				setSendResult(mobileSys, sendResult, dataJson, false);
			}
		} else {
			setSendResult(mobileSys, sendResult, dataJson, false);
		}

	}
	
	/**
	 * 设置发送结果
	 * @param mobileSys
	 * @param sendResult
	 * @param dataJson
	 * @param success
	 */
	private void setSendResult(MobileSystemEnum mobileSys,
			UmengSendResult sendResult, JSONObject dataJson,boolean success) {
		if(mobileSys == MobileSystemEnum.Android){
			sendResult.setAndroidSend(success);
		}else if(mobileSys == MobileSystemEnum.Ios){
			sendResult.setIosSend(success);
		}
		sendResult.setResultMessage((success?mobileSys.code()+" : success":(mobileSys.code()+":"+StringUtils.defaultIfBlank(
				dataJson.getString("error_code"), "unknown")))+";"+StringUtils.defaultIfBlank(sendResult.getResultMessage(),""));
	}


}
