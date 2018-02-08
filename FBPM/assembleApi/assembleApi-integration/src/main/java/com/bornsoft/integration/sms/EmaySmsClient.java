package com.bornsoft.integration.sms;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.StringUtil;
import com.bornsoft.integration.sms.emay.ResultModel;
import com.bornsoft.integration.sms.emay.dto.SmsSingleRequest;
import com.bornsoft.integration.sms.emay.reponse.EmayHttpResponseBytes;
import com.bornsoft.integration.sms.emay.reponse.EmayHttpResponseBytesPraser;
import com.bornsoft.integration.sms.emay.reponse.EmayHttpResultCode;
import com.bornsoft.integration.sms.emay.reponse.SmsResponse;
import com.bornsoft.integration.sms.emay.utils.AES;
import com.bornsoft.integration.sms.emay.utils.GZIPUtils;
import com.bornsoft.integration.sms.emay.utils.JsonHelper;
import com.bornsoft.integration.sms.emay.utils.http.EmayHttpClient;
import com.bornsoft.integration.sms.emay.utils.http.EmayHttpRequestBytes;
import com.bornsoft.integration.sms.enums.SmsChannelEnum;
import com.bornsoft.utils.base.BornResultBase;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
/**
 * @Description: 亿美 
 * @author taibai@yiji.com
 * @date 2017-10-16 下午5:51:17
 * @version V1.0
 */
@Service
public class EmaySmsClient extends SmsClientBase{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public BornResultBase send(String mobile, String content){
		logger.info("请求"+SmsChannelEnum.Emay.message()+"短信入参, mobile={}, content={}", mobile, content);
		// appId
		String appId = getConfigNotNull(ApiSystemParamEnum.Emay_Sms_AppId);
		// 密钥
		String secretKey = getConfigNotNull(ApiSystemParamEnum.Emay_Sms_Secret);
		// 接口地址
		String host = getConfig(ApiSystemParamEnum.Emay_Sms_System_Url);
		
		if(StringUtil.isBlank(host)){
			host = "bjmtn.b2m.cn";
		}
		
		content = getSignatrue() + content;
		ResultModel smsResult = setSingleSms(appId,secretKey,host,"AES/ECB/PKCS5Padding", content, null, null, mobile,false,"UTF-8");
		logger.info("请求结果：{}",smsResult.getResult());
		if("SUCCESS".equals(smsResult.getCode())){
			SmsResponse response = JsonHelper.fromJson(SmsResponse.class, smsResult.getResult());
			if (response != null) {
				BornResultBase result = new BornResultBase(CommonResultEnum.EXECUTE_SUCCESS,
						"发送成功!");
				logger.info("data : " + response.getMobile() + "," + response.getSmsId() + "," + response.getCustomSmsId());
				return result;
			}else{
				throw new IllegalArgumentException(smsResult.getResult());
			}
		}else{
			throw new IllegalArgumentException(smsResult.getResult());
		}
	}
	
	/**
	 * 发送单条短信
	 * @param isGzip 是否压缩
	 * @return 
	 */
	private  ResultModel setSingleSms(String appId,String secretKey,String host,String algorithm,String content, String customSmsId, String extendCode, String mobile,boolean isGzip,String encode) {
		SmsSingleRequest pamars = new SmsSingleRequest();
		pamars.setContent(content);
		pamars.setCustomSmsId(customSmsId);
		pamars.setExtendedCode(extendCode);
		pamars.setMobile(mobile);
		ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/sendSingleSMS",isGzip,encode);
		return result;

	}
	
	/**
	 * 公共请求方法
	 */
	public  ResultModel request(String appId,String secretKey,String algorithm,Object content, String url,final boolean isGzip,String encode) {
		Map<String, String> headers = new HashMap<String, String>();
		EmayHttpRequestBytes request = null;
		try {
			headers.put("appId", appId);
			headers.put("encode", encode);
			String requestJson = JsonHelper.toJsonString(content);
			logger.info("result json: " + requestJson);
			byte[] bytes = requestJson.getBytes(encode);
			logger.info("request data size : " + bytes.length);
			if (isGzip) {
				headers.put("gzip", "on");
				bytes = GZIPUtils.compress(bytes);
				logger.info("request data size [com]: " + bytes.length);
			}
			byte[] parambytes = AES.encrypt(bytes, secretKey.getBytes(), algorithm);
			logger.info("request data size [en] : " + parambytes.length);
			request = new EmayHttpRequestBytes(url, encode, "POST", headers, null, parambytes);
		} catch (Exception e) {
			logger.info("加密异常");
			e.printStackTrace();
		}
		EmayHttpClient client = new EmayHttpClient();
		String code = null;
		String result = null;
		try {
			EmayHttpResponseBytes res = client.service(request, new EmayHttpResponseBytesPraser());
			if(res == null){
				logger.info("请求接口异常");
				return new ResultModel(code, result);
			}
			if (res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
				if (res.getHttpCode() == 200) {
					code = res.getHeaders().get("result");
					if (code.equals("SUCCESS")) {
						byte[] data = res.getResultBytes();
						logger.info("response data size [en and com] : " + data.length);
						data = AES.decrypt(data, secretKey.getBytes(), algorithm);
						if (isGzip) {
							data = GZIPUtils.decompress(data);
						}
						logger.info("response data size : " + data.length);
						result = new String(data, encode);
						logger.info("response json: " + result);
					}
				} else {
					logger.info("请求接口异常,请求码:" + res.getHttpCode());
				}
			} else {
				logger.info("请求接口网络异常:" + res.getResultCode().getCode());
			}
		} catch (Exception e) {
			logger.error("解析失败",e);
			e.printStackTrace();
		}
		ResultModel re = new ResultModel(code, result);
		return re;
	}

}

