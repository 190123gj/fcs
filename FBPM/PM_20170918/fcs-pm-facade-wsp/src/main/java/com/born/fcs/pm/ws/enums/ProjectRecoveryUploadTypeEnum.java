/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:18:52 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 项目追偿上传的附件的类型
 * @author hjiajie
 * 
 */
public enum ProjectRecoveryUploadTypeEnum {
	
	/*** 债务人重整或破产清算 开始 ***/
	/** 命名规则：recovery+大类型【reorganization或LITIGATION】+字段名/自定义名 **/
	//// //// 债务人重整或破产清算 主表 开始
	/*** 债务人重整或破产清算-我司申报时间-附件 **/
	RECOVERY_REORGANIZATION_DIVISION_WE_DECLARE_TIME(
														"RECOVERY_REORGANIZATION_DIVISION_WE_DECLARE_TIME",
														"我司申报时间"),
	/*** 债务人重整或破产清算-债权确认-附件 **/
	RECOVERY_REORGANIZATION_DEBTS_CONFIRM("RECOVERY_REORGANIZATION_DEBTS_CONFIRM", "债权确认"),
	/*** 债务人重整或破产清算-会议情况-附件 **/
	RECOVERY_REORGANIZATION_COUNCIL_CIRCUMSTANCES("RECOVERY_REORGANIZATION_COUNCIL_CIRCUMSTANCES",
													"会议情况"),
	/*** 债务人重整或破产清算-我司意见-附件 **/
	RECOVERY_REORGANIZATION_WE_SUGGESTION("RECOVERY_REORGANIZATION_WE_SUGGESTION", "我司意见"),
	/*** 债务人重整或破产清算-批准的重整方案及执行情况-附件 **/
	RECOVERY_REORGANIZATION_RE_EXECUTION_PLAN("RECOVERY_REORGANIZATION_RE_EXECUTION_PLAN",
												"批准的重整方案及执行情况"),
	/*** 债务人重整或破产清算-和解方案-附件 **/
	RECOVERY_REORGANIZATION_SETTLEMENT_SCHEME_CONTENT(
														"RECOVERY_REORGANIZATION_SETTLEMENT_SCHEME_CONTENT",
														"和解方案"),
	/*** 债务人重整或破产清算-清算方案-附件 **/
	RECOVERY_REORGANIZATION_LIQUIDATION_SCHEME("RECOVERY_REORGANIZATION_LIQUIDATION_SCHEME", "清算方案"),
	/*** 债务人重整或破产清算-清偿情况-附件 **/
	RECOVERY_REORGANIZATION_LIQUIDATION_SITUATION("RECOVERY_REORGANIZATION_LIQUIDATION_SITUATION",
													"清偿情况"),
	
	//// //// 债务人重整或破产清算 主表 结束
	//------------------------------------------------
	//////// 债务人重整或破产清算 债权人会议表 开始
	// 暂无
	//////// 债务人重整或破产清算 债权人会议表 结束
	//------------------------------------------------
	//////// 债务人重整或破产清算 回收金额明细表 开始
	// 暂无
	//////// 债务人重整或破产清算 回收金额明细表 结束
	//------------------------------------------------
	//////// 债务人重整或破产清算 抵质押资产抵债表 开始
	
	/*** 债务人重整或破产清算-抵质押资产抵债-默认附件-附件 **/
	RECOVERY_REORGANIZATION_PLEDGE_SOLD_OUT("RECOVERY_REORGANIZATION_PLEDGE_SOLD_OUT", "默认附件"),
	//////// 债务人重整或破产清算 抵质押资产抵债表 结束
	//------------------------------------------------
	/*** 债务人重整或破产清算 结束 ***/
	
	/*** 诉讼 开始 ***/
	
