package com.bornsoft.pub.enums;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: apix征信接口【用于统计,无实际作用】 
 * @author taibai@yiji.com
 * @date 2016-10-31 上午10:33:00
 * @version V1.0
 */
public enum ApixServiceEnum implements IBornEnum {

	DishonestQuery("dishonestQuery", "失信黑名单查询"),
	ValidateBankCard("validateBankCard", "银行卡校验"),
	ValidateIdCard("validateIdCard", "身份证校验"),
	ValidateMobile("validateMobile", "手机号校验"),
	;

	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	/**
	 * @param code
	 * @param message
	 */
	private ApixServiceEnum(String code, String message) {
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
	public List<ApixServiceEnum> getAllEnum() {
		List<ApixServiceEnum> list = new ArrayList<ApixServiceEnum>();
		for (ApixServiceEnum _enum : values()) {
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
	public static ApixServiceEnum getByCode(String code) {
		for (ApixServiceEnum _enum : values()) {
			if (StringUtils.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
	}
}