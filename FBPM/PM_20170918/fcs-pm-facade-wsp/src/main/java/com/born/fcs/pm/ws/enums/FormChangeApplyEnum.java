/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 签报表单
 * 
 * @Author wuzj
 */
public enum FormChangeApplyEnum {
	
	CUSTOMER_DATA("CUSTOMER_DATA", "客户资料", "cutomerChangeProcessService",
					"/customerMg/customer/changeApply.htm"),
	
	PROJECT_APPROVAL("PROJECT_APPROVAL", "项目批复", "councilSummaryChangeProcessService",
						"/projectMg/meetingMg/summary/changeApply.htm"),
	
	CHARGE_REPAY_PLAY("CHARGE_REPAY_PLAY", "还款计划", "chargeRepayPlanService",
						"/projectMg/chargeRepayPlan/changeApply.htm"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 处理service */
	private final String processService;
	
	/** 表单地址 */
	private final String formUrl;
	
	/**
	 * 构造一个<code>FromChangeTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormChangeApplyEnum(String code, String message, String processService, String formUrl) {
		this.code = code;
		this.message = message;
		this.processService = processService;
		this.formUrl = formUrl;
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
	 * @return Returns the processService.
	 */
	public String getProcessService() {
		return processService;
	}
	
	/**
	 * @return Returns the formUrl.
	 */
	public String getFormUrl() {
		return formUrl;
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
	 * @return Returns the processService.
	 */
	public String processService() {
		return message;
	}
	
	/**
	 * @return Returns the formUrl.
	 */
	public String formUrl() {
		return formUrl;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FromChangeTypeEnum
	 */
	public static FormChangeApplyEnum getByCode(String code) {
		for (FormChangeApplyEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FromChangeTypeEnum>
	 */
	public static List<FormChangeApplyEnum> getAllEnum() {
		List<FormChangeApplyEnum> list = new ArrayList<FormChangeApplyEnum>();
		for (FormChangeApplyEnum _enum : values()) {
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
		for (FormChangeApplyEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
