/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:43:51 创建
 */
package com.born.fcs.fm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 预测类型
 * @author hjiajie
 * 
 */
public enum ForecastTypeEnum {
	
	////// 流出
	/** 银行融资担保 **/
	//用款事由为保证金划出
	OUT_YHRZDB("OUT_YHRZDB", "银行融资担保", "OUT", "outYhrzdbTime", "outYhrzdbTimeType", "", ""),
	/** 债权融资担保 **/
	//用款事由为保证金划出
	OUT_ZJRZDB("OUT_ZJRZDB", "债权融资担保", "OUT", "outXjrzdbTime", "outZjrzdbTimeType", "", ""),
	/** 金融产品担保 **/
	//用款事由为保证金划出
	OUT_JRCPDB("OUT_JRCPDB", "金融产品担保", "OUT", "outJrcpdbTime", "outJrcpdbTimeType", "", ""),
	/** 非融资担保业务 **/
	//用款事由为保证金划出
	OUT_FRZDBYW("OUT_FRZDBYW", "非融资担保业务", "OUT", "outFrzdbywTime", "outFrzdbywTimeType", "", ""),
	/** 再担保业务 **/
	//用款事由为保证金划出
	OUT_ZDBYW("OUT_ZDBYW", "再担保业务", "OUT", "outZdbywTime", "outZdbywTimeType", "", ""),
	/** 委托贷款业务 **/
	//用款事由为“委贷放款”
	OUT_WTDKYW("OUT_WTDKYW", "委托贷款业务", "OUT", "outWtdkywTime", "outWtdkywTimeType", "", ""),
	/** 小贷业务 **/
	//用款事由为保证金划出
	OUT_XDYW("OUT_XDYW", "小贷业务", "OUT", "outXdywTime", "outXdywTimeType", "", ""),
	/** 流出其他业务 **/
	//用款事由为保证金划出
	OUT_QTYW("OUT_QTYW", "其他业务", "OUT", "outQtywTime", "outqtywTimeType", "", ""),
	/** 理财业务 **/
	//立项申请单中“拟购买时间”为计划用款时间；产品转让时“回购时间”为计划用款时间
	OUT_LCYW("OUT_LCYW", "理财业务", "OUT", "outLcywTime", "outLcywTimeType", "", ""),
	/** 支付管理系统 **/
	//计划用款时间
	OUT_ZFGLXT("OUT_ZFGLXT", "支付管理系统", "OUT", "outXfglxtTime", "outXfglxtTimeType",
				"outXfglxtOtherTime", "outXfglxtOtherTimeType"),
	/** 退费申请单 **/
	//计划用款时间，审核未通过，则计划取消
	OUT_TFSQD("OUT_TFSQD", "退费申请单", "OUT", "outTfsqdTime", "outTfsqdTimeType", "outTfsqdOtherTime",
				"outTfsqdOtherTimeType"),
	/** 资产受让 **/
	//计划用款时间
	OUT_ZCSQ("OUT_ZCSQ", "资产受让", "OUT", "outZcsqTime", "outZcsqTimeType", "outZcsqOtherTime",
				"outZcsqOtherTimeType"),
	/** 受托清收 **/
	//计划用款时间
	OUT_STQS("OUT_STQS", "受托清收", "OUT", "outStqsTime", "outStqsTimeType", "", ""),
	/** 代偿款划出 **/
	//风险处置会会议纪要中的代偿截止时间
	OUT_DCKHC("OUT_DCKHC", "代偿款划出", "OUT", "outDckhcTime", "outDckhcTimeType", "", ""),
	/** 退还客户保证金 **/
	//项目到期日为退还客户保证金的预测时间
	OUT_THKHBZJ("OUT_THKHBZJ", "退还客户保证金", "OUT", "outThkhbzjTime", "outThkhbzjTimeType", "", ""),
	
	/** 内部资金拆借 **/
	OUT_INNER_LOAN("OUT_INNER_LOAN", "内部资金拆借", "OUT", "", "", "", ""),
	
