package com.born.fcs.pm.ws.enums.managerbchange;

import java.util.ArrayList;
import java.util.List;

/**
 * B角变更生效阶段
 * 
 * @author wuzj
 */
public enum ChangeManagerbPhaseEnum {
	
	INVESTIGATING_PHASES("INVESTIGATING_PHASES", "尽职调查"),
	CONTRACT_PHASES("CONTRACT_PHASES", "合同签订"),
	CONFIRM_CREDIT_CONDITION("CONFIRM_CREDIT_CONDITION", "落实授信条件"),
	//AFTERWARDS_PHASES("AFTERWARDS_PHASES", "保后"),
	
	;
	
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
	private ChangeManagerbPhaseEnum(String code, String message) {
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
	public static ChangeManagerbPhaseEnum getByCode(String code) {
		for (ChangeManagerbPhaseEnum _enum : values()) {
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
	public static List<ChangeManagerbPhaseEnum> getAllEnum() {
		List<ChangeManagerbPhaseEnum> list = new ArrayList<ChangeManagerbPhaseEnum>();
		for (ChangeManagerbPhaseEnum _enum : values()) {
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
		for (ChangeManagerbPhaseEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
