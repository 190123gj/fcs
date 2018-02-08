/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Filename EstateTradeAttachmentTypeEnum.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-4-10</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public enum CommonAttachmentTypeEnum {
	
	/** 表单附件 */
	FORM_ATTACH("FORM_ATTACH", "表单附件"),
	
	/** 反担保附件 */
	COUNTER_GUARANTEE("COUNTER_GUARANTEE", "反担保附件"),
	
	/** 承销项目附件 */
	UNDERWRITING_PROJECT("UNDERWRITING_PROJECT", "承销项目附件"),
	/** 风险处置上会申报附件 */
	RISK_HANDLE("RISK_HANDLE", "上会申报附件"),
	/** 解保申请-还款凭证 */
	RELEASE_REPAY("RELEASE_REPAY", "解保申请-还款凭证"),
	/** 解保申请-解除担保责任通知书 */
	RELEASE_REPORT("RELEASE_REPORT", "解保申请-解除担保责任通知书"),
	/** 解保申请-其他附件 */
	RELEASE_OTHER("RELEASE_OTHER", "解保申请-其他附件"),
	/** 保后检查报告-资料收集 */
	AFTER_DATA_COLLECT("AFTER_DATA_COLLECT", "保后检查报告-资料收集"),
	/** 保后检查报告-授信客户意见 */
	CUSTOMER_OPINION("CUSTOMER_OPINION", "保后检查报告-授信客户意见"),
	/** 保后检查报告-监管内容附件 */
	CONTENT_ATTACHMENT("CONTENT_ATTACHMENT", "保后检查报告-监管内容附件"),
	/** 保后检查报告-营业税完税凭证 */
	TAX_CERTIFICATE("TAX_CERTIFICATE", "保后检查报告-营业税完税凭证"),
	/** 保后检查报告-监管内容附件 */
	BANK_STATEMENT("BANK_STATEMENT", "保后检查报告-银行流水"),
	
	LOAN_USE_RECEIPT_VOUCHER("LOAN_USE_RECEIPT_VOUCHER", "放用款回执-放款凭证"),
	
	LOAN_USE_RECEIPT_OTHER("LOAN_USE_RECEIPT_OTHER", "放用款回执-其他"),
	
	LEGAL_DOCUMENTS("LEGAL_DOCUMENTS", "相关法律文书(保后-诉保)"),
	
	FORM_CHANGE_APPLY("FORM_CHANGE_APPLY", "签报附件"),
	
	INTERNAL_OPINION_EXCHANGE("INTERNAL_OPINION_EXCHANGE", "内审意见交换附件"),
	
	BUDGET_ATTACH("BUDGET_ATTACH", "预算附件"),
	
	INVESTIGATION_6("INVESTIGATION_6", "尽调-项目情况调查"),
	
	OTHER("OTHER", "其它附件"); //默认，一般不使用
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * CommonAttachmentTypeEnum
	 * @param code 编码
	 * @param message 描述
	 */
	private CommonAttachmentTypeEnum(String code, String message) {
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
	 * @return CommonAttachmentTypeEnum
	 */
	public static CommonAttachmentTypeEnum getByCode(String code) {
		for (CommonAttachmentTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	public List<CommonAttachmentTypeEnum> getAllEnum() {
		List<CommonAttachmentTypeEnum> list = new ArrayList<CommonAttachmentTypeEnum>();
		for (CommonAttachmentTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (CommonAttachmentTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
