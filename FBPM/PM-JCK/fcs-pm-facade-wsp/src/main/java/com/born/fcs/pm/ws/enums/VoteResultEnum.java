package com.born.fcs.pm.ws.enums;

/**
 * 某次投票结果
 *
 *
 * @author Fei
 *
 */
public enum VoteResultEnum {
	
	UNKNOWN("UNKNOWN", "未投"),
	PASS("PASS", "通过"),
	NOTPASS("NOTPASS", "不通过"),
	CONDITIONAL_PASS("CONDITIONAL_PASS", "附条件通过"),
	QUIT("QUIT", "本次不议"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private VoteResultEnum(String code, String message) {
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
	 * @return VoteResultEnum
	 */
	public static VoteResultEnum getByCode(String code) {
		for (VoteResultEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<VoteResultEnum>
	 */
	public static java.util.List<VoteResultEnum> getAllEnum() {
		java.util.List<VoteResultEnum> list = new java.util.ArrayList<VoteResultEnum>(
			values().length);
		for (VoteResultEnum _enum : values()) {
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
		for (VoteResultEnum _enum : values()) {
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
		VoteResultEnum _enum = getByCode(code);
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
	public static String getCode(VoteResultEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
