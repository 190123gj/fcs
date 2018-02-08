package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *	比较运算符号
 * 
 * @author wuzj
 *
 */
public enum CompareEnum {
	
	EQUAL("EQUAL", "=", "=", "等于", ""),
	NOT_GREATER("NOT_GREATER", "≤", "<=", "小于等于", "不高于"),
	LESS("LESS", "<", "<", "小于", "低于"),
	NOT_LESS("NOT_LESS", "≥", ">=", "大于等于", "不低于"),
	GREATER("GREATER", ">", ">", "大于", "高于");
	
	/** 枚举值 */
	private final String code;
	
	/**
	 * 符号
	 */
	private final String symbol;
	
	/**
	 * 数学脚本
	 */
	private final String script;
	
	/** 枚举描述 */
	private final String desc;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CompareEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CompareEnum(String code, String symbol, String script, String desc, String message) {
		this.code = code;
		this.symbol = symbol;
		this.script = script;
		this.desc = desc;
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
	public String getDesc() {
		return desc;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the symbol.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * @return Returns the script.
	 */
	public String getScript() {
		return script;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the script.
	 */
	public String script() {
		return script;
	}
	
	/**
	 * @return Returns the symbol.
	 */
	public String symbol() {
		return symbol;
	}
	
	/**
	 * @return Returns the desc.
	 */
	public String desc() {
		return desc;
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
	 * @return CompareEnum
	 */
	public static CompareEnum getByCode(String code) {
		for (CompareEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CompareEnum>
	 */
	public static List<CompareEnum> getAllEnum() {
		List<CompareEnum> list = new ArrayList<CompareEnum>();
		for (CompareEnum _enum : values()) {
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
		for (CompareEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
