package com.bornsoft.api.service;

import org.apache.commons.lang3.StringUtils;

/**
  * @Description: 服务枚举类 
  * @author taibai@yiji.com 
  * @date  2016-8-13 下午3:29:56
  * @version V1.0
 */
public enum BornApiServiceEnum {
	
	/**============================================风险监控系统=======================================**/
	/**风险信息接收***/
	RISK_INFO_NOTIFY("riskInfoNotify", "风险信息通知接收"),
	/**当日分时电量查询***/
	LOGIN_RISK_SYSTEM("loginRiskSystem", "登录风险监控系统",true),	
	/**当日分时电量查询***/
	QUERY_RISK_INFO("queryRiskInfo", "跳转查询企业风险信息",true),
	
	/**============================================电量查询=======================================**/
	/**当日分时电量查询***/
	ELECTRIC_QUERY("queryElectric", "电量信息查询"),
	/**当日分时电量查询***/
	ELECTRIC_EXPORT_INFO("exportElectricInfo", "导出用电量报表"),
	
	/**============================================金蝶=======================================**/
	KINGDEE_VOUCHERNO_RECEVIE_SERVICE("voucherNoRecevieService", "权利凭证号接收"),
	;
	
	/** 服务代码 **/
	private String code;
	/** 服务描述 **/
	private String desc;
	/** 是否需要保存请求日志纪录 **/
	private boolean saveLog;
	/**	是否需要添加前缀**/
	private boolean addPerfix;
	/** 是否页页跳转 **/
	private boolean redirect = false;
	
	BornApiServiceEnum(String code, String desc, boolean redirect) {
		this.code = code;
		this.desc = desc;
		this.redirect = redirect;
	}
	
	
	BornApiServiceEnum(String code, String desc) {
		this(code, desc, false);
	}

	public final String getCode() {
		return code;
	}

	public final String getDesc() {
		return desc;
	}

	public final boolean isSaveLog() {
		return saveLog;
	}
	
	public boolean isAddPerfix() {
		return addPerfix;
	}
	
	public boolean isRedirect() {
		return redirect;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return BornApiServiceEnum
	 */
	public static BornApiServiceEnum getByCode(String code) {
		for (BornApiServiceEnum _enum : values()) {
			if (StringUtils.equals(_enum.getCode(), code)) {
				return _enum;
			}
		}
		return null;
	}
}