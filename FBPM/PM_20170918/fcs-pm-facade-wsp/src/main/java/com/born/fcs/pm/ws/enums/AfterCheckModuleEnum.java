package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 保后检查报告 科目类型
 * 
 * @author lirz
 *
 * 2016-6-4 下午2:58:30
 */
public enum AfterCheckModuleEnum {
	
	FINANCIAL_STATEMENT("FINANCIAL_STATEMENT", "财务报表"), //三个表
	
	DRAFT_CAPITAL("DRAFT_CAPITAL", "企业资产情况调查工作底稿"),
	DRAFT_DEBT("DRAFT_DEBT", "企业负债调查情况工作底稿"),
	
	BANK_LOAN("BANK_LOAN", "银行贷款及其他融资"),
	
	DRAFT_INCOME("DRAFT_INCOME", "企业收入核实情况工作底稿"),
	DRAFT_COST("DRAFT_COST", "企业成本核实情况工作底稿"),
	
	COUNTER_CHECK("COUNTER_CHECK", "（反）担保措施检查"),
	OTHER_CHECK("OTHER_CHECK", "其他重要事项核查"),
	WARNING_AND_ATTENTION("WARNING_AND_ATTENTION", "预警信号或关注事项"),
	
	PROJECT_CHECK("PROJECT_CHECK", "开发项目完成情况检查"),
	CREDIT("CREDIT", "企业信用状况"),
	OTHER_DEBT("OTHER_DEBT", "其他负债情况调查"),
	
	CONTRACT_PROJECT("CONTRACT_PROJECT", "主要承包项目结构"),
	COST_CONSIST("COST_CONSIST", "成本机构及变动情况"),
	SUB_CONTRACTOR("SUB_CONTRACTOR", "项目分包商"),
	INVENTORY("INVENTORY", "存货"),
	PROJECTING("PROJECTING", "在建项目情况表"),
	SUCCESSFUL_PROJECT("SUCCESSFUL_PROJECT", "已中标未开工项目情况表"),
	
	HOLDER_BACKGROUND("HOLDER_BACKGROUND", "股东背景"),
	CHECK_PROCESS("CHECK_PROCESS", "审批流程"),
	LOAN_CHECK("LOAN_CHECK", "贷款结构检查"),
	LOAN_FOCUS("LOAN_FOCUS", "贷款集中度调查"),
	AVERAGE_CAPITAL("AVERAGE_CAPITAL", "平均资产情况调查"),
	LOAN_QUALITY("LOAN_QUALITY", "贷款质量调查")

	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>AfterCheckModuleEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AfterCheckModuleEnum(String code, String message) {
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
	 * @return AfterCheckModuleEnum
	 */
	public static AfterCheckModuleEnum getByCode(String code) {
		for (AfterCheckModuleEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<AfterCheckModuleEnum>
	 */
	public static List<AfterCheckModuleEnum> getAllEnum() {
		List<AfterCheckModuleEnum> list = new ArrayList<AfterCheckModuleEnum>();
		for (AfterCheckModuleEnum _enum : values()) {
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
		for (AfterCheckModuleEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
