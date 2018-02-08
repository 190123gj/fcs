package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议纪要 - 收费方式 - 收费阶段
 * 
 * @author wuzj
 */
public enum ChargeWayPhaseEnum {
	
	/*首次收费日期*/
	BEFORE_FIRST_LOAN("BEFORE_FIRST_LOAN", "首次放款"),
	BEFORE_FIRST_USE("BEFORE_FIRST_USE", "首次用款"),
	
	/**
	 * 根据业务类型不同描述为
	 * 
	 * 授信金额确认、发行金额确认、保全金额确认
	 */
	AMOUNT_CONFIRM("AMOUNT_CONFIRM", "金额确认"),
	
	/*第二次 ...第N次收费时间*/
	BEFORE_THIS_YEAR_LOAN("BEFORE_THIS_YEAR_LOAN", "本年度放款"),
	BEFORE_THIS_YEAR_USE("BEFORE_THIS_YEAR_USE", "本年度用款");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ChargeWayPhaseEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ChargeWayPhaseEnum(String code, String message) {
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
	 * @return ChargeWayPhaseEnum
	 */
	public static ChargeWayPhaseEnum getByCode(String code) {
		for (ChargeWayPhaseEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ChargeWayPhaseEnum>
	 */
	public static List<ChargeWayPhaseEnum> getAllEnum() {
		List<ChargeWayPhaseEnum> list = new ArrayList<ChargeWayPhaseEnum>();
		for (ChargeWayPhaseEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 首次收费阶段
	 * @return
	 */
	public static List<ChargeWayPhaseEnum> getFirstChageEnum() {
		List<ChargeWayPhaseEnum> list = new ArrayList<ChargeWayPhaseEnum>();
		list.add(BEFORE_FIRST_LOAN);
		list.add(BEFORE_FIRST_USE);
		list.add(AMOUNT_CONFIRM);
		return list;
	}
	
	/**
	 * 非首次收费阶段
	 * @return
	 */
	public static List<ChargeWayPhaseEnum> getNoneFirstChageEnum() {
		List<ChargeWayPhaseEnum> list = new ArrayList<ChargeWayPhaseEnum>();
		list.add(BEFORE_THIS_YEAR_LOAN);
		list.add(BEFORE_THIS_YEAR_USE);
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (ChargeWayPhaseEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
