/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产标示信息(用来确定资产信息)
 * 
 * @author Ji
 *
 */
public enum AssetRemarkInfoEnum {
	//不动产
	NO_MOVABLE_LAND_CARDID("NO_MOVABLE_LAND_CARDID", "不动产-土地/居住类房产", "证号"),
	NO_MOVABLE_LAND_AREA("NO_MOVABLE_LAND_AREA", "不动产-土地/居住类房产", "面积"),
	NO_MOVABLE_LAND_AREA1("NO_MOVABLE_LAND_AREA1", "不动产-土地/居住类房产", "建筑面积"),
	
	//	NO_MOVABLE_LAND_SIT("NO_MOVABLE_LAND_SIT", "不动产-土地/居住类房产", "坐落"),
	//	NO_MOVABLE_LAND_REGIST("NO_MOVABLE_LAND_REGIST", "不动产-土地/居住类房产", "登记机关"),
	//	NO_MOVABLE_LAND_REGIST_ADDRESS("NO_MOVABLE_LAND_REGIST_ADDRESS", "不动产-土地/居住类房产", "登记机关地址"),
	
	//	NO_MOVABLE_LIVE_HOUSE("NO_MOVABLE_LIVE_HOUSE", "不动产-居住类房产", "证号"),
	
	//	NO_MOVABLE_FOREST_LAND_CONDITION("NO_MOVABLE_FOREST_LAND_CONDITION", "不动产-林权", "林地情况"),
	//	NO_MOVABLE_FOREST_FOREST("NO_MOVABLE_FOREST_FOREST", "不动产-林权", "林种"),
	//	NO_MOVABLE_FOREST_TREE("NO_MOVABLE_FOREST_TREE", "不动产-林权", "树种"),
	//	NO_MOVABLE_FOREST_AREA("NO_MOVABLE_FOREST_AREA", "不动产-林权", "种植面积"),
	//动产......
	MOVABLE_STOCK_NAME("MOVABLE_STOCK_NAME", "动产-存货", "存货名称"),
	//	MOVABLE_STOCK_REGULATOR("MOVABLE_STOCK_REGULATOR", "动产-存货", "监管机构"),
	//	MOVABLE_STOCK_TOTAL("MOVABLE_STOCK_TOTAL", "动产-存货", "存货总价"),
	//	MOVABLE_STOCK_UNIT_PRICE("MOVABLE_STOCK_UNIT_PRICE", "动产-存货", "存货单价"),
	//	MOVABLE_STOCK_NUMBER("MOVABLE_STOCK_NUMBER", "动产-存货", "存货数量"),
	
	MOVABLE_ACCOUNTS_CODE("MOVABLE_ACCOUNTS_CODE", "动产-应收账款", "信用代码"),
	MOVABLE_ACCOUNTS_AMOUNT("MOVABLE_ACCOUNTS_AMOUNT", "动产-应收账款", "质押金额"),
	//	MOVABLE_ACCOUNTS_NATURE("MOVABLE_ACCOUNTS_NATURE", "动产-应收账款", "付款机构性质"),
	//	MOVABLE_ACCOUNTS_TOTAL_AMOUNT("MOVABLE_ACCOUNTS_TOTAL_AMOUNT", "动产-应收账款", "应收账款总金额"),
	//	MOVABLE_ACCOUNTS_PERIOD("MOVABLE_ACCOUNTS_PERIOD", "动产-应收账款", "主债权存续期间"),
	//	MOVABLE_ACCOUNTS_DESIGNATED_ACCOUNT("MOVABLE_ACCOUNTS_DESIGNATED_ACCOUNT", "动产-应收账款", "指定收款账户"),
	//	MOVABLE_ACCOUNTS_WARRANTY_PERIOD("MOVABLE_ACCOUNTS_WARRANTY_PERIOD", "动产-应收账款", "质保期"),
	//	MOVABLE_ACCOUNTS_GUARANTEE_MONEY("MOVABLE_ACCOUNTS_GUARANTEE_MONEY", "动产-应收账款", "质保金"),
	//	MOVABLE_ACCOUNTS_PAY_TIME("MOVABLE_ACCOUNTS_PAY_TIME", "动产-应收账款", "付款时间"),
	
