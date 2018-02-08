package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议纪要 - 还款方式 - 还款阶段
 * 
 * @author wuzj
 */
public enum RepayWayPhaseEnum {
	
	/*首次收费日期*/
	AFTER_FIRST_LOAN("AFTER_FIRST_LOAN", "首次放款"),
	AFTER_FIRST_USE("AFTER_FIRST_USE", "首次用款"),
	
	/**
	 * 根据业务类型不同描述为
	 * 
	 * 授信金额确认、发行金额确认、保全金额确认
	 */
	AMOUNT_CONFIRM("AMOUNT_CONFIRM", "金额确认");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>RepayWayPhaseEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private RepayWayPhaseEnum(String code, String message) {
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
	 * @param code
	 * @return RepayWayPhaseEnum
	 */
	public static RepayWayPhaseEnum getByCode(String code) {
		for (RepayWayPhaseEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<RepayWayPhaseEnum>
	 */
	public static List<RepayWayPhaseEnum> getAllEnum() {
		List<RepayWayPhaseEnum> list = new ArrayList<RepayWayPhaseEnum>();
		for (RepayWayPhaseEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (RepayWayPhaseEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
