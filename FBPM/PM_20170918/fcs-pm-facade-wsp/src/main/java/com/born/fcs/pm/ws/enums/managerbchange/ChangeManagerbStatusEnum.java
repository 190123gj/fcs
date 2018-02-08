package com.born.fcs.pm.ws.enums.managerbchange;

import java.util.ArrayList;
import java.util.List;

/**
 * B角变更生效阶段
 * 
 * @author wuzj
 */
public enum ChangeManagerbStatusEnum {
	
	APPLYING("APPLYING", "申请中"),
	DENY("DENY", "不通过"),
	APPROVAL("APPROVAL", "通过"),
	WAIT_RESTORE("WAIT_RESTORE", "待还原"),
	APPLIED("APPLIED", "已生效"), ;
	
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
	private ChangeManagerbStatusEnum(String code, String message) {
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
	public static ChangeManagerbStatusEnum getByCode(String code) {
		for (ChangeManagerbStatusEnum _enum : values()) {
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
	public static List<ChangeManagerbStatusEnum> getAllEnum() {
		List<ChangeManagerbStatusEnum> list = new ArrayList<ChangeManagerbStatusEnum>();
		for (ChangeManagerbStatusEnum _enum : values()) {
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
		for (ChangeManagerbStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
