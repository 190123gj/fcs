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
  * @Description: 收支关系
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午2:39:23
  * @version V1.0
 */
public enum BudgetRelationEnum {

	UP("UP", "上游"), 
	DOWN("DOWN", "下游");
	
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
	private BudgetRelationEnum(String code, String message) {
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
	public static BudgetRelationEnum getByCode(String code) {
		for (BudgetRelationEnum _enum : values()) {
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
	public List<BudgetRelationEnum> getAllEnum() {
		List<BudgetRelationEnum> list = new ArrayList<BudgetRelationEnum>();
		for (BudgetRelationEnum _enum : values()) {
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
		for (BudgetRelationEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

	{
		GsonUtil.builder.registerTypeAdapter(getClass(), new TypeAdapter<BudgetRelationEnum>() {

			@Override
			public BudgetRelationEnum read(JsonReader reader) throws IOException {
				if (reader.peek() == JsonToken.NULL) {
					reader.nextNull();
					return null;
				}else{
					String value = reader.nextString();
					return BudgetRelationEnum.getByCode(value);
				}
			}

			@Override
			public void write(JsonWriter arg0, BudgetRelationEnum arg1)
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
