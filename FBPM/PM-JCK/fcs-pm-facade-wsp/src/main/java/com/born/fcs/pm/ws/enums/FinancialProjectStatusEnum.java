package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财项目状态
 * 
 * 所有理财产品第一次立项通过后，短期产品直接变成待购买，中长期走上会、上会通过后变成待购买
 * <p>
 * 第二次购买立项后直接变成待购买
 * </p>
 * @author wuzj
 */
public enum FinancialProjectStatusEnum {
	
	SET_UP_DRAFT("SET_UP_DRAFT", "立项申请中", false, false),
	SET_UP_AUDITING("SET_UP_AUDITING", "立项审核中", false, false),
	SET_UP_APPROVAL("SET_UP_APPROVAL", "立项通过", false, false),
	
	COUNCIL_WAITING("COUNCIL_WAITING", "立项上会中", false, false),
	
	CAPITAL_APPLY_WAITING("CAPITAL_APPLY_WAITING", "待购买", false, false),
	
	CAPITAL_APPLY_DRAFT("CAPITAL_APPLY_DRAFT", "资金划付申请中", false, false),
	CAPITAL_APPLY_AUDITING("CAPITAL_APPLY_AUDITING", "资金划付审核中", false, false),
	CAPITAL_APPLY_APPROVAL("CAPITAL_APPLY_APPROVAL", "资金划付通过", false, false),
	
	PURCHASED("PURCHASED", "已购买", true, true), //理财项目信息维护后
	
	TRANSFER_DRAFT("TRANSFER_DRAFT", "转让申请中", true, true),
	TRANSFER_AUDITING("TRANSFER_AUDITING", "转让审核中", true, true),
	TRANSFER_APPROVAL("TRANSFER_APPROVAL", "转让通过", true, true),
	TRANSFER_DENY("TRANSFER_DENY", "转让不通过", true, true),
	TRANSFER_COUNCIL_WAITING("TRANSFER_COUNCIL_WAITING", "转让上会中", true, true),
	TRANSFER_COUNCIL_APPROVAL("TRANSFER_COUNCIL_APPROVAL", "转让上会通过", true, true),
	TRANSFER_COUNCIL_DENY("TRANSFER_COUNCIL_DENY", "转让上会不通过", true, true),
	TRANSFERED("TRANSFERED", "转让完成", true, true),
	
	BUY_BACK_WAITING("BUY_BACK_WAITING", "待回购", true, true),
	BUY_BACK_APPLYING("BUY_BACK_APPLYING", "回购划付申请中", true, true),
	BUY_BACK_APPLY_APPROVAL("BUY_BACK_APPLY_APPROVAL", "回购划付通过", true, true),
	BUY_BACK_APPLY_DENY("BUY_BACK_APPLY_DENY", "回购划付未通过", true, true),
	BUY_BACK_FINISH("BUY_BACK_FINISH", "已回购", true, true),
	
	REDEEM_DRAFT("REDEEM_DRAFT", "提前赎回申请中", true, true),
	REDEEM_AUDITING("REDEEM_AUDITING", "提前赎回审核中", true, true),
	REDEEM_APPROVAL("REDEEM_APPROVAL", "提前赎回通过", true, true),
	REDEEM_DENY("REDEEM_DENY", "提前赎回不通过", true, true),
	
	REDEEMED("REDEEMED", "已赎回", true, true),
	EXPIRE("EXPIRE", "到期 ", false, false), //待维护到期信息
	FAILED("FAILED", "未成立", false, false),
	FINISH("FINISH", "完成", false, false);
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 是否可转让 */
	private boolean canTransfer;
	
	/** 是否可赎回 */
	private boolean canRedeem;
	
	/**
	 * 构造一个<code>FinancialProjectStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinancialProjectStatusEnum(String code, String message, boolean canTransfer,
										boolean canRedeem) {
		this.code = code;
		this.message = message;
		this.canTransfer = canTransfer;
		this.canRedeem = canRedeem;
	}
	
	public boolean canTransfer() {
		return canTransfer;
	}
	
	public boolean canRedeem() {
		return canRedeem;
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
	 * @return EnterpriseNatureEnum
	 */
	public static FinancialProjectStatusEnum getByCode(String code) {
		for (FinancialProjectStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EnterpriseNatureEnum>
	 */
	public static List<FinancialProjectStatusEnum> getAllEnum() {
		List<FinancialProjectStatusEnum> list = new ArrayList<FinancialProjectStatusEnum>();
		for (FinancialProjectStatusEnum _enum : values()) {
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
		for (FinancialProjectStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
