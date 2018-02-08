package com.bornsoft.utils.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.bornsoft.utils.enums.MimeType;
import com.yjf.common.net.HttpUtil;
import com.yjf.common.net.HttpUtil.HttpResult;

/**
 * @Description: httpUtil，依赖于yjf-common包中的httpUtil
 * @author:      xiaohui@yiji.com
 * @date         2016-2-15 下午1:30:54
 * @version:     v1.0
 */
public class PoolHttpUtils {

	/**
	 * 模拟发送http请求
	 * @param url
	 * @param dataMap
	 * @return 返回内容
	 */
	public static String sendPostString(String url, Map<String, String> dataMap) {
		return getHttpUtil().post(url, dataMap, "UTF-8").getBody();
	}

	/**
	 * 模拟发送http请求
	 * @param url
	 * @param dataMap
	 * @return 返回内容
	 */
	public static HttpResult sendPost(String url, Map<String, String> dataMap) {
		return getHttpUtil().post(url, dataMap, "UTF-8");
	}

	/**
	 * 模拟发送http请求
	 * @param url
	 * @param dataMap
	 * @return 返回内容
	 */
	public static String sendGetString(String url, Map<String, String> dataMap) {
		return getHttpUtil().get(url, dataMap, "UTF-8").getBody();
	}

	/**
	 * 模拟发送http请求
	 * @param url
	 * @param dataMap
	 * @return 返回内容
	 */
	public static HttpResult sendGet(String url, Map<String, String> dataMap) {
		return getHttpUtil().get(url, dataMap, "UTF-8");
	}

	/**
	 * 模拟发送http请求
	 * @param url
	 * @param dataMap
	 * @return 返回内容
	 */
	public static String sendPutString(String url, Map<String, String> dataMap) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(appendParam(url, dataMap));
		HttpResponse response = client.execute(put);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	/**
	 * 模拟发送http请求
	 * @param url
	 * @param dataMap
	 * @return 返回内容
	 */
	public static String sendDeleteString(String url, Map<String, String> dataMap) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete httpdelete = new HttpDelete(appendParam(url, dataMap));
		HttpResponse response = client.execute(httpdelete);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	
	/**
	 * @Description: 组装请求参数
	 * @param url
	 * @param paramMap
	 * @return String
	 */
	private static String appendParam(String url, Map<String, String> paramMap) {
		url += "?";
		for(Map.Entry<String, String> entry : paramMap.entrySet()) {
			url += entry.getKey() + "=" + entry.getValue() + "&";
		}
		url = url.substring(0, url.length() - 1);
		return url;
	}
	
	public static HttpUtil getHttpUtil() {
		//超时时间与读写时间各设为一分钟
		return HttpUtil.getInstance().readTimeout(60).connectTimeout(60);
	}
	
	/**
	 * @param url
	 * @param content
	 * @param contentType
	 * @return
	 */
	public static HttpResult sendPostString(String url, String content,MimeType contentType){
		HttpUtil httpUtil = getHttpUtil();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(content,"UTF-8"));
		post.setHeader("Content-Type", contentType.code());
		return httpUtil.execute(url, post, null, false, "UTF-8");
	}
}