	////////诉前保全 开始
	/*** 诉讼-诉前保全-保全裁定书-附件 **/
	RECOVERY_LITIGATION_BEFORE_PRESERVATION_WRITTEN_VERDICT(
															"RECOVERY_LITIGATION_BEFORE_PRESERVATION_WRITTEN_VERDICT",
															"保全裁定书"),
	/*** 诉讼-诉前保全-协助执行通知书-附件 **/
	RECOVERY_LITIGATION_BEFORE_PRESERVATION_EXECUTION_NOTICE(
																"RECOVERY_LITIGATION_BEFORE_PRESERVATION_EXECUTION_NOTICE",
																"协助执行通知书"),
	/*** 诉讼-诉前保全-送达回执-附件 **/
	RECOVERY_LITIGATION_BEFORE_PRESERVATION_DELIVERY_RECEIPT(
																"RECOVERY_LITIGATION_BEFORE_PRESERVATION_DELIVERY_RECEIPT",
																"送达回执"),
	/*** 诉讼-诉前保全-其他-附件 **/
	RECOVERY_LITIGATION_BEFORE_PRESERVATION_OTHER("RECOVERY_LITIGATION_BEFORE_PRESERVATION_OTHER",
													"其他"),
	
	////////诉前保全 结束
	//------------------------------------------------
	
	////////立案 开始
	/*** 诉讼-立案-诉状-附件 **/
	RECOVERY_LITIGATION_PLACE_ON_FILE_PETITION("RECOVERY_LITIGATION_PLACE_ON_FILE_PETITION", "诉状"),
	/*** 诉讼-立案-证据清单及证据-附件 **/
	RECOVERY_LITIGATION_PLACE_ON_FILE_EVIDENCE("RECOVERY_LITIGATION_PLACE_ON_FILE_EVIDENCE",
												"证据清单及证据"),
	/*** 诉讼-立案-案件受理通知书-附件 **/
	RECOVERY_LITIGATION_PLACE_ON_FILE_ACCEPT_NOTICE(
													"RECOVERY_LITIGATION_PLACE_ON_FILE_ACCEPT_NOTICE",
													"案件受理通知书"),
	/*** 诉讼-立案-缴费通知书-附件 **/
	RECOVERY_LITIGATION_PLACE_ON_FILE_PAY_NOTICE("RECOVERY_LITIGATION_PLACE_ON_FILE_PAY_NOTICE",
													"缴费通知书"),
	/*** 诉讼-立案-其他-附件 **/
	RECOVERY_LITIGATION_PLACE_ON_FILE_OTHER("RECOVERY_LITIGATION_PLACE_ON_FILE_OTHER", "其他"),
	////////立案 结束
	//------------------------------------------------
	
	////////诉讼保全 开始
	/*** 诉讼-诉讼保全-保全裁定书-附件 **/
	RECOVERY_LITIGATION_PRESERVATION_WRITTEN_VERDICT(
														"RECOVERY_LITIGATION_PRESERVATION_WRITTEN_VERDICT",
														"保全裁定书"),
	/*** 诉讼-诉讼保全-协助执行通知书-附件 **/
	RECOVERY_LITIGATION_PRESERVATION_EXECUTION_NOTICE(
														"RECOVERY_LITIGATION_PRESERVATION_EXECUTION_NOTICE",
														"协助执行通知书"),
	/*** 诉讼-诉讼保全-送达回执-附件 **/
	RECOVERY_LITIGATION_PRESERVATION_DELIVERY_RECEIPT(
														"RECOVERY_LITIGATION_PRESERVATION_DELIVERY_RECEIPT",
														"送达回执"),
	/*** 诉讼-诉讼保全-其他-附件 **/
	RECOVERY_LITIGATION_PRESERVATION_OTHER("RECOVERY_LITIGATION_PRESERVATION_OTHER", "其他"),
	////////诉讼保全 结束
	//------------------------------------------------
	
