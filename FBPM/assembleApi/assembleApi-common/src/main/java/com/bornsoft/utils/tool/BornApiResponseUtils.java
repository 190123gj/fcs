package com.bornsoft.utils.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yjf.common.fastjson.MoneySerializer;
import com.yjf.common.lang.util.money.Money;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Description:Api 回应报文常用工具类
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午2:20:19 
 * @version V1.0
 */
public final class BornApiResponseUtils {
	
	/**
	 * 生成JSON
	 * @param result
	 * @return
	 */
	public static <T> String toJSONString(T result) {
		if (result == null) return null;

		SerializeConfig config = new SerializeConfig();
		config.put(Money.class, MoneySerializer.INSTANCE);

		return JSON.toJSONString(result, config, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty);
	}

	/**
	 * 生成JSON
	 * @param result
	 * @return
	 */
	public static <T> String toJSONString(List<T> result) {
		if (result == null) return null;

		SerializeConfig config = new SerializeConfig();
		config.put(Money.class, MoneySerializer.INSTANCE);

		return JSON.toJSONString(result, config, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty);
	}

	/**
	 * 将返回结果放入返回Map中<BR>
	 * 由于签名返回结果时，签名要求不能为null，所以判断一下，给个空串.
	 */
	public static void putResponseKV(Map<String, String> r, String k, String v) {
		r.put(k, v == null ? StringUtils.EMPTY : v);
	}

	public static void putResponseKV(Map<String, Object> r, String k, Object v) {
		r.put(k, v == null ? StringUtils.EMPTY : v);
	}

	private BornApiResponseUtils() {
	}
}
