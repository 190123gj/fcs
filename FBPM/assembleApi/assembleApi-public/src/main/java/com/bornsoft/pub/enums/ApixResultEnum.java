package com.bornsoft.pub.enums;

import java.util.ArrayList;
import java.util.List;

import com.bornsoft.utils.base.IBornEnum;

/**
  * @Description: 通用枚举 
  * @author taibai@yiji.com 
  * @date  2016-8-5 下午2:09:14
  * @version V1.0
 */
public enum ApixResultEnum implements IBornEnum{

	/** 业务处理中 **/
	EXECUTE_SUCCESS("0", "验证信息一致"),
	EXECUTE_FAILED("-1", "验证失败"),
	NO_DISHONEST("1", "没有失信数据"),
	;
	
	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	private ApixResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}

	public static ApixResultEnum getByCode(String code) {
		for (ApixResultEnum _enum : values()) {
			if (_enum.code().equalsIgnoreCase(code)) {
				return _enum;
			}
		}
		return null;
	}

	public List<ApixResultEnum> getAllEnum() {
		List<ApixResultEnum> list = new ArrayList<ApixResultEnum>();
		for (ApixResultEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (ApixResultEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

}
