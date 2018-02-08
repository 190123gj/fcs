package com.bornsoft.utils.constants;

import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: 系统参数枚举（仅APP） 
 * @author taibai@yiji.com
 * @date 2016-9-1 下午4:23:29
 * @version V1.0
 */
public enum ApiSystemParamEnum implements IBornEnum{
	
	
	Api_WebService_Identity_Switch("API_WEBSERVICE_IDENTITY_SWITCH","API webservice 服务鉴权开关"),
	Api_WebService_Wihte("API_WEBSERVICE_WIHTE","API webservice 访问IP白名单"),
	/********************APP加密密钥******************/
	App_Secrity_Key("APP_SECRITY_KEY","APP加密KEY"),
	
	/********************APIX******************/
	Apix_Very_Key("APIX_VERY_KEY","APIX[征信、个人信息校验] 密钥"),
	Apix_Dishonest_Key("APIX_DISHONEST_KEY","APIX[失信数据查询] 密钥"),
	Apix_Api_Switch("APIX_API_SWITCH","APIX接口开关"),
	
	/********************风险监控系统******************/
	Risk_Partner_Id("RISK_PARTNER_ID","风险监控系统商户ID"),
	Risk_Partner_Key("RISK_PARTNER_KEY","风险监控系统密钥"),
	Risk_System_Switch("RISK_SYSTEM_SWITCH","风险监控系统接口开关"),
	
	Risk_Api_System_Url("RISK_API_SYSTEM_URL","风险监控系统API访问地址"),
	Risk_Api_Partner_Id("RISK_API_PARTNER_ID","风险监控系统API商户ID"),
	Risk_Api_Partner_Key("RISK_API_PARTNER_KEY","风险监控系统API密钥"),

	/********************博恩软件内部专用******************/
	Born_Partner_Id("BORN_PARTNER_ID","博恩内部接口商户ID"),
	Born_Partner_Key("BORN_PARTNER_KEY","博恩内部接口密钥"),
	
	/********************金蝶******************/
	Kingdee_Partner_Id("KINGDEE_PARTNER_ID","金蝶商户ID"),
	Kingdee_Partner_Key("KINGDEE_PARTNER_KEY","金蝶密钥"),
	
	Kingdee_Api_System_Url("KINGDEE_API_SYSTEM_URL","金蝶系统API访问地址"),
	Kingdee_Api_Partner_Id("KINGDEE_API_PARTNER_ID","金蝶系统API商户ID"),
	Kingdee_Api_Partner_Key("KINGDEE_API_PARTNER_KEY","金蝶系统API密钥"),
	
	FACE_URL("FACE_URL","PC端地址"),
	OWN_API_URL("RISK_HTTP_URL","自身地址"),
	
	//=====短信==========//
	Sms_Switch("SMS_SWITCH","短信服务开关"),
	Sms_Channel("SMS_CHANNEL","短信服务渠道"),
	Sms_Sign("SMS_SIGN","短信签名"),
	
	/********************华软******************/
	Huaruan_Sms_System_Url("HUARUAN_SMS_SYSTEM_URL","华软短信服务地址"),
	Huaruan_Sms_Username("HUARUAN_SMS_USERNAME","华软短信服务用户名"),
	Huaruan_Sms_Password("HUARUAN_SMS_PASSWORD","华软短信服务密码"),
	Huaruan_Sms_Gwid("HUARUAN_SMS_GWID","华软短信服务网关ID"),
	Huaruan_Sms_Sign("HUARUAN_SMS_SIGN","华软短信服务签名"),
	Huaruan_Sms_Switch("HUARUAN_SMS_SWITCH","华软短信服务开关"),
	
	/**********************EUCP****************/
	Emay_Sms_System_Url("EMAY_SMS_SYSTEM_URL","亿美短信服务地址"),
	Emay_Sms_AppId("EMAY_SMS_APPID","亿美短信服务APPID"),
	Emay_Sms_Secret("EMAY_SMS_SECRET","亿美短信服务SECRET"),
	
	
	UMeng_Android_Key("UMENG_ANDROID_KEY","安卓友盟KEY"),
	UMeng_Ios_Key("UMENG_IOS_KEY","IOS友盟KEY"),
	UMeng_Switch("UMENG_SWITCH","友盟消息发送开关"),

	PC_AUDIT_URLS("PC_AUDIT_URLS","跳转PC审核的url"),
	
	
	;
	private String code;
	private String message;

	private ApiSystemParamEnum(String code, String message) {
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


