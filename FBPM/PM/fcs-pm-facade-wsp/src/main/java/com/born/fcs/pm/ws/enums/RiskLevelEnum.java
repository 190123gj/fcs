package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 风险评级分类
 * 
 * @author lirz
 *
 * 2016-5-18 下午8:55:22
 */
public enum RiskLevelEnum {
	
	NOMAL("NOMAL", "正常类", 75),
	ATTENTION("ATTENTION", "关注类", 60),
	SECONDARY("SECONDARY", "次级类", 40),
	SUSPICIOUS("SUSPICIOUS", "可疑类", 30),
	LOSE("LOSE", "损失类", 0);
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	/** 最小分数 */
	private final int minScore;
	
	/**
	 * 构造一个<code>RiskLevelEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private RiskLevelEnum(String code, String message, int minScore) {
		this.code = code;
		this.message = message;
		this.minScore = minScore;
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
	
	public int getMinScore() {
		return minScore;
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
	
	public int minScore() {
		return minScore;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return RiskLevelEnum
	 */
	public static RiskLevelEnum getByCode(String code) {
		for (RiskLevelEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<RiskLevelEnum>
	 */
	public static List<RiskLevelEnum> getAllEnum() {
		List<RiskLevelEnum> list = new ArrayList<RiskLevelEnum>();
		for (RiskLevelEnum _enum : values()) {
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
		for (RiskLevelEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