	////////庭前准备 开始
	/*** 诉讼-庭前准备-开庭时间-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_OPENING_TIME("RECOVERY_LITIGATION_BEFORE_TRAIL_OPENING_TIME",
													"开庭时间"),
	/*** 诉讼-庭前准备-公告时间-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_NOTICE_TIME("RECOVERY_LITIGATION_BEFORE_TRAIL_NOTICE_TIME",
													"公告时间"),
	/*** 诉讼-庭前准备-文书送达时间-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_CLERK_ARRIVED_TIME(
														"RECOVERY_LITIGATION_BEFORE_TRAIL_CLERK_ARRIVED_TIME",
														"文书送达时间"),
	/*** 诉讼-庭前准备-管辖异议-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION(
															"RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION",
															"管辖异议"),
	/*** 诉讼-庭前准备-管辖异议裁定-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT(
																		"RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT",
																		"管辖异议裁定"),
	/*** 诉讼-庭前准备-管辖异议上诉-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_APPEAL(
																	"RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_APPEAL",
																	"管辖异议上诉"),
	/*** 诉讼-庭前准备-管辖异议二审裁定-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT_SECOND(
																			"RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT_SECOND",
																			"管辖异议二审裁定"),
	/*** 诉讼-庭前准备-证据交换-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_EVIDENCE_EXCHANGE(
														"RECOVERY_LITIGATION_BEFORE_TRAIL_EVIDENCE_EXCHANGE",
														"证据交换"),
	/*** 诉讼-庭前准备-鉴定申请-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_APPLY(
														"RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_APPLY",
														"鉴定申请"),
	/*** 诉讼-庭前准备-鉴定材料-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_MATERIAL(
														"RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_MATERIAL",
														"鉴定材料"),
	/*** 诉讼-庭前准备-鉴定费用-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_AMOUNT(
														"RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_AMOUNT",
														"鉴定费用"),
	/*** 诉讼-庭前准备-申请调查取证-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_INVESTIGATING_APPLY(
															"RECOVERY_LITIGATION_BEFORE_TRAIL_INVESTIGATING_APPLY",
															"申请调查取证"),
	/*** 诉讼-庭前准备-申请证人出庭-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_WITNESSES_APPLY(
														"RECOVERY_LITIGATION_BEFORE_TRAIL_WITNESSES_APPLY",
														"申请证人出庭"),
	/*** 诉讼-庭前准备-增加诉讼请求申请-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_INCREASE_LITIGATION_APPLY(
																"RECOVERY_LITIGATION_BEFORE_TRAIL_INCREASE_LITIGATION_APPLY",
																"增加诉讼请求申请"),
	/*** 诉讼-庭前准备-memo附件-附件 **/
	RECOVERY_LITIGATION_BEFORE_TRAIL_MEMO("RECOVERY_LITIGATION_BEFORE_TRAIL_MEMO", "增加诉讼请求申请"),
	
	////////庭前准备  结束
	//------------------------------------------------
	
	////////开庭 开始
	/*** 诉讼-开庭-我方主要诉讼请求或答辩意见-附件 **/
	RECOVERY_LITIGATION_OPENING_WE_LITIGATION_DEMAND(
														"RECOVERY_LITIGATION_OPENING_WE_LITIGATION_DEMAND",
														"我方主要诉讼请求或答辩意见"),
	/*** 诉讼-开庭-对方主要诉讼请求或答辩意见-附件 **/
	RECOVERY_LITIGATION_OPENING_OTHER_SIDE_LITIGATION_DEMAND(
																"RECOVERY_LITIGATION_OPENING_OTHER_SIDE_LITIGATION_DEMAND",
																"对方主要诉讼请求或答辩意见"),
	/*** 诉讼-开庭-补充证据-附件 **/
	RECOVERY_LITIGATION_OPENING_ADDITIONAL_EVIDENCE(
													"RECOVERY_LITIGATION_OPENING_ADDITIONAL_EVIDENCE",
													"补充证据"),
	/*** 诉讼-开庭-备注-附件 **/
	RECOVERY_LITIGATION_OPENING_MEMO("RECOVERY_LITIGATION_OPENING_MEMO", "备注"),
	////////开庭 结束
	//------------------------------------------------
	
