package com.born.fcs.crm.ws.service.enums;

/**
 * 评价等级枚举
 * */
public enum EvaluetingLevelEnum {
	AAA("AAA", 90, 200, "得分≥90"),
	AA("AA", 80, 90, "80≤得分＜90"),
	AA_("AA-", 76, 80, "76≤得分＜80"),
	A0("A+", 70, 76, "70≤得分＜76"),
	A("A", 66, 70, "66≤得分＜70  "),
	BBB("BBB", 60, 66, "60≤得分＜66"),
	BB("BB", 50, 60, "50≤得分＜60"),
	B("B", -100, 50, "得分＜50"),
	F("F", -999, -998, "被公司提起或准备提起法律诉讼的客户"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 等级分数下限 */
	private final double startScore;
	/** 等级分数上限 */
	private final double endScore;
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 * @param startScore 等级分数下限
	 * @param endScore 等级分数上限
	 */
	private EvaluetingLevelEnum(String code, int startScore, int endScore, String message) {
		this.code = code;
		this.message = message;
		this.startScore = startScore;
		this.endScore = endScore;
	}
	
	/**
	 * 获取当前评分对应等级
	 * */
	public static String getLevel(double score) {
		for (EvaluetingLevelEnum _enum : values()) {
			if (score >= _enum.getStartScore() && score < _enum.getEndScore()) {
				return _enum.getCode();
			}
		}
		return "";
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
	
	public double getStartScore() {
		return this.startScore;
	}
	
	public double getEndScore() {
		return this.endScore;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return EvaluetingLevelEnum
	 */
	public static EvaluetingLevelEnum getByCode(String code) {
		for (EvaluetingLevelEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EvaluetingLevelEnum>
	 */
	public static java.util.List<EvaluetingLevelEnum> getAllEnum() {
		java.util.List<EvaluetingLevelEnum> list = new java.util.ArrayList<EvaluetingLevelEnum>(
			values().length);
		for (EvaluetingLevelEnum _enum : values()) {
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
		for (EvaluetingLevelEnum _enum : values()) {
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
		EvaluetingLevelEnum _enum = getByCode(code);
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
	public static String getCode(EvaluetingLevelEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
