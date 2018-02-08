package com.bornsoft.utils.tool;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.fastjson.MoneySerializer;
import com.yjf.common.lang.util.money.Money;

/**
 * @Description: json转换帮助类
 * @author:      xiaohui@yiji.com
 * @date         2016-1-21 下午6:58:28
 * @version:     v1.0
 */
public class JsonParseUtil {

	public static SerializeConfig config = new SerializeConfig();
	static{
		config.put(Money.class, MoneySerializer.INSTANCE);
		config.put(CommonResultEnum.class, BornEnumSerializer.instance);
	}
	

	private static ParserConfig parserConfig = new ParserConfig();
	static {
		parserConfig.getDerializers().put(Money.class, MoneyDeserializer.instance);
	}
	
	private JsonParseUtil(){
		throw new AssertionError("静态类不能实例化");
	}
	/**
	 * 解析数组
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		if (text == null) {
			return null;
		}

		List<T> list = null;
		DefaultJSONParser parser = new DefaultJSONParser(text, parserConfig);
		JSONLexer lexer = parser.getLexer();
		if (lexer.token() == JSONToken.NULL) {
			lexer.nextToken();
			list = null;
		} else {
			list = new ArrayList<T>();
			parser.parseArray(clazz, list);
			parser.handleResovleTask(list);
		}

		parser.close();

		return list;
	}


	/**
	 * 解析对象
	 */
	public static <T> T parseObject(String text, Class<T> clazz) {
		if (text == null) return null;

		return JSON.parseObject(text, clazz, parserConfig, JSON.DEFAULT_PARSER_FEATURE);
	}
	
	/**
	 * 生成JSON
	 */
	public static <T> String toJSONString(T result) {
		if (result == null) return "";
		return JSON.toJSONString(result,config);
		
	}

	/**
	 * json-->Map<String, String>
	 * @param str
	 * @return
	 */
	public static Map<String, String> toMap(String str) {  
		HashMap<String, String> data = new HashMap<String, String>();  
		JSONObject jsonObject = JSONObject.parseObject(str);
		for (Entry<String, Object> entry : jsonObject.entrySet()) {
			data.put(entry.getKey(), String.valueOf(entry.getValue()));
		}
		return data;
	}  
	
	/**
	 * json-->Map<String, String>
	 * @param str
	 * @return
	 */
	public static Map<String, String> toMapDecode(String str) {  
		HashMap<String, String> data = new HashMap<String, String>();  
		JSONObject jsonObject = JSONObject.parseObject(str);
		for (Entry<String, Object> entry : jsonObject.entrySet()) {
			data.put(entry.getKey(), decode(String.valueOf(entry.getValue())));
		}
		return data;
	}  
	
	/**
	 * 生成JSON
	 * @param result
	 * @return
	 */
	public static <T> String toJSONString(List<T> result) {
		if (result == null || result.size() == 0) return "";
		System.out.println(111);
		return JSON.toJSONString(result, config, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty);
	}
	
	/**
	 * DECODE解码
	 * @param digestValue
	 * @return
	 */
	private static String decode(String digestValue) {
		try {
			digestValue = URLDecoder.decode(digestValue, "UTF-8");
		} catch (Exception ex) {}
		return digestValue;
	}

}
