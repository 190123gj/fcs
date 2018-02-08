package com.bornsoft.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.tool.GsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public enum CounciStatusEnum implements IBornEnum {
		NOT_BEGIN("NOT_BEGIN","未开始"),
		IN_PROGRESS("IN_PROGRESS","会议中"),
		BREAK_UP("BREAK_UP","已结束"),
		;
		
		/** 枚举值 */
		private final String code;

		/** 枚举描述 */
		private final String message;

		/**
		 * @param code
		 * @param message
		 */
		private CounciStatusEnum(String code, String message) {
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
		 * 获取全部枚举
		 * 
		 * @return List<BankCardTypeEnum>
		 */
		public List<CounciStatusEnum> getAllEnum() {
			List<CounciStatusEnum> list = new ArrayList<CounciStatusEnum>();
			for (CounciStatusEnum _enum : values()) {
				list.add(_enum);
			}
			return list;
		}
		
		/**
		 * 通过枚举<code>code</code>获得枚举
		 * 
		 * @param code
		 * @return CounciStatusEnum
		 */
		public static CounciStatusEnum getByCode(String code) {
			for (CounciStatusEnum _enum : values()) {
				if (StringUtils.equals(_enum.getCode(), code)) {
					return _enum;
				}
			}
			return null;
		}
		
		{
			GsonUtil.builder.registerTypeAdapter(getClass(), new TypeAdapter<CounciStatusEnum>() {

				@Override
				public CounciStatusEnum read(JsonReader reader) throws IOException {
					if (reader.peek() == JsonToken.NULL) {
						reader.nextNull();
						return null;
					}else{
						String value = reader.nextString();
						return CounciStatusEnum.getByCode(value);
					}
				}

				@Override
				public void write(JsonWriter arg0, CounciStatusEnum arg1)
						throws IOException {
					if(arg1==null){
						arg0.nullValue();
					}else{
						arg0.value(arg1.code());
					}
					
				}
			});
		}
		
public static void main(String[] args) {
	System.out.println(Arrays.toString(CounciStatusEnum.values()));
}	
}