	////////裁判 开始
	/*** 诉讼-裁判-裁判文书-附件 **/
	RECOVERY_LITIGATION_REFEREE_CLERK("RECOVERY_LITIGATION_REFEREE_CLERK", "裁判文书"),
	/*** 诉讼-裁判-送达时间-附件 **/
	RECOVERY_LITIGATION_REFEREE_ARRIVED_TIME("RECOVERY_LITIGATION_REFEREE_ARRIVED_TIME", "送达时间"),
	/*** 诉讼-裁判-公告时间-附件 **/
	RECOVERY_LITIGATION_REFEREE_NOTICE_TIME("RECOVERY_LITIGATION_REFEREE_NOTICE_TIME", "公告时间"),
	/*** 诉讼-裁判-生效时间-附件 **/
	RECOVERY_LITIGATION_REFEREE_EFFECTIVE_TIME("RECOVERY_LITIGATION_REFEREE_EFFECTIVE_TIME", "生效时间"),
	/*** 诉讼-裁判-备注-附件 **/
	RECOVERY_LITIGATION_REFEREE_MEMO("RECOVERY_LITIGATION_REFEREE_MEMO", "备注"),
	////////裁判 结束
	//------------------------------------------------
	
	////////二审上述 开始
	/*** 诉讼-二审上述-上诉请求-附件 **/
	RECOVERY_LITIGATION_SECOND_APPEAL_APPEAL_DEMAND(
													"RECOVERY_LITIGATION_SECOND_APPEAL_APPEAL_DEMAND",
													"上诉请求"),
	/*** 诉讼-二审上述-公告时间-附件 **/
	RECOVERY_LITIGATION_SECOND_APPEAL_NOTICE_TIME("RECOVERY_LITIGATION_SECOND_APPEAL_NOTICE_TIME",
													"公告时间"),
	/*** 诉讼-二审上述-开庭时间-附件 **/
	RECOVERY_LITIGATION_SECOND_APPEAL_OPENING_TIME(
													"RECOVERY_LITIGATION_SECOND_APPEAL_OPENING_TIME",
													"开庭时间"),
	/*** 诉讼-二审上述-新证据-附件 **/
	RECOVERY_LITIGATION_SECOND_APPEAL_NEW_EVIDENCE(
													"RECOVERY_LITIGATION_SECOND_APPEAL_NEW_EVIDENCE",
													"新证据"),
	/*** 诉讼-二审上述-争议焦点-附件 **/
	RECOVERY_LITIGATION_SECOND_APPEAL_CONTROVERSY_FOCUS(
														"RECOVERY_LITIGATION_SECOND_APPEAL_CONTROVERSY_FOCUS",
														"争议焦点"),
	/*** 诉讼-二审上述-备注-附件 **/
	RECOVERY_LITIGATION_SECOND_APPEAL_MEMO("RECOVERY_LITIGATION_SECOND_APPEAL_MEMO", "备注"),
	////////二审上述 结束
	//------------------------------------------------
	
	////////实现担保物权特别程序 开始
	/*** 诉讼-实现担保物权特别程序-裁判文书-附件 **/
	RECOVERY_LITIGATION_SPECIAL_PROCEDURE_REFEREE_CLERK(
														"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_REFEREE_CLERK",
														"裁判文书"),
	/*** 诉讼-实现担保物权特别程序-诉状-附件 **/
	RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PETITION(
													"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PETITION",
													"诉状"),
	/*** 诉讼-实现担保物权特别程序-证据清单及证据-附件 **/
	RECOVERY_LITIGATION_SPECIAL_PROCEDURE_EVIDENCE(
													"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_EVIDENCE",
													"证据清单及证据"),
	/*** 诉讼-实现担保物权特别程序-案件受理通知书-附件 **/
	RECOVERY_LITIGATION_SPECIAL_PROCEDURE_ACCEPT_NOTICE(
														"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_ACCEPT_NOTICE",
														"案件受理通知书"),
	/*** 诉讼-实现担保物权特别程序-缴费通知书-附件 **/
	RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PAY_NOTICE(
														"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PAY_NOTICE",
														"缴费通知书"),
	/*** 诉讼-实现担保物权特别程序-备注-附件 **/
	RECOVERY_LITIGATION_SPECIAL_PROCEDURE_MEMO("RECOVERY_LITIGATION_SPECIAL_PROCEDURE_MEMO", "备注"),
	
