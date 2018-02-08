package com.bornsoft.pub.enums;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: 金蝶服务枚举
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午11:28:44
 * @version:     v1.0
 */
public enum KingdeeServiceEnum implements IBornEnum {

	GATHERING("skSyncService", "收款单同步"),
	PAYMENT("fkSyncService", "付款单同步"),
	TRAVEL_EXPENSE("clfSyncService", "差旅费报销单同步"),
	EXPENSES_REQUISITIONS("feeApplySyncInfo", "费用申请单"),
	TERNAL_TRANS("internalTransSyncService", "内部转账单"),//FIXME 这是几个意思  
	QUERY_ACCOUNT("kjkmQueryService", "查询会计科目"),
	QUERY_GUARANTEE("bzjzhQueryService", "查询保证金账户"),
	SYN_ORGANIZATION("orgSyncService", "组织架构同步"),
	SYN_CLEARKA("userSyncService", "职员信息同步"),
	SYN_CUSTOMER("customerSyncService", "客户信息同步"),
	SYN_CONTRACT("contractSyncService", "合同信息同步"),
	;

	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	/**
	 * @param code
	 * @param message
	 */
	private KingdeeServiceEnum(String code, String message) {
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
	 * 获取全部枚举
	 * 
	 * @return List<BankCardTypeEnum>
	 */
	public List<KingdeeServiceEnum> getAllEnum() {
		List<KingdeeServiceEnum> list = new ArrayList<KingdeeServiceEnum>();
		for (KingdeeServiceEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return CoreExportTypeEnum
	 */
	public static KingdeeServiceEnum getByCode(String code) {
		for (KingdeeServiceEnum _enum : values()) {
			if (StringUtils.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
	}
}