package com.bornsoft.pub.enums;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bornsoft.utils.tool.GsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @Description: 信用评级枚举
 * @author:      xiaohui@yiji.com
 * @date         2016-1-31 下午2:20:41
 * @version:     v1.0
 */
public enum CreditLevelEnum {
	A3("AAA", "得分≥90"),
	A2("AA", "80≤得分＜90"),
	A2_("AA-", "  76≤得分＜80"), 
	A("A+", " 70≤得分＜76"),
	A_("A", " 66≤得分＜70"), 
	BBB("BBB", "  60≤得分＜66"), 
	BB("BB", " 50≤得分＜60"), 
	B("B", "得分＜50"),
	F("F","被公司提起或准备提起法律诉讼的客户，不通过评定"),
	;
	
	/**
	 * 枚举值
	 */
	private final String code;

	/**
	 * 枚举描述
	 */
	private final String message;

	/**
	 * 构造一个<code>TransferType</code>枚举对象
	 */
	private CreditLevelEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @return DepositResultEnum
	 */
	public static CreditLevelEnum getByCode(String code) {
		for (CreditLevelEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 * 
	 * @return List<DepositResultEnum>
	 */
	public List<CreditLevelEnum> getAllEnum() {
		List<CreditLevelEnum> list = new ArrayList<CreditLevelEnum>();
		for (CreditLevelEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (CreditLevelEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

	{
		GsonUtil.builder.registerTypeAdapter(getClass(), new TypeAdapter<CreditLevelEnum>() {

			@Override
			public CreditLevelEnum read(JsonReader reader) throws IOException {
				if (reader.peek() == JsonToken.NULL) {
					reader.nextNull();
					return null;
				}else{
					String value = reader.nextString();
					return CreditLevelEnum.getByCode(value);
				}
			}

			@Override
			public void write(JsonWriter arg0, CreditLevelEnum arg1)
					throws IOException {
				if(arg1==null){
					arg0.nullValue();
				}else{
					arg0.value(arg1.code());
				}
				
			}
		});
	}
}
