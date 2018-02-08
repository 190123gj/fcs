package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目状态
 *
 * @author wuzj
 */
public enum ProjectStatusEnum {
	
	NORMAL("NORMAL", "正常"),
	
	FAILED("FAILED", "未成立"),
	
	PAUSE("PAUSE", "暂缓"),
	
	RECOVERY("RECOVERY", "追偿"),
	
	TRANSFERRED("TRANSFERRED", "已转让"),
	
	SELL_FINISH("SELL_FINISH", "发售完成"),
	
	FINISH("FINISH", "完成"),
	
	EXPIRE("EXPIRE", "即将到期"),
	
	/** 逾期 */
	OVERDUE("OVERDUE", "逾期")
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProjectStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectStatusEnum(String code, String message) {
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
	 * @return EnterpriseNatureEnum
	 */
	public static ProjectStatusEnum getByCode(String code) {
		for (ProjectStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EnterpriseNatureEnum>
	 */
	public static List<ProjectStatusEnum> getAllEnum() {
		List<ProjectStatusEnum> list = new ArrayList<ProjectStatusEnum>();
		for (ProjectStatusEnum _enum : values()) {
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
		for (ProjectStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
