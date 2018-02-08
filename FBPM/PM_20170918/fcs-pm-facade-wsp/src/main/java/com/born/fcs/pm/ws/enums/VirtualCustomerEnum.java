package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟客户
 * 
 * @author wuzj
 */
public enum VirtualCustomerEnum {
	
	风险控制专题会项目处置("风险控制专题会项目处置", 999999999999999999L, "风险控制专题会项目处置"),
	风险控制专题会事项申报("风险控制专题会事项申报", 999999999999999998L, "风险控制专题会事项申报"),
	风险控制专题会其他申报("风险控制专题会其他申报", 999999999999999997L, "风险控制专题会其他申报");
	
	/** 枚举编码 */
	private final String code;
	
	/** 虚拟客户ID */
	private final long customerId;
	
	/** 虚拟客户名称 */
	private final String customerName;
	
	/**
	 * 构造一个<code>VirtualCustomerEnum</code>枚举对象
	 * 
	 * @param code
	 * @param customerName
	 */
	private VirtualCustomerEnum(String code, long customerId, String customerName) {
		this.code = code;
		this.customerId = customerId;
		this.customerName = customerName;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the customerId.
	 */
	public long getCustoemrId() {
		return customerId;
	}
	
	/**
	 * @return Returns the customerName.
	 */
	public String getCustoemrName() {
		return customerName;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the customerId.
	 */
	public long customerId() {
		return customerId;
	}
	
	/**
	 * @return Returns the customerName.
	 */
	public String customerName() {
		return customerName;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return VirtualCustomerEnum
	 */
	public static VirtualCustomerEnum getByCode(String code) {
		for (VirtualCustomerEnum _enum : values()) {
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
	 * @return VirtualCustomerEnum
	 */
	public static VirtualCustomerEnum getByCustomerId(long customerId) {
		for (VirtualCustomerEnum _enum : values()) {
			if (_enum.getCustoemrId() == customerId) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<VirtualCustomerEnum>
	 */
	public static List<VirtualCustomerEnum> getAllEnum() {
		List<VirtualCustomerEnum> list = new ArrayList<VirtualCustomerEnum>();
		for (VirtualCustomerEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
}
