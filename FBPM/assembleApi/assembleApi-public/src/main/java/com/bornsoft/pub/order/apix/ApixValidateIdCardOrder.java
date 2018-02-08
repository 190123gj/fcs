package com.bornsoft.pub.order.apix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bornsoft.pub.enums.ApixServiceEnum;
import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.SerializeAlias;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @Description: 身份证实名校验
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:29:54
 * @version V1.0
 */
public class ApixValidateIdCardOrder extends ApixOrderBase {

	/**
	 */
	private static final long serialVersionUID = 2254709073507994020L;
	
	@SerializeAlias(alias = "type")
	private ValidateIdCardType validateType;// 请求数据类型
	@SerializeAlias(alias = "cardno")
	private String certNo;// 身份证号
	private String name;// 姓名，utf-8编码
	
	public ApixValidateIdCardOrder() {
		
	}
	
	public ValidateIdCardType getValidateType() {
		return validateType;
	}

	/**
	 * 默认： ValidateIdCardType.Id_Card_Photo 
	 * @param validateType
	 */
	public void setValidateType(ValidateIdCardType validateType) {
		this.validateType = validateType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static enum ValidateIdCardType implements IBornEnum {

		Id_Card("idcard", "身份证核验"), Id_Card_Photo("idcard_photo", "身份证核验[返照片]"), ;

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
		private ValidateIdCardType(String code, String message) {
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
		public static ValidateIdCardType getByCode(String code) {
			for (ValidateIdCardType _enum : values()) {
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
		public List<ValidateIdCardType> getAllEnum() {
			List<ValidateIdCardType> list = new ArrayList<ValidateIdCardType>();
			for (ValidateIdCardType _enum : values()) {
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
			for (ValidateIdCardType _enum : values()) {
				list.add(_enum.code());
			}
			return list;
		}

		{
			GsonUtil.builder.registerTypeAdapter(getClass(),
					new TypeAdapter<ValidateIdCardType>() {

						@Override
						public ValidateIdCardType read(JsonReader reader)
								throws IOException {
							if (reader.peek() == JsonToken.NULL) {
								reader.nextNull();
								return null;
							} else {
								String value = reader.nextString();
								return ValidateIdCardType.getByCode(value);
							}
						}

						@Override
						public void write(JsonWriter arg0,
								ValidateIdCardType arg1) throws IOException {
							if (arg1 == null) {
								arg0.nullValue();
							} else {
								arg0.value(arg1.code());
							}

						}
					});
		}

	}
	
	@Override
	public void validateOrder() throws BornApiException {
		if(this.validateType==null){
			this.validateType = ValidateIdCardType.Id_Card_Photo;
		}
	}

	@Override
	public String getUrl() {
		return "http://v.apix.cn/apixcredit/idcheck/idcard";
	}
	
	@Override
	public String getService() {
		return ApixServiceEnum.ValidateIdCard.code();
	}

}
