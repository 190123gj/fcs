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
	RELEASE_COUNTER_GUARANTEE("RELEASE_COUNTER_GUARANTEE", "解保申请-反担保措施附件"),
	/** 保后检查报告-附件 */
	AFTER_REPORT("AFTER_REPORT", "保后检查报告附件"),
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
	/** 保后检查报告-房地产开发-在建项目 */
	AFTER_REAL_ESTATE_PROJECTING("AFTER_REAL_ESTATE_PROJECTING", "保后检查报告-房地产开发-在建项目"),
	
	LOAN_USE_RECEIPT_VOUCHER("LOAN_USE_RECEIPT_VOUCHER", "放用款回执-放款凭证"),
	
	LOAN_USE_RECEIPT_OTHER("LOAN_USE_RECEIPT_OTHER", "放用款回执-其他"),
	
	LEGAL_DOCUMENTS("LEGAL_DOCUMENTS", "相关法律文书(保后-诉保)"),
	
	FORM_CHANGE_APPLY("FORM_CHANGE_APPLY", "签报附件"),
	
	INTERNAL_OPINION_EXCHANGE("INTERNAL_OPINION_EXCHANGE", "内审意见交换附件"),
	
	BUDGET_ATTACH("BUDGET_ATTACH", "预算附件"),
	
	INVESTIGATION_REPORT("INVESTIGATION_REPORT", "尽调报告附件"),
	
	INVESTIGATION_6("INVESTIGATION_6", "尽调-项目情况调查"),
	
	INVESTIGATION_FINANCIAL("INVESTIGATION_FINANCIAL", "尽调-财务报表附件"),
	
	PROJECT_END_FILE("PROJECT_END_FILE", "项目终止资料"),
	
	FINANCIAL_PROJECT_MAINTAIN("FINANCIAL_PROJECT_MAINTAIN", "理财项目信息维护"),
	FINANCIAL_TRANSFER_MAINTAIN("FINANCIAL_TRANSFER_MAINTAIN", "理财转让信息维护"),
	PROJECT_RISK_LOG("PROJECT_RISK_LOG", "风险项目管理日志"),
	
	FINANCIAL_REDEEM_MAINTAIN("FINANCIAL_REDEEM_MAINTAIN", "理财赎回信息维护"),
	
	CAPITAL_APPROPRIATION("CAPITAL_APPROPRIATION", "资金划付表单附件"),
	
	CAPITAL_APPROPRIATION_FINANCE_AFFIRM("CAPITAL_APPROPRIATION_FINANCE_AFFIRM", "资金划付申请单-财务确认附件"),
	
	CAPITAL_APPROPRIATION_FINANCE_AFFIRM_DETAIL("CAPITAL_APPROPRIATION_FINANCE_AFFIRM_DETAIL",
												"资金划付申请单-财务确认明细附件"),
	
	REFUND_APPLICATION("REFUND_APPLICATION", "退费申请表单附件"),
	
	ISSUE_VOUCHER("ISSUE_VOUCHER", "承销/发债信息维护-募集资金到账凭证"),
	
	ISSUE_LETTER("ISSUE_LETTER", "承销/发债信息维护-担保函"),
	
	CONTRACT_SCANURL("CONTRACT_SCANURL", "合同-回传文件"),
	
	CONTRACT_COURT_RULINGURL("CONTRACT_COURT_RULINGURL", "合同-法院裁定书"),
	
	CONTRACT_REFER("CONTRACT_REFER", "合同/文书/(函/通知书)-参考附件"),
	
	CONTRACT_REFER_ATTACHMENT("CONTRACT_REFER_ATTACHMENT", "合同/文书/(函/通知书)-附件"),
	
	CREDIT_REPORT("CREDIT_REPORT", "征信报告"),
	
	CREDIT_REPORT_ATTACHMENT("CREDIT_REPORT_ATTACHMENT", "征信报告附件"),
	
	EXPIRE_PROJECT_RECEIPT("EXPIRE_PROJECT_RECEIPT", "承销项目-到期通知列表还款凭证"),
	
	FILE_OUTPUT("FILE_OUTPUT", "档案-出库附件"),
	
	CHARGE_NOTIFICATION("CHARGE_NOTIFICATION", "收费通知单-委托付款说明"),
	
	CHARGE_NOTIFICATION_BASE("CHARGE_NOTIFICATION_BASE", "收费通知单-基本信息附件"),
	
	CHARGE_NOTIFICATION_FINANCE_AFFIRM("CHARGE_NOTIFICATION_FINANCE_AFFIRM", "收费通知单-财务确认附件"),
	
	CHARGE_NOTIFICATION_FINANCE_AFFIRM_DETAIL("CHARGE_NOTIFICATION_FINANCE_AFFIRM_DETAIL",
												"收费通知单-财务确认明细附件"),
	
	PROJECT_SETUP("PROJECT_SETUP", "项目立项-附件"),
	
	PROJECT_CREDIT_CONDITION_CONTRACT_AGREEMENT("PROJECT_CREDIT_CONDITION_CONTRACT_AGREEMENT",
												"授信落实附件-合同/协议"),
	PROJECT_CREDIT_CONDITION_ATTACHMENT("PROJECT_CREDIT_CONDITION_ATTACHMENT", "授信落实附件-普通附件"),
	PROJECT_RELATED("PROJECT_RELATED", "项目相关附件"),
	
	RISK_REVIEW("RISK_REVIEW", "风险审核报告相关附件"),
	
	PROJECT_INNOVATIVE_PRODUCT("PROJECT_INNOVATIVE_PRODUCT", "创新项目附件"),
	
	INVESTIGATION_INNOVATIVE_PRODUCT("INVESTIGATION_INNOVATIVE_PRODUCT", "创新项目尽职调查附件"),
	
	SUMMARY_INNOVATIVE_PRODUCT("SUMMARY_INNOVATIVE_PRODUCT", "创新项目上会会议纪要附件"),
	
	PROJECT_REVIEW_ATTACH("PROJECT_REVIEW_ATTACH", "项目评审会议纪要附件"),
	
	RISK_HANDLE_ATTACH("RISK_HANDLE_ATTACH", "风险处置会议纪要附件"),
	
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