	///////// 流入
	/** 银行融资担保 **/
	//流入事由为“收取担保费”
	IN_YHRZDB("IN_YHRZDB", "银行融资担保", "IN", "inYhrzdbTime", "inYhrzdbTimeType", "", ""),
	/** 债权融资担保 **/
	//流入事由为“收取担保费”
	IN_ZJRZDB("IN_ZJRZDB", "债权融资担保", "IN", "inZjrzdbtime", "inZjrzdbTimeType", "", ""),
	/** 金融产品担保 **/
	//流入事由为“收取担保费”
	IN_JRCPDB("IN_JRCPDB", "金融产品担保", "IN", "inJrcpdbTime", "inJrcpdbTimeType", "", ""),
	/** 非融资担保业务 **/
	//流入事由为“收取担保费”
	IN_FRZDBYW("IN_FRZDBYW", "非融资担保业务", "IN", "inFrzdbywTime", "inFrzdbywTimeType", "", ""),
	/** 再担保业务 **/
	//流入事由为“收取担保费”
	IN_ZDBYW("IN_ZDBYW", "再担保业务", "IN", "inZdbywTime", "inZdbywTimeType", "", ""),
	/** 委托贷款业务 **/
	//流入事由为“收取委贷利息”，委贷项目的还款计划中的时间为委贷本金收回时间
	IN_WTDKYW("IN_WTDKYW", "委托贷款业务", "IN", "inWtdkywTime", "inWtdkywTimeType", "", ""),
	/** 承销业务 **/
	//流入事由为“收取承销费”
	IN_CXYW("IN_CXYW", "承销业务", "IN", "inCxywTime", "inCxywTimeType", "", ""),
	/** 小贷业务 **/
	//流入事由为“收取承销费”
	IN_XDYW("IN_XDYW", "小贷业务", "IN", "inXdywTime", "inXdywTimeType", "", ""),
	/** 理财业务 **/
	//理财产品到期时间为预测资金流入时间；理财产品转让时间为预测资金流入时间
	IN_LCYW("IN_LCYW", "理财业务", "IN", "inLcyTime", "inLcywTimeType", "", ""),
	/** 经纪业务 **/
	//流入事由为“收费”
	IN_JJYW("IN_JJYW", "经纪业务", "IN", "inJjywTime", "inJjywTimeType", "", ""),
	/** 其他业务 **/
	//流入事由为“收费”
	IN_QTYW("IN_QTYW", "其他业务", "IN", "inQtywTime", "inQtywTimeType", "", ""),
	/** 代偿款收回 **/
	//流入事由为“代偿款收回”
	IN_DCKSH("IN_DCKSH", "代偿款收回", "IN", "inDckshTime", "inDckshTimeType", "", ""),
	/** 资产转让 **/
	//流入事由为“资产转让”
	IN_ZCZR("IN_ZCZR", "资产转让 ", "IN", "inXczrTime", "inXczrTimeType", "", ""),
	
	/** 内部资金拆借 **/
	IN_INNER_LOAN("IN_INNER_LOAN", "内部资金拆借", "IN", "", "", "", ""),
	
	/** 存出保证金划回 **/
	IN_DEPOSITS_DRAW_BACK("IN_DEPOSITS_DRAW_BACK", "存出保证金划回", "IN", "", "", "", ""),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 枚举描述 */
	private final String type;
	
	/** 枚举描述 */
	private final String forecastTime;
	
	/** 枚举描述 */
	private final String forecastTimeType;
	
	/** 枚举描述 */
	private final String forecastOtherTime;
	
	/** 枚举描述 */
	private final String forecastOtherTimeType;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ForecastTypeEnum(String code, String message, String type, String forecastTime,
								String forecastTimeType, String forecastOtherTime,
								String forecastOtherTimeType) {
		this.code = code;
		this.message = message;
		this.type = type;
		this.forecastTime = forecastTime;
		this.forecastTimeType = forecastTimeType;
		this.forecastOtherTime = forecastOtherTime;
		this.forecastOtherTimeType = forecastOtherTimeType;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	public String getForecastTime() {
		return this.forecastTime;
	}
	
	public String getForecastTimeType() {
		return this.forecastTimeType;
	}
	
	public String getForecastOtherTime() {
		return this.forecastOtherTime;
	}
	
	public String getForecastOtherTimeType() {
		return this.forecastOtherTimeType;
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
	
	public String getType() {
		return this.type;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return ForecastTypeEnum
	 */
	public static ForecastTypeEnum getByCode(String code) {
		for (ForecastTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return ForecastTypeEnum
	 */
	public static List<ForecastTypeEnum> getByType(String type) {
		List<ForecastTypeEnum> _enums = new ArrayList<ForecastTypeEnum>();
		for (ForecastTypeEnum _enum : values()) {
			if (_enum.getType().equals(type)) {
				_enums.add(_enum);
			}
		}
		return _enums;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ForecastTypeEnum>
	 */
	public static java.util.List<ForecastTypeEnum> getAllEnum() {
		java.util.List<ForecastTypeEnum> list = new java.util.ArrayList<ForecastTypeEnum>(
			values().length);
		for (ForecastTypeEnum _enum : values()) {
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
		for (ForecastTypeEnum _enum : values()) {
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
		ForecastTypeEnum _enum = getByCode(code);
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
	public static String getCode(ForecastTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
