package com.born.fcs.crm.ws.service.enums;

/** 渠道分类 */
public enum ChanalTypeEnum {
	YH("YH", "银行", "A"),
	ZQ("ZQ", "证券公司", "B"),
	XT("XT", "信托公司", "C"),
	JYPT("JYPT", "交易平台", "D"),
	JJ("JJ", "基金公司", "E"),
	ZL("ZL", "租赁公司", "F"),
	WLJR("WLJR", "网络金融平台", "G"),
	ZZYX("ZZYX", "自主营销", "H"),
	QT("QT", "其他渠道", "I"),
	BGS("BGS", "本公司", "J");
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 渠道编号开头 */
	private String preCode;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ChanalTypeEnum(String code, String message, String preCode) {
		this.code = code;
		this.message = message;
		this.preCode = preCode;
	}
	
	public String getPreCode() {
		return this.preCode;
	}
	
	public void setPreCode(String preCode) {
		this.preCode = preCode;
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
	 * @return ChanalTypeEnum
	 */
	public static ChanalTypeEnum getByCode(String code) {
		for (ChanalTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ChanalTypeEnum>
	 */
	public static java.util.List<ChanalTypeEnum> getAllEnum() {
		java.util.List<ChanalTypeEnum> list = new java.util.ArrayList<ChanalTypeEnum>(
			values().length);
		for (ChanalTypeEnum _enum : values()) {
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
		for (ChanalTypeEnum _enum : values()) {
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
		ChanalTypeEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 通过code获取编号前缀
	 * @param code 枚举值
	 * @return
	 */
	public static String getPreCodeByCode(String code) {
		if (code == null) {
			return null;
		}
		ChanalTypeEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getPreCode();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(ChanalTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
