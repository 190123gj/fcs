package com.bornsoft.utils.tool;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bornsoft.utils.adapters.BornAdapterFactory;
import com.bornsoft.utils.exception.BornApiException;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yjf.common.lang.util.money.Money;

/**
  * @Description: Json工具类,Gson版 
  * @author taibai@yiji.com 
  * @date  2016-8-22 下午3:48:13
  * @version V1.0
 */
public class GsonUtil {

	public static  GsonBuilder builder = new GsonBuilder();
	/**
	 * 金额转换
	 */
	private static GsonBuilder standardBuilder =  new GsonBuilder();

	static{
		builder.registerTypeAdapter(Money.class, new MoneyAdapter());
		builder.registerTypeAdapter(Date.class, new DateAdapter());
		builder.registerTypeAdapterFactory(new BornAdapterFactory());
		builder.setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fa) {
				String name = fa.getName(); 
				return name.equals("__equalsCalc")||name.equals("__hashCodeCalc")||name.equals("typeDesc");
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});
		
		//金额转换
		standardBuilder.registerTypeAdapter(Money.class, new MoneyAdapter(){
			@Override
			public void write(JsonWriter writer, Money value)
					throws IOException {
				if (value == null) {
					writer.nullValue();
					return;
				}else{
					writer.value(value.toStandardString());
				}
			}
		});
		standardBuilder.registerTypeAdapter(Date.class, new DateAdapter());
		standardBuilder.registerTypeAdapterFactory(new BornAdapterFactory());
		
		standardBuilder.setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fa) {
				String name = fa.getName(); 
				return name.equals("__equalsCalc")||name.equals("__hashCodeCalc")||name.equals("typeDesc");
			}
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});
	}
	
	/**
	 * 将对象转换为json字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		return create().toJson(obj);
	}
	
	/**
	 * 将指定对象解析为 JSON 【version<9 以前money类型不变,version>=9后money类型变为标准格式】,单个money对象相当于一个String,不能调用此方法
	 * @param obj
	 * @return
	 */
	public static JSON toJSONObject(Object obj){
		String jsonStr = "";
		if(BornApiRequestUtils.getAppVersion()>=9){
			jsonStr = createGsonWithStandardMoneyAdapter().toJson(obj);
		}else{
			jsonStr = create().toJson(obj);
		}
		if(obj instanceof List){
			return JSONObject.parseArray(jsonStr);
		}else{
			return JSONObject.parseObject(jsonStr);
		}
	}

	public static Gson create(){
		return builder.create();
	}
	
	public static GsonBuilder createNewBuilder(ExclusionStrategy Strategy){
		GsonBuilder builder =  new GsonBuilder();
		builder.registerTypeAdapter(Money.class, new MoneyAdapter());
		builder.registerTypeAdapter(Date.class, new DateAdapter());
		return builder;
	}
	
	private  static  Gson createGsonWithStandardMoneyAdapter(){
		return standardBuilder.create();
	}
	
	private GsonUtil() {
		throw new AssertionError("静态类不能实例化");
	}
	
	public static  class MoneyAdapter extends TypeAdapter<Money>{

		@Override
		public Money read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}else{
				return Money.amout(reader.nextString());
			}
		}

		@Override
		public void write(JsonWriter writer, Money value) throws IOException {
			if (value == null) {
				writer.nullValue();
				return;
			}else{
				writer.value(value.toString());
			}
		}
	}
	
	public static class DateAdapter extends TypeAdapter<Date>{

		@Override
		public Date read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}else{
				try {
					return DateUtils.toDate(reader.nextString(), DateUtils.PATTERN2);
				} catch (ParseException e) {
					throw new BornApiException("日期解析失败");
				}
			}
		}

		@Override
		public void write(JsonWriter writer, Date value) throws IOException {
			if (value == null) {
				writer.nullValue();
				return;
			}else{
				writer.value(DateUtils.toString(value,DateUtils.PATTERN2));
			}
		}
	}
}
