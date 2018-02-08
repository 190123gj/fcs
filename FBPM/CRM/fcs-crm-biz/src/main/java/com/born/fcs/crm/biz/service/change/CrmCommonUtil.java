package com.born.fcs.crm.biz.service.change;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author zhaohaibing
 * 
 * @Email abing@yiji.com
 * 
 * @Date: 2015-1-5
 * 
 * 
 */
public class CrmCommonUtil {
	private static java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
	
	/**
	 * 将查询的结果装入Map<String,String>
	 * 
	 * */
	public static Map<String, String> beanToMap(Object bean) {
		Map<String, String> map = new HashMap<String, String>();
		setMap(bean, map);
		return cleanNull(map);
		
	}
	
	public static Map<String, String> beanToMap(Object bean, Map<String, String> map) {
		setMap(bean, map);
		return cleanNull(map);
		
	}
	
	@SuppressWarnings("unchecked")
	private static void setMap(Object bean, Map<String, String> map) {
		Map<String, String> map0 = BeanMap.create(bean);
		Object sMap[] = map0.keySet().toArray();
		for (int i = 0; i < map0.size(); i++) {
			String key = (String) sMap[i];
			Object value = map0.get(sMap[i]);
			if (value != null) {
				String class0 = String.valueOf(value.getClass());
				if (class0.toLowerCase().indexOf("date") > -1) {
					map.put(key, DateUtil.dtSimpleFormat((Date) value));
				} else if (class0.toLowerCase().indexOf("money") > -1) {
					map.put(key, ((Money) value).toStandardString());
				} else if (class0.toLowerCase().indexOf("double") > -1) {
					map.put(key, df.format((double) value));
				} else {
					map.put(key, String.valueOf(value));
				}
				
			} else {
				map.put(key, "");
			}
			
		}
	}
	
	/**
	 * 将返回数据中的 null 换成 ""
	 * */
	public static Map<String, String> cleanNull(Map<String, String> map) {
		Object sMap[] = map.keySet().toArray();
		for (int i = 0; i < map.size(); i++) {
			if (StringUtil.isEmpty(map.get(sMap[i])) || "null".equalsIgnoreCase(map.get(sMap[i]))) {
				map.put((String) sMap[i], "");
			}
		}
		return map;
	}
	
}
