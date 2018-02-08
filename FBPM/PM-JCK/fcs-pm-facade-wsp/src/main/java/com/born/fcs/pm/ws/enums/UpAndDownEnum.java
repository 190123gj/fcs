package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 上调/下调
 * 
 * @author lirz
 *
 * 2016-3-21 下午4:10:59
 */
public enum UpAndDownEnum {
	
	UP("UP", "上调( + )", "上游"),
	
	DOWN("DOWN", "下调( - )", "下游");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 上下游描述 */
	private final String stream;
	
	/**
	 * 构造一个<code>UpAndDownEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private UpAndDownEnum(String code, String message, String stream) {
		this.code = code;
		this.message = message;
		this.stream = stream;
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
	 * @return Returns the stream.
	 */
	public String getStream() {
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
	 * @return Returns the stream.
	 */
	public String stream() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return UpAndDownEnum
	 */
	public static UpAndDownEnum getByCode(String code) {
		for (UpAndDownEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<UpAndDownEnum>
	 */
	public static List<UpAndDownEnum> getAllEnum() {
		List<UpAndDownEnum> list = new ArrayList<UpAndDownEnum>();
		for (UpAndDownEnum _enum : values()) {
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
		for (UpAndDownEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
