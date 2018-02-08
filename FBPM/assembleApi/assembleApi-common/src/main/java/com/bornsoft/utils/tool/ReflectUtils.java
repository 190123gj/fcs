package com.bornsoft.utils.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.exception.BornApiException;

/**
 * @Description: 反射相关帮助类
 * @author xiaohui@yiji.com
 * @date 2015-10-9 下午3:31:35 
 * @version V1.0
 */
public class ReflectUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);


	/**
	 * 反射获取指定字段值
	 * @param fieldName
	 * @param obj
	 * @return
	 */
	public static Object getObjectValue(Field field, Object obj) {
		Object value = null;
		try {
			String methodName = (field.getType() == Boolean.class || field.getType() == boolean.class) ? getMethodName("is", field.getName()):getMethodName("get", field.getName());
			Method method = getDeclaredMethod(obj, methodName, new Class[0]);
			if (method != null) {
				method.setAccessible(true);
				value = method.invoke(obj, new Object[0]);
				method.setAccessible(false);
			}else{
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		} catch (Exception ex) { 
			logger.error("获取对象值失败:", ex);
			throw new IllegalArgumentException("获取对象值失败:" + ex.getMessage());
		}
		return value;
	}
	
	
	/**
	 * 反射获取指定字段值
	 * @param fieldName
	 * @param obj
	 * @return
	 */
	public static String getFieldValue(String fieldName, Object obj) {
		try {
			String methodName = getMethodName("get", fieldName);
			Method method = getDeclaredMethod(obj, methodName, new Class[0]);
			if (method != null) {
				method.setAccessible(true);
				return defaultObject(method.invoke(obj, new Object[0]));
			}
		} catch (Exception ex) { 
			logger.error("获取对象值失败,fieldName", ex);
			throw new IllegalArgumentException("获取对象值失败!fieldName:" + ex.getMessage());
		}
		return "";
	}

	/**
	 * 反射设置指定字段值
	 * @param fieldName
	 * @param obj
	 * @param fieldValue
	 */
	public static void setFieldValue(String fieldName, Object obj, String fieldValue) {
		try {
			String methodName = getMethodName("set", fieldName);
			Method method = getDeclaredMethod(obj, methodName, String.class);
			if (method != null) {
				method.setAccessible(true);
				method.invoke(obj, new Object[]{fieldValue});
			}
		} catch (Exception ex) {
			logger.error("设置对象值失败:", ex);
			throw new IllegalArgumentException("设置对象值失败!");
		}
	}

	/**
	 * 将Map值赋于实体类
	 * @param valueMap
	 * @param obj
	 */
	public static void parseToBean(Map<String, String> valueMap, Object obj) {
		for (Map.Entry<String, String> entry : valueMap.entrySet()) {
			setFieldValue(entry.getKey(), obj, entry.getValue());
		}
	}

	/**
	 * 将Map值赋于实体类[由于部分字段差异、可自提供映射关系]
	 * @param valueMap
	 * @param obj
	 * @param mappings
	 */
	public static void parseToBean(Map<String, String> valueMap, Object obj, Map<String, String> mappings) {
		String fieldName = "";
		for (Map.Entry<String, String> entry : valueMap.entrySet()) {
			fieldName = mappings.get(entry.getKey());
			if (fieldName != null) {
				setFieldValue(fieldName, obj, entry.getValue());
			} else {
				setFieldValue(entry.getKey(), obj, entry.getValue());
			}
		}
	}

	/**
	 * 反射设置指定字段值[字段的类型为集合]
	 * @param fieldName
	 * @param fieldValue
	 */
	public static void setListFieldValue(String fieldName, Object obj, List<?> list) {
		try {
			String methodName = getMethodName("set", fieldName);
			Method method = getDeclaredMethod(obj, methodName, List.class);
			if (method != null) {
				method.setAccessible(true);
				method.invoke(obj, new Object[]{list});
			}
		} catch (Exception ex) {
			logger.error("设置list对象值失败:", ex);
			throw new IllegalArgumentException("设置list属性字段值失败!");
		}
	}

	/**
	 * 把对象的字段组成MAP[只取String]
	 * @param isToUp
	 * @param obj
	 * @param paramMap
	 * @return Map<String, String>
	 */
	public static Map<String, String> parseToMap(Object obj) {
		return parseToMap(obj,null);
	}
	
	/**
	 * 将对象所有属性转为Map
	 * @param obj
	 * @return
	 */
	public static Map<Field,Object> getValueMap(Object obj){
		Class<?> topClass = Object.class;
		Map<Field,Object> valueMap = new HashMap<>();
		Field [] fields = null;
		
		for(Class<?> clazz = obj.getClass(); clazz != topClass ; clazz = clazz.getSuperclass()) {
			fields = clazz.getDeclaredFields();
			for(Field f: fields){
				if(!valueMap.containsKey(f.getName())){
					valueMap.put(f, getObjectValue(f, obj));
				}
			}
		}
		return valueMap;
	}
	/**
	 * 有上限遍历属性
	 * @param obj
	 * @param topClass 不包含topClass
	 * @return
	 */
	public static Map<String, String> parseToMap(Object obj,
			Class<?> topClass) {
		Map<String,String> paramMap = new HashMap<String, String>();
		try {
			if(topClass == null){
				topClass = Object.class;
			}
			Field [] fields = null;
			Method method = null;
			String fieldValue = "";

			for(Class<?> clazz = obj.getClass() ; clazz != topClass ; clazz = clazz.getSuperclass()) {
				fields = clazz.getDeclaredFields();
				for (Field field : fields) {
						if(StringUtils.equals(field.getName(), "serialVersionUID")){
							continue;
						}
						if(field.getType() == boolean.class || field.getType() == Boolean.class){
							method = clazz.getDeclaredMethod(getMethodName("is", field.getName()), new Class[0]);
						}else{
							method = clazz.getDeclaredMethod(getMethodName("get", field.getName()), new Class[0]);
						}
						if (method != null) {
							method.setAccessible(true);
							fieldValue = defaultObject (method.invoke(obj, new Object[0]));
						}
						if (fieldValue == null) { fieldValue = ""; }
						SerializeAlias alias = field.getAnnotation(SerializeAlias.class);
						if(alias!=null){
							if(!alias.ignore()){
								paramMap.put(alias.alias(), fieldValue);
							}
						}else{
							paramMap.put(field.getName(), fieldValue);
						}
				}
			}
			return paramMap;
		} catch(BornApiException e){
			logger.error("Bean转化为Map失败:", e);
			throw e;
		}catch (Exception ex) {
			logger.error("Bean转化为Map失败:", ex);
			throw new IllegalArgumentException("parseToBean failure!");
		}
	}

	/**
	 * 获取方法名称
	 * @param prefix
	 * @param fieldName
	 * @return
	 */
	private static String getMethodName(String prefix, String fieldName) {
		return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
	/**
	 * 获取方法
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	private static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){
		Method method = null ;
		for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {
			try {    
				method = clazz.getDeclaredMethod(methodName, parameterTypes) ;    
				return method ;   
			} catch (Exception e) {  
			}
		}
		return null;
	}

	/**
	 * 设置默认值
	 * @param obj
	 * @return
	 */
	private static String defaultObject(Object obj) {
		if (obj == null) {
			return "";
		} else if (obj instanceof Collection) {
			return GsonUtil.create().toJson(obj);
		} else if (obj instanceof IBornEnum) {
			return obj.getClass().asSubclass(IBornEnum.class).cast(obj).code();
		} else {
			return obj.toString();
		}
	}

	public static String toString(Object obj){
		return ToStringBuilder.reflectionToString(obj,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
