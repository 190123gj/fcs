package com.born.fcs.am.ws.enums.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 收费基数基准
 * 
 * @author wuzj
 *
 */
public enum RelatedUserTypeEnum {

	BUSI_MANAGER("BUSI_MANAGER", "业务经理"),

	BUSI_MANAGERB("BUSI_MANAGERB", "业务经理B角"),

	RISK_MANAGER("RISK_MANAGER", "风险经理"),

	LEGAL_MANAGER("LEGAL_MANAGER", "法务经理"),

	FINANCIAL_MANAGER("FINANCIAL_MANAGER", "财务经理"),

	FLOW_SUBMIT_MAN("FLOW_SUBMIT_MAN", "流程发起人"),

	FLOW_PROCESS_MAN("FLOW_PROCESS_MAN", "流程处理人员"),

	FLOW_CANDIDATE_MAN("FLOW_CANDIDATE_MAN", "流程候选人员"),

	COUNCIL_JUDGE("COUNCIL_JUDGE", "参会评委"),

	COUNCIL_PARTICIPANT("COUNCIL_PARTICIPANT", "会议列席人员"),

	OTHER("OTHER", "其他");

	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	/**
	 * 构造一个<code>RelatedUserTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private RelatedUserTypeEnum(String code, String message) {
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
	 * @return RelatedUserTypeEnum
	 */
	public static RelatedUserTypeEnum getByCode(String code) {
		for (RelatedUserTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 * 
	 * @return List<RelatedUserTypeEnum>
	 */
	public static List<RelatedUserTypeEnum> getAllEnum() {
		List<RelatedUserTypeEnum> list = new ArrayList<RelatedUserTypeEnum>();
		for (RelatedUserTypeEnum _enum : values()) {
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
		for (RelatedUserTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
