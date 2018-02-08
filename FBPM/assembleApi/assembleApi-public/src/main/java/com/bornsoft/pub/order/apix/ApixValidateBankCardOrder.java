package com.bornsoft.pub.order.apix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bornsoft.pub.enums.ApixServiceEnum;
import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.SerializeAlias;
import com.bornsoft.utils.tool.ValidateParamUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @Description: 银行卡实名校验
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:29:54
 * @version V1.0
 */
public class ApixValidateBankCardOrder extends ApixOrderBase {

	/**
	 */
	private static final long serialVersionUID = -6862155143174953904L;
	
	@SerializeAlias(alias = "type")
	private ValidateTypeEnum validateType;//核验类型参数，包括二元素、三元素和四元素
	@SerializeAlias(alias = "bankcardno")
	private String bankCardNo;//银行卡号
	private String name;//姓名
	@SerializeAlias(alias = "idcardno")
	private String certNo;//身份证号
	@SerializeAlias(alias = "phone")
	private String mobile;//预留手机号
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTrue(validateType!=null, "校验类型不能为空");
		ValidateParamUtil.hasText(bankCardNo, "银行卡号不能为空");
		ValidateParamUtil.hasText(name, "姓名不能为空");
		if(ValidateTypeEnum.BANK_CARD_FOUR==validateType){
			ValidateParamUtil.hasText(certNo, "身份证号码不能为空");
			ValidateParamUtil.hasText(mobile, "手机号不能为空");
		}else if(ValidateTypeEnum.BANK_CARD_THREE==validateType){
			ValidateParamUtil.hasText(certNo, "身份证号码不能为空");
		}else if(ValidateTypeEnum.BANK_CARD_PHONE==validateType){
			ValidateParamUtil.hasText(mobile, "手机号不能为空");
		}
	}
	
	public ValidateTypeEnum getValidateType() {
		return validateType;
	}

	public void setValidateType(ValidateTypeEnum validateType) {
		this.validateType = validateType;
	}


	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String getUrl() {
		return "http://v.apix.cn/apixcredit/idcheck/bankcard";
	}
	
	@Override
	public String getService() {
		return ApixServiceEnum.ValidateBankCard.code();
	}
	
	public static enum ValidateTypeEnum implements IBornEnum{

		BANK_CARD_NAME("bankcard_name", "银行卡+姓名"), 
		BANK_CARD_PHONE("bankcard_phone", "银行卡+手机号"),
		BANK_CARD_THREE("bankcard_three", "银行卡+姓名+身份证号"),
		BANK_CARD_FOUR("bankcard_four", "银行卡+姓名+身份证号+手机号"),
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
		private ValidateTypeEnum(String code, String message) {
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
		public static ValidateTypeEnum getByCode(String code) {
			for (ValidateTypeEnum _enum : values()) {
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
		public List<ValidateTypeEnum> getAllEnum() {
			List<ValidateTypeEnum> list = new ArrayList<ValidateTypeEnum>();
			for (ValidateTypeEnum _enum : values()) {
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
			for (ValidateTypeEnum _enum : values()) {
				list.add(_enum.code());
			}
			return list;
		}

		{
			GsonUtil.builder.registerTypeAdapter(getClass(), new TypeAdapter<ValidateTypeEnum>() {

				@Override
				public ValidateTypeEnum read(JsonReader reader) throws IOException {
					if (reader.peek() == JsonToken.NULL) {
						reader.nextNull();
						return null;
					}else{
						String value = reader.nextString();
						return ValidateTypeEnum.getByCode(value);
					}
				}

				@Override
				public void write(JsonWriter arg0, ValidateTypeEnum arg1)
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

}
