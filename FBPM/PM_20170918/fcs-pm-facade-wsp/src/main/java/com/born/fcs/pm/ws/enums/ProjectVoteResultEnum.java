package com.born.fcs.pm.ws.enums;

/**
 * 项目的投票结果
 *
 *
 * @author Fei
 *
 */
public enum ProjectVoteResultEnum {
	
	NOT_BEGIN("NOT_BEGIN", "未开始"),
	IN_VOTE("IN_VOTE", "投票中"),
	END_PASS("END_PASS", "已通过"),
	END_NOPASS("END_NOPASS", "未通过"),
	END_QUIT("END_QUIT", "本次不议"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ProjectVoteResultEnum(String code, String message) {
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
	public static ProjectVoteResultEnum getByCode(String code) {
		for (ProjectVoteResultEnum _enum : values()) {
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
	public static java.util.List<ProjectVoteResultEnum> getAllEnum() {
		java.util.List<ProjectVoteResultEnum> list = new java.util.ArrayList<ProjectVoteResultEnum>(
			values().length);
		for (ProjectVoteResultEnum _enum : values()) {
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
		for (ProjectVoteResultEnum _enum : values()) {
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
		ProjectVoteResultEnum _enum = getByCode(code);
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
	public static String getCode(ProjectVoteResultEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
