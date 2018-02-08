package com.born.fcs.crm.ws.service.enums;

/**
 * 行业与评价模板选择
 * **/
public enum StandardUseEnums {
	
	A("A", "YB", "CW_NY", "000000", "农、林、牧、渔业"),
	B("B", "YB", "CW_GY", "000000", "采矿业"),
	C("C", "YB", "CW_GY", "000000", "制造业"),
	D("D", "YB", "CW_GY", "000000", "电力、热力、燃气及水生产和供应业"),
	E("E", "YB", "CW_JA", "000000", "建筑业"),
	F("F", "YB", "CW_SM", "000000", "批发和零售业"),
	G("G", "YB", "CW_JT", "000000", "交通运输、仓储和邮政业"),
	H("H", "YB", "CW_SM", "000000", "住宿和餐饮业"),
	I("I", "YB", "CW_ZH", "000000", "信息传输、软件和信息技术服务业"),
	J("J", "YB", "CW_ZH", "000000", "金融业"),
	K("K", "YB", "CW_FC", "000000", "房地产业"),
	L("L", "CT", "", "000", "租赁和商务服务业"),
	M("M", "YB", "CW_ZH", "000000", "科学研究和技术服务业"),
	N("N", "GY", "", "00000", "水利、环境和公共设施管理业"),
	O("O", "YB", "CW_ZH", "000000", "居民服务、修理和其他服务业"),
	P("P", "GY", "", "00000", "教育"),
	Q("Q", "GY", "", "00000", "卫生和社会工作"),
	R("R", "YB", "CW_ZH", "000000", "文化、体育和娱乐业"),
	S("S", "GY", "", "00000", "公共管理、社会保障和社会组织"),
	T("T", "YB", "CW_ZH", "000000", "国际组织"), ;
	
	/** 枚举值 */
	private final String code;
	
	/**
	 * 评价分类
	 * @param type YB：一般企业类；CT:城市开发类；GY:公用事业类
	 * 
	 * */
	private final String type;
	
	/**
	 * 可用财务指标
	 * @param financeType 详见 StandardTypeEnums
	 * */
	private final String financeType;
	
	/** 枚举描述 */
	private final String message;
	
	/** 页签完整性验证 */
	private final String checkStatus;
	
	/**
	 * 
	 * @param code 行业首字母
	 * @param type 评价分类
	 * @param financeType 一般分类所用财务指标
	 * @param message 行业描述
	 */
	private StandardUseEnums(String code, String type, String financeType, String checkStatus,
								String message) {
		this.code = code;
		this.message = message;
		this.type = type;
		this.financeType = financeType;
		this.checkStatus = checkStatus;
	}
	
	public String getCheckStatus() {
		return this.checkStatus;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getFinanceType() {
		return this.financeType;
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
	 * @return StandardUseEnums
	 */
	public static StandardUseEnums getByCode(String code) {
		for (StandardUseEnums _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<StandardUseEnums>
	 */
	public static java.util.List<StandardUseEnums> getAllEnum() {
		java.util.List<StandardUseEnums> list = new java.util.ArrayList<StandardUseEnums>(
			values().length);
		for (StandardUseEnums _enum : values()) {
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
		for (StandardUseEnums _enum : values()) {
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
		StandardUseEnums _enum = getByCode(code);
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
	public static String getCode(StandardUseEnums _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
	
	/** 获取评价类型 */
	public static String getTypeByCode(String code) {
		if (code == null) {
			return null;
		}
		StandardUseEnums _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getType();
	}
	
	/** 获取一般企业财务指标类型 */
	public static String getFinanceTypeByCode(String code) {
		if (code == null) {
			return null;
		}
		StandardUseEnums _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getFinanceType();
	}
	
}
