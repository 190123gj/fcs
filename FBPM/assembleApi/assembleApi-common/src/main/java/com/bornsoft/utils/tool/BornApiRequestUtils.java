package com.bornsoft.utils.tool;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParserConfig;
import com.yjf.common.fastjson.MoneyDeserializer;
import com.yjf.common.lang.util.money.Money;

/**
 * @Description: API常用工具方法
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午2:19:54 
 * @version V1.0
 */
public final class BornApiRequestUtils {
	
	

	/**
	 * 获取当前请求接口版本号,默认为8
	 * @return
	 */
	public static int getAppVersion(){
		return AppUtils.getAppVersion();
	}
	
	public static Integer getParamterInteger(HttpServletRequest request, String reqKey, Integer defalut) {
		try {
			return Integer.parseInt(request.getParameter(reqKey));
		} catch (Exception e) {
			e.printStackTrace();
			return defalut;
		}
	}
	
	public static Long getParamterLong(HttpServletRequest request, String reqKey, Long defalut) {
		try {
			return Long.parseLong(request.getParameter(reqKey));
		} catch (Exception e) {
			e.printStackTrace();
			return defalut;
		}
	}
	
	public static Money getParameterMoney(HttpServletRequest request, String reqKey) {
		try {
			return new Money(request.getParameter(reqKey));
		} catch (Exception e) {
			return Money.zero();
		}
	}

	/**
	 * 解析请求参数
	 */
	public static String getParameter(HttpServletRequest request, String reqKey) {
		return StringUtils.defaultString(request.getParameter(reqKey), StringUtils.EMPTY);
	}

	public static String getParameter(HttpServletRequest request, String reqKey, String defaultV) {
		return StringUtils.defaultString(request.getParameter(reqKey), defaultV);
	}

	/**
	 * 解析数组
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		if (text == null) {
			return null;
		}

		List<T> list = null;
		ParserConfig config = new ParserConfig();
		config.getDerializers().put(Money.class, MoneyDeserializer.instance);

		DefaultJSONParser parser = new DefaultJSONParser(text, config);
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

		ParserConfig config = new ParserConfig();
		config.getDerializers().put(Money.class, MoneyDeserializer.instance);

		return JSON.parseObject(text, clazz, config, JSON.DEFAULT_PARSER_FEATURE);
	}

	private BornApiRequestUtils() {
	}
}
