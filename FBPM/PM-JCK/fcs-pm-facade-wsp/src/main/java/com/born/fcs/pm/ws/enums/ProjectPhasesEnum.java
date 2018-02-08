package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目阶段
 *
 * @author wuzj
 */
public enum ProjectPhasesEnum {
	
	SET_UP_PHASES("SET_UP_PHASES", "立项阶段"),
	
	INVESTIGATING_PHASES("INVESTIGATING_PHASES", "尽调阶段"),
	
	COUNCIL_PHASES("COUNCIL_PHASES", "评审阶段"),
	
	RE_COUNCIL_PHASES("RE_COUNCIL_PHASES", "复议申请阶段"),
	
	CONTRACT_PHASES("CONTRACT_PHASES", "合同阶段"),
	
	LOAN_USE_PHASES("LOAN_USE_PHASES", "放用款阶段"),
	
	FUND_RAISING_PHASES("FUND_RAISING_PHASES", "资金募集阶段"),
	
	AFTERWARDS_PHASES("AFTERWARDS_PHASES", "保后阶段"),
	
	RECOVERY_PHASES("RECOVERY_PHASES", "追偿阶段"),
	
	FINISH_PHASES("FINISH_PHASES", "已完成");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProjectPhasesEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectPhasesEnum(String code, String message) {
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
	 * @return ProjectPhasesEnum
	 */
	public static ProjectPhasesEnum getByCode(String code) {
		for (ProjectPhasesEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectPhasesEnum>
	 */
	public static List<ProjectPhasesEnum> getAllEnum() {
		List<ProjectPhasesEnum> list = new ArrayList<ProjectPhasesEnum>();
		for (ProjectPhasesEnum _enum : values()) {
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
		for (ProjectPhasesEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