	////////实现担保物权特别程序 结束
	//------------------------------------------------
	
	////////强制执行公证执行证书 开始
	/*** 诉讼-强制执行公证执行证书-执行证书-附件 **/
	RECOVERY_LITIGATION_CERTIFICATE_NO("RECOVERY_LITIGATION_CERTIFICATE_NO", "执行证书"),
	/*** 诉讼-强制执行公证执行证书-申请书-附件 **/
	RECOVERY_LITIGATION_CERTIFICATE_DECLARATION("RECOVERY_LITIGATION_CERTIFICATE_DECLARATION",
												"申请书"),
	/*** 诉讼-强制执行公证执行证书-证据清单及证据-附件 **/
	RECOVERY_LITIGATION_CERTIFICATE_EVIDENCE("RECOVERY_LITIGATION_CERTIFICATE_EVIDENCE", "证据清单及证据"),
	/*** 诉讼-强制执行公证执行证书-缴费通知书-附件 **/
	RECOVERY_LITIGATION_CERTIFICATE_PAY_NOTICE("RECOVERY_LITIGATION_CERTIFICATE_PAY_NOTICE",
												"缴费通知书"),
	/*** 诉讼-强制执行公证执行证书-其他-附件 **/
	RECOVERY_LITIGATION_CERTIFICATE_OTHER("RECOVERY_LITIGATION_CERTIFICATE_OTHER", "其他"),
	/*** 诉讼-强制执行公证执行证书-备注-附件 **/
	RECOVERY_LITIGATION_CERTIFICATE_MEMO("RECOVERY_LITIGATION_CERTIFICATE_MEMO", "备注"),
	
	////////强制执行公证执行证书 结束
	//------------------------------------------------
	
	////////执行 开始
	/*** 诉讼-执行-强制执行申请-附件 **/
	RECOVERY_LITIGATION_EXECUTE_EXECUTE_APPLY("RECOVERY_LITIGATION_EXECUTE_EXECUTE_APPLY", "强制执行申请"),
	/*** 诉讼-执行-立案-附件 **/
	RECOVERY_LITIGATION_EXECUTE_PLACE_ON_FILE("RECOVERY_LITIGATION_EXECUTE_PLACE_ON_FILE", "立案"),
	/*** 诉讼-执行-受理法院-附件 **/
	RECOVERY_LITIGATION_EXECUTE_ACCEPTING_COURT("RECOVERY_LITIGATION_EXECUTE_ACCEPTING_COURT",
												"受理法院"),
	/*** 诉讼-执行-执行和解-附件 **/
	RECOVERY_LITIGATION_EXECUTE_COMPROMISE("RECOVERY_LITIGATION_EXECUTE_COMPROMISE", "执行和解"),
	/*** 诉讼-执行-调解-附件 **/
	RECOVERY_LITIGATION_EXECUTE_CONCILIATION("RECOVERY_LITIGATION_EXECUTE_CONCILIATION", "调解"),
	/*** 诉讼-执行-评估-附件 **/
	RECOVERY_LITIGATION_EXECUTE_ESTIMATE("RECOVERY_LITIGATION_EXECUTE_ESTIMATE", "评估"),
	
	/*** 诉讼-执行-备注-附件 **/
	RECOVERY_LITIGATION_EXECUTE_MEMO("RECOVERY_LITIGATION_EXECUTE_MEMO", "备注"),
	
