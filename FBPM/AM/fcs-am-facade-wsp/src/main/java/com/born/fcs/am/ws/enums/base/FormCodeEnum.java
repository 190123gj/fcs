package com.born.fcs.am.ws.enums.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单流程映射
 * 
 * @author wuzj
 */
public enum FormCodeEnum {

	DEFAULT("DEFAULT", "", "", "", "", "", "", "通用"),

	ASSETS_TRANSFER_APPLICATION("ASSETS_TRANSFER_APPLICATION", "", "",
			"/assetMg/transfer/view.htm", "/assetMg/transfer/edit.htm",
			"/assetMg/transfer/print.htm", "0", "资产转让");

	/** 表单code */
	private final String code;

	/**
	 * 对应流程编号
	 */
	private final String flowCode;

	/**
	 * 流程业务处理的相关serviceName
	 */
	private final String processServiceName;

	/** 枚举描述 */
	private final String message;

	/**
	 * 默认的验证状态
	 */
	private final String defaultCheckStatus;

	/**
	 * 表单查看Url
	 */
	private final String viewUrl;

	/**
	 * 编辑页面
	 */
	private final String editUrl;

	/**
	 * 打印页面
	 */
	private final String printUrl;

	/**
	 * 构造一个<code>FormCodeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormCodeEnum(String code, String flowCode,
			String processServiceName, String viewUrl, String editUrl,
			String printUrl, String defaultCheckStatus, String message) {
		this.code = code;
		this.flowCode = flowCode;
		this.processServiceName = processServiceName;
		this.defaultCheckStatus = defaultCheckStatus;
		this.viewUrl = viewUrl;
		this.editUrl = editUrl;
		this.printUrl = printUrl;
		this.message = message;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return Returns the code.
	 */
	public String getFlowCode() {
		return flowCode;
	}

	/**
	 * @return Returns the processServiceName.
	 */
	public String getProcessServiceName() {
		return processServiceName;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return Returns the viewUrl.
	 */
	public String getViewUrl() {
		return viewUrl;
	}

	/**
	 * @return Returns the editUrl.
	 */
	public String getEditUrl() {
		return editUrl;
	}

	/**
	 * @return Returns the printUrl.
	 */
	public String getPrintUrl() {
		return printUrl;
	}

	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}

	/**
	 * @return Returns the flowCode.
	 */
	public String flowCode() {
		return flowCode;
	}

	/**
	 * @return Returns the processServiceName.
	 */
	public String processServiceName() {
		return processServiceName;
	}

	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}

	/**
	 * @return Returns the viewUrl.
	 */
	public String viewUrl() {
		return viewUrl;
	}

	/**
	 * @return Returns the editUrl.
	 */
	public String editUrl() {
		return editUrl;
	}

	/**
	 * @return Returns the printUrl.
	 */
	public String printUrl() {
		return printUrl;
	}

	/**
	 * @return Returns the defaultCheckStatus.
	 */
	public String defaultCheckStatus() {
		return this.defaultCheckStatus;
	}

	/**
	 * @return Returns the defaultCheckStatus.
	 */
	public String getDefaultCheckStatus() {
		return this.defaultCheckStatus;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FormCodeEnum
	 */
	public static FormCodeEnum getByCode(String code) {
		for (FormCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormCodeEnum>
	 */
	public static List<FormCodeEnum> getAllEnum() {
		List<FormCodeEnum> list = new ArrayList<FormCodeEnum>();
		for (FormCodeEnum _enum : values()) {
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
		for (FormCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
