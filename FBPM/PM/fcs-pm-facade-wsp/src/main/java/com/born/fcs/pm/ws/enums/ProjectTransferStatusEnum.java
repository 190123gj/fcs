package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目移交状态
 *
 * @author wuzj
 */
public enum ProjectTransferStatusEnum {
	
	TRANSFER_DRAFT("TRANSFER_DRAFT", "草稿"),
	
	TRANSFER_AUDITING("TRANSFER_AUDITING", "移交审核中"),
	
	TRANSFER_SUCCESS("TRANSFER_SUCCESS", "移交成功"),
	
	TRANSFER_FAIL("TRANSFER_FAIL", "移交失败"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProjectTransferStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectTransferStatusEnum(String code, String message) {
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
	 * @return ProjectTransferStatusEnum
	 */
	public static ProjectTransferStatusEnum getByCode(String code) {
		for (ProjectTransferStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectTransferStatusEnum>
	 */
	public static List<ProjectTransferStatusEnum> getAllEnum() {
		List<ProjectTransferStatusEnum> list = new ArrayList<ProjectTransferStatusEnum>();
		for (ProjectTransferStatusEnum _enum : values()) {
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
		for (ProjectTransferStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