	//---//---//  执行 -- 执行内容 开始
	/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
	RECOVERY_LITIGATION_EXECUTE_STUFF_MEMO("RECOVERY_LITIGATION_EXECUTE_STUFF_MEMO", "默认附件"),
	//---//---//  执行 -- 执行内容 结束
	//---//---//------------------------------//--//--//
	//---//---//  执行 -- 回收金额明细表 开始
	//暂无
	//---//---//  执行 -- 回收金额明细表 结束
	//---//---//------------------------------//--//--//
	//---//---//  执行 -- 抵质押资产抵债表 开始
	/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
	RECOVERY_LITIGATION_EXECUTE_PLEDGE_SOLD_OUT_MEMO(
														"RECOVERY_LITIGATION_EXECUTE_PLEDGE_SOLD_OUT_MEMO",
														"默认附件"),
	//---//---//  执行 -- 抵质押资产抵债表 结束
	//---//---//------------------------------//--//--//
	
	////////执行 结束
	//------------------------------------------------
	
	////////再审程序 一审 开始
	/*** 诉讼-再审程序 一审-我方主要诉讼请求或答辩意见-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_WE_LITIGATION_DEMAND(
																	"RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_WE_LITIGATION_DEMAND",
																	"我方主要诉讼请求或答辩意见"),
	/*** 诉讼-再审程序 一审-补充证据-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_ADDITIONAL_EVIDENCE(
																"RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_ADDITIONAL_EVIDENCE",
																"补充证据"),
	/*** 诉讼-再审程序 一审-备注-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_MEMO("RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_MEMO",
													"备注"),
	////////再审程序 一审 结束
	//------------------------------------------------
	
	////////再审程序 二审 开始											
	
	/*** 诉讼-再审程序 二审-上诉请求-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_SECOND_APPEAL_DEMAND(
														"RECOVERY_LITIGATION_ADJOURNED_SECOND_APPEAL_DEMAND",
														"上诉请求"),
	/*** 诉讼-再审程序 二审-公告时间-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_SECOND_NOTICE_TIME(
														"RECOVERY_LITIGATION_ADJOURNED_SECOND_NOTICE_TIME",
														"公告时间"),
	/*** 诉讼-再审程序 二审-开庭时间-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_SECOND_OPENING_TIME(
														"RECOVERY_LITIGATION_ADJOURNED_SECOND_OPENING_TIME",
														"开庭时间"),
	/*** 诉讼-再审程序 二审-新证据-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_SECOND_NEW_EVIDENCE(
														"RECOVERY_LITIGATION_ADJOURNED_SECOND_NEW_EVIDENCE",
														"新证据"),
	/*** 诉讼-再审程序 二审-争议焦点-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_SECOND_CONTROVERSY_FOCUS(
															"RECOVERY_LITIGATION_ADJOURNED_SECOND_CONTROVERSY_FOCUS",
															"争议焦点"),
	/*** 诉讼-再审程序 二审-备注-附件 **/
	RECOVERY_LITIGATION_ADJOURNED_SECOND_MEMO("RECOVERY_LITIGATION_ADJOURNED_SECOND_MEMO", "备注"),
	
	////////再审程序 二审 结束
	//------------------------------------------------
	
	////////执行回转 开始
	/*** 诉讼-执行回转-默认附件-附件 **/
	RECOVERY_LITIGATION_EXECUTE_GYRATION_MEMO("RECOVERY_LITIGATION_EXECUTE_GYRATION_MEMO", "默认附件"),
	////////执行回转 结束
	//------------------------------------------------
	
	/*** 诉讼 结束 ***/
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ProjectRecoveryUploadTypeEnum(String code, String message) {
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
	 * @return ProjectRecoveryUploadTypeEnum
	 */
	public static ProjectRecoveryUploadTypeEnum getByCode(String code) {
		for (ProjectRecoveryUploadTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectRecoveryUploadTypeEnum>
	 */
	public static java.util.List<ProjectRecoveryUploadTypeEnum> getAllEnum() {
		java.util.List<ProjectRecoveryUploadTypeEnum> list = new java.util.ArrayList<ProjectRecoveryUploadTypeEnum>(
			values().length);
		for (ProjectRecoveryUploadTypeEnum _enum : values()) {
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
		for (ProjectRecoveryUploadTypeEnum _enum : values()) {
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
		ProjectRecoveryUploadTypeEnum _enum = getByCode(code);
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
	public static String getCode(ProjectRecoveryUploadTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
