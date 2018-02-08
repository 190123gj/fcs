package com.bornsoft.pub.enums;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.tool.GsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
  * @Description: 报表枚举 
  * @author taibai@yiji.com 
  * @date  2016-8-16 上午10:39:56
  * @version V1.0
 */
public enum CoreExportTypeEnum implements IBornEnum{
		
		POWER_MONTH("POWER_MONTH", "用电量月度报表"),
		;
		
		/** 枚举值 */
		private final String code;

		/** 枚举描述 */
		private final String message;

		/**
		 * @param code
		 * @param message
		 */
		private CoreExportTypeEnum(String code, String message) {
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
		public List<CoreExportTypeEnum> getAllEnum() {
			List<CoreExportTypeEnum> list = new ArrayList<CoreExportTypeEnum>();
			for (CoreExportTypeEnum _enum : values()) {
				list.add(_enum);
			}
			return list;
		}
		
		/**
		 * 通过枚举<code>code</code>获得枚举
		 * 
		 * @param code
		 * @return CoreExportTypeEnum
		 */
		public static CoreExportTypeEnum getByCode(String code) {
			for (CoreExportTypeEnum _enum : values()) {
				if (StringUtils.equals(_enum.getCode(), code)) {
					return _enum;
				}
			}
			return null;
		}
		
		{
			GsonUtil.builder.registerTypeAdapter(getClass(), new TypeAdapter<CoreExportTypeEnum>() {

				@Override
				public CoreExportTypeEnum read(JsonReader reader) throws IOException {
					if (reader.peek() == JsonToken.NULL) {
						reader.nextNull();
						return null;
					}else{
						String value = reader.nextString();
						return CoreExportTypeEnum.getByCode(value);
					}
				}

				@Override
				public void write(JsonWriter arg0, CoreExportTypeEnum arg1)
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