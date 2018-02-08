package com.bornsoft.utils.enums;

import java.util.ArrayList;
import java.util.List;

import com.bornsoft.utils.base.IBornEnum;

/**
  * @Description: 通用枚举 
  * @author taibai@yiji.com 
  * @date  2016-8-5 下午2:09:14
  * @version V1.0
 */
public enum CommonResultEnum implements IBornEnum{

	/** 业务处理中 **/
	BIZ_PROCESSING("BIZ_PROCESSING", "业务正在处理中"),
	
	/** 执行成功 **/
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),
	
	/** 执行失败**/
	EXECUTE_FAILURE("EXECUTE_FAILURE", "执行失败"),
	
	/** 未知异常 */
	UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常"),
	;
	
	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	private CommonResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

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
	 * @param code
	 * @return LogResultEnum
	 */
	public static CommonResultEnum getByCode(String code) {
		for (CommonResultEnum _enum : values()) {
			if (_enum.getCode().equalsIgnoreCase(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 * 
	 * @return List<LogResultEnum>
	 */
	public List<CommonResultEnum> getAllEnum() {
		List<CommonResultEnum> list = new ArrayList<CommonResultEnum>();
		for (CommonResultEnum _enum : values()) {
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
		for (CommonResultEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

}
