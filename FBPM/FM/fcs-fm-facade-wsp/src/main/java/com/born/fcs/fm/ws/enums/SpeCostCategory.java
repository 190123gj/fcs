package com.born.fcs.fm.ws.enums;
/**
 * 特殊费用种类
 * @author lzb
 *
 */
public enum SpeCostCategory {
	
	/** 差旅费 */
	TRAVEL("TRAVEL", "正常", 1),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 费用种类ID */
	private final long categoryId;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private SpeCostCategory(String code, String message, long categoryId) {
		this.code = code;
		this.message = message;
		this.categoryId = categoryId;
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	
	public long categoryId() {
		return categoryId;
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
	 * @return SpeCostCategory
	 */
	public static SpeCostCategory getByCode(String code) {
		for (SpeCostCategory _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SpeCostCategory>
	 */
	public static java.util.List<SpeCostCategory> getAllEnum() {
		java.util.List<SpeCostCategory> list = new java.util.ArrayList<SpeCostCategory>(
			values().length);
		for (SpeCostCategory _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (SpeCostCategory _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		SpeCostCategory _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(SpeCostCategory _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
