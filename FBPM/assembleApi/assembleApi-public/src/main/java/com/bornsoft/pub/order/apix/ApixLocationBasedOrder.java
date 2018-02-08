package com.bornsoft.pub.order.apix;

import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.tool.SerializeAlias;


public abstract class ApixLocationBasedOrder extends ApixOrderBase{

	/**
	 */
	private static final long serialVersionUID = -1183093854109432188L;

	@SerializeAlias(alias = "cellid")
	private String cellId;//基站号
	@SerializeAlias(alias = "ishex")
	private String isHex;//cellid是否16进制，ishex=0表示10进制，ishex=1表示16进制。默认为0
	
	/**
	 * 基站号
	 * @return
	 */
	public String getCellId() {
		return cellId;
	}
	/**
	 * 基站号
	 * @param cellId
	 */
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getIsHex() {
		return isHex;
	}


	public void setIsHex(String isHex) {
		this.isHex = isHex;
	}


	@Override
	public String getUrl() {
		return "http://a.apix.cn/apixlife/basestation/telecom";
	}
	
	/**
	 * @Description: 电信基站定位 
	 * @author taibai@yiji.com
	 * @date 2016-8-27 上午11:06:59
	 * @version V1.0
	 */
	public static class ApixLocationBasedTelecomOrder extends ApixLocationBasedOrder{
		/**
		 */
		private static final long serialVersionUID = -1823000092650222611L;
		private String sid;	//SID系统识别码（各地区一个）
		private String nid;	//NID网络识别码（各地区1-3个）
		public String getSid() {
			return sid;
		}
		
		public void setSid(String sid) {
			this.sid = sid;
		}
		
		public String getNid() {
			return nid;
		}
		
		public void setNid(String nid) {
			this.nid = nid;
		}
		
		@Override
		public String getUrl() {
			return super.getUrl();
		}
		
	}

	/**
	 * @Description: 移动联通基站定位 
	 * @author taibai@yiji.com
	 * @date 2016-8-27 上午11:06:59
	 * @version V1.0
	 */
	public static class ApixLocationBasedMobUnicOrder extends ApixLocationBasedOrder{
		
		/**
		 */
		private static final long serialVersionUID = 3725698074897702498L;
		@SerializeAlias(alias = "mnc")
		private OperatorEnum operator;
		private String lac;//小区代码

		public OperatorEnum getOperator() {
			return operator;
		}
		public void setOperator(OperatorEnum operator) {
			this.operator = operator;
		}
		/**
		 * 小区代码
		 * @return
		 */
		public String getLac() {
			return lac;
		}
		/**
		 * 小区代码
		 * @param lac
		 */
		public void setLac(String lac) {
			this.lac = lac;
		}
		
		@Override
		public String getUrl() {
			return "http://a.apix.cn/apixlife/basestation/mob_unic";
		}
	}
	
	public  static enum OperatorEnum implements IBornEnum{

		Mobile("0", "移动"), 
		Unicom("1", "联通"),
		;
		private final String code;
		private final String message;

		private OperatorEnum(String code, String message) {
			this.code = code;
			this.message = message;
		}

		@Override
		public String code() {
			return code;
		}

		@Override
		public String message() {
			return message;
		}
	}
}
