package com.bornsoft.integration.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bornsoft.utils.base.BornResultBase;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.yjf.common.util.StringUtils;

/**
 * @Description: 华软通信短信
 * @author:      xiaohui@yiji.com
 * @date         2016-10-28 下午5:14:49
 * @version:     v1.0
 */
@Service
public class HuaruanDistanceClient extends SmsClientBase{


	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 短信发送(自动添加短信签名)
	 * @param mobile		手机号
	 * @param content		短信内容
	 * @return
	 */
	public BornResultBase send(String mobile, String content) {
		BornResultBase result = null;
		logger.info("请求华软短信入参, mobile={}, content={}", mobile, content);
		
		try {
			if (StringUtils.isEmpty(mobile)) {
				throw new IllegalArgumentException("发送手机号不能为空!");
			}
			if (StringUtils.isEmpty(content)) {
				throw new IllegalArgumentException("发送内容不能为空!");
			}
			

			String requestUrl = getConfigNotNull(ApiSystemParamEnum.Huaruan_Sms_System_Url);
			String userName = getConfigNotNull(ApiSystemParamEnum.Huaruan_Sms_Username);
			String password = getConfigNotNull(ApiSystemParamEnum.Huaruan_Sms_Password);// 经过MD5加密
			String gwid = getConfigNotNull(ApiSystemParamEnum.Huaruan_Sms_Gwid);
			String signatrueName = getSignatrue();

			// 短信编码
			content = URLEncoder.encode(signatrueName + content, "GB2312");

			String sms = requestUrl + "?action=Send&username=" + userName
					+ "&password=" + password + "&gwid=" + gwid + "&mobile="
					+ mobile + "&message=" + content;
			// 执行发送并获取发送结果
			URL yahoo = new URL(sms);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yahoo.openStream(), "GB2312"));
			StringBuffer buffer = new StringBuffer();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
			}
			in.close();

			// 组装结果
			if (StringUtils.isEmpty(buffer.toString())) {
				throw new IllegalArgumentException("短信通道未正常响应结果!");
			}
			JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
			String code = jsonObject.getString("CODE");
			if ("1".equals(code)) {
				result = new BornResultBase(CommonResultEnum.EXECUTE_SUCCESS,
						"发送成功!");
			} else {
				throw new IllegalArgumentException(
						jsonObject.getString("RESULT"));
			}
			
		} catch (Throwable ex) {
			logger.error("请求华软短信mobile={},异常: ", mobile, ex);
			if (ex instanceof IllegalArgumentException) {
				result = new BornResultBase(CommonResultEnum.EXECUTE_FAILURE, ex.getMessage());
			} else	if (ex instanceof BornApiException) {
				result = new BornResultBase(CommonResultEnum.EXECUTE_FAILURE, ex.getMessage());
			} else {
				result = new BornResultBase(CommonResultEnum.EXECUTE_FAILURE, "发送失败!");
			}
		}
		logger.info("请求华软短信出参, mobile={}, result={}", mobile, result);
		return result;
	}
	

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
//		BornResultBase result = new HuaruanDistanceClient().send("18523114705", "手机短信验证码：699060(如非本人操作，请忽略本短信。)");
//		System.out.println(result);
	}
}

