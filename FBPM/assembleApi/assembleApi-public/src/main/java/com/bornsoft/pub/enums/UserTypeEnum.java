package com.bornsoft.pub.enums;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.tool.GsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @Description: 用户类型枚举 
 * @author taibai@yiji.com
 * @date 2016-10-17 下午5:41:24
 * @version V1.0
 */
public enum UserTypeEnum implements IBornEnum{

	/**
	 * 企业
	 */
	BUSINESS("B","企业"),
	/**
	 * 个人账户
	 */
	PERSONAL("P","个人"),
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
	private UserTypeEnum(String code, String message) {
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
	public static UserTypeEnum getByCode(String code) {
		for (UserTypeEnum _enum : values()) {
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
	public List<UserTypeEnum> getAllEnum() {
		List<UserTypeEnum> list = new ArrayList<UserTypeEnum>();
		for (UserTypeEnum _enum : values()) {
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
		for (UserTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	{
		GsonUtil.builder.registerTypeAdapter(getClass(), new TypeAdapter<UserTypeEnum>() {

			@Override
			public UserTypeEnum read(JsonReader reader) throws IOException {
				if (reader.peek() == JsonToken.NULL) {
					reader.nextNull();
					return null;
				}else{
					String value = reader.nextString();
					return UserTypeEnum.getByCode(value);
				}
			}

			@Override
			public void write(JsonWriter arg0, UserTypeEnum arg1)
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