	MOVABLE_MACHINERY_EQUIPMENT_NAME("MOVABLE_MACHINERY_EQUIPMENT_NAME", "动产-机器设备", "设备名称"),
	MOVABLE_MACHINERY_EQUIPMENT_TYPE("MOVABLE_MACHINERY_EQUIPMENT_TYPE", "动产-机器设备", "设备种类"),
	//	MOVABLE_MACHINERY_EQUIPMENT_TECHNICAL_PARAMETER(
	//													"MOVABLE_MACHINERY_EQUIPMENT_TECHNICAL_PARAMETER",
	//													"动产-机器设备", "设备主要技术参数"),
	//	MOVABLE_MACHINERY_EQUIPMENT_SEATED("MOVABLE_MACHINERY_EQUIPMENT_SEATED", "动产-机器设备", "设备坐落"),
	MOVABLE_MACHINERY_EQUIPMENT_NO("MOVABLE_MACHINERY_EQUIPMENT_NO", "动产-机器设备", "设备编号"),
	//	MOVABLE_MACHINERY_EQUIPMENT_MANUFACTURER("MOVABLE_MACHINERY_EQUIPMENT_MANUFACTURER", "动产-机器设备",
	//												"生产厂家"),
	//	MOVABLE_MACHINERY_EQUIPMENT_PURCHASE_DATE("MOVABLE_MACHINERY_EQUIPMENT_PURCHASE_DATE",
	//												"动产-机器设备", "购置日期"),
	//	MOVABLE_MACHINERY_EQUIPMENT_PURCHASE_PRICE("MOVABLE_MACHINERY_EQUIPMENT_PURCHASE_PRICE",
	//												"动产-机器设备", "购置价格"),
	
	MOVABLE_SHIP_NUMBER("MOVABLE_SHIP_NAME", "动产-船舶", "船舶登记证号"),
	MOVABLE_SHIP_NAME("MOVABLE_SHIP_NAME", "动产-船舶", "船名"),
	//	MOVABLE_SHIP_PORT_REGISTRY("MOVABLE_SHIP_PORT_REGISTRY", "动产-船舶", "船籍港"),
	//	MOVABLE_SHIP_PRICE("MOVABLE_SHIP_PRICE", "动产-船舶", "建造或购置价格"),
	//	MOVABLE_SHIP_PLANT("MOVABLE_SHIP_PLANT", "动产-船舶", "造船厂"),
	
	MOVABLE_CAR_NUMBER("MOVABLE_CAR_NAME", "动产-车辆", "机动车登记证号"),
	MOVABLE_CAR_PLATE_NUMBER("MOVABLE_CAR_PLATE_NUMBER", "动产-车辆", "车牌号");
	//	MOVABLE_CAR_MODEL("MOVABLE_CAR_MODEL", "动产-车辆", "汽车型号"),
	//	MOVABLE_CAR_FRAME_NUMBER("MOVABLE_CAR_FRAME_NUMBER", "动产-车辆", "车架号"),
	//	MOVABLE_CAR_CERTIFAVCATE_NUMBER("MOVABLE_CAR_CERTIFAVCATE_NUMBER", "动产-车辆", "合格证号"),
	//	MOVABLE_CAR_DRIVING_NUMBER("MOVABLE_CAR_DRIVING_NUMBER", "动产-车辆", "行驶证号");
	
	//	CREDIT_CODE("CREDIT_CODE", "", "信用代码"),
	//	STOCK_NAME("STOCK_NAME", "", "存货名称"),
	//	EQUIPMENT_NAME("EQUIPMENT_NAME", "", "设备名称"),
	//	SHIP_REGISTRATION_NO("SHIP_REGISTRATION_NO", "", "船舶登记证号"),
	//	VEHICLE_REGISTRATION_NO("VEHICLE_REGISTRATION_NO", "", "机动车登记证号");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 -明细 */
	private final String message;
	/** 资产类型 */
	private final String type;
	
	/**
	 * 构造一个<code>BooleanEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AssetRemarkInfoEnum(String code, String type, String message) {
		this.code = code;
		this.message = message;
		this.type = type;
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
	
	public String getType() {
		return type;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return BooleanEnum
	 */
	public static AssetRemarkInfoEnum getByCode(String code) {
		for (AssetRemarkInfoEnum _enum : values()) {
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
	public static List<AssetRemarkInfoEnum> getAllEnum() {
		List<AssetRemarkInfoEnum> list = new ArrayList<AssetRemarkInfoEnum>();
		for (AssetRemarkInfoEnum _enum : values()) {
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
		for (AssetRemarkInfoEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
