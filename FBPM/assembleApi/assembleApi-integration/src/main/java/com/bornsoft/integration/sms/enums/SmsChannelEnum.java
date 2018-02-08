package com.bornsoft.integration.sms.enums;

import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: 短信渠道 
 * @author taibai@yiji.com
 * @date 2017-10-16 下午5:36:26
 * @version V1.0
 */
public enum SmsChannelEnum implements IBornEnum{

	Huaruan("huaruan","华软"),
	Emay("emay","亿美"),
	UnKnown("unknown","未知"),
	;
	
	private final String code;
	private final String message;
	
	
	private SmsChannelEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return SmsChannelEnum
	 */
	public static SmsChannelEnum getByCode(String code) {
		for (SmsChannelEnum _enum : values()) {
			if (_enum.code().equalsIgnoreCase(code)) {
				return _enum;
			}
		}
		return Huaruan;
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
