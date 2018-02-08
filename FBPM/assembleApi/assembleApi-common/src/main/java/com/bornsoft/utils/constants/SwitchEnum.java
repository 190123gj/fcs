package com.bornsoft.utils.constants;

import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: 开关模拟
 * @author taibai@yiji.com
 * @date 2016-12-8 下午5:48:42
 * @version V1.0
 */
public enum SwitchEnum implements IBornEnum {
	On("on", "打开"), Off("off", "关闭"), ;
	private String code;
	private String message;

	private SwitchEnum(String code, String message) {
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
