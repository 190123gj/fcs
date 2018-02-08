package com.bornsoft.integration.kingdee;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

/**
 * Description: Https请求类
 * @author:		fruxue@yiji.com
 * @date:		2016-7-8上午11:25:54 
 * @version:	V1.0
 */
public class HttpRequestUtil {

	final static String PROTOCOL_NAME = "https";
	
	/**
	 * https post请求发送
	 * @param surl
	 * @param json
	 * @return
	 */
    public static String sendJsonWithHttps(String surl, String json) throws Exception
    {
        HttpClient client = new HttpClient();
        client.getParams().setContentCharset("UTF-8");
        PostMethod post = new PostMethod(surl);
        post.setRequestHeader("Content-Type", "application/json");
        RequestEntity requestEntity = new ByteArrayRequestEntity(json.getBytes("utf-8"));
        post.setRequestEntity(requestEntity);
        client.executeMethod(post);
        InputStream in = post.getResponseBodyAsStream();
        byte[] buf = new byte[512];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        do
        {
            int n = in.read(buf);
            if (n > 0)
            {
                baos.write(buf, 0, n);
            }
            else if (n <= 0)
            {
                break;
            }
        } while (true);
        return baos.toString();
    }
}
