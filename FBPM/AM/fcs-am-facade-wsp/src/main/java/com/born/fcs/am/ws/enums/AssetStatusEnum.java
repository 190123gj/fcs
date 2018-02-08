/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产状态
 * 
 * @author Ji
 *
 */
public enum AssetStatusEnum {
	
	NOT_USED("NOT_USED", "未使用"),
	// 拟抵（质）押的资产：在尽职调查报告中的资产信息,到授信条件落实审核通过前 ，为拟抵（质）押的资产；
	/**
	 * 说明：一个资产的状态不可能同时存在该表中的情况：当项目为抵债资产了，不能与其他状态共存，
	 * 即资产状态一旦变成抵债资产，则不能在尽职调查报告中选择重新关联新的项目了； 已转让的资产不能在新的尽职调查报告中关联到项目中；
	 */
	QUASI_PLEDGE("QUASI_PLEDGE", "拟抵押"),
	
	QUASI_MORTGAGE("QUASI_MORTGAGE", "拟质押"),
	
	// 已落实授信条件，授信条件落实单据审核通过
	SECURED_PLEDGE("SECURED_PLEDGE", "已办理抵押"),
	// 已落实授信条件，授信条件落实单据审核通过
	SECURED_MORTGAGE("SECURED_MORTGAGE", "已办理质押"),
	// 解保申请单审核通过；
	SOLUTION("SOLUTION", "已解保"),
	// 该项目在追偿跟踪表中标记为抵债资产的；该资产不能做重复的抵质押；
	DEBT_ASSET("DEBT_ASSET", "抵债资产"),
	// 在资产转让申请单中，该资产关联的项目为转让的，（转让确认的条件：该项目不上会，转让申请单审核通过；
	// 或者该项目转让需要上会，上会的会议纪要审核通过）；
	// 当资产受让中流程通过后，该资产恢复为抵债资产；
	TRANSFERRED("TRANSFERRED", "已转让"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>BooleanEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AssetStatusEnum(String code, String message) {
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
	 * @return BooleanEnum
	 */
	public static AssetStatusEnum getByCode(String code) {
		for (AssetStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<BooleanEnum>
	 */
	public static List<AssetStatusEnum> getAllEnum() {
		List<AssetStatusEnum> list = new ArrayList<AssetStatusEnum>();
		for (AssetStatusEnum _enum : values()) {
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
		for (AssetStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
