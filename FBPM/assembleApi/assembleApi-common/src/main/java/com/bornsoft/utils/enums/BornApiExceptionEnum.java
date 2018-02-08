package com.bornsoft.utils.enums;

/**
 * @Description: 异常枚举
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午1:58:57 
 * @version V1.0
 */
public enum BornApiExceptionEnum {
	
	SERVICE_CALL_FAILED("服务调用失败"),
	NON_EXISTS_SERVICE_PROVIDER("服务提供者不存在"),
	NON_EXISTS_SERVICE("服务不存在"),
	ILLEGAL_PARAMETER("参数不合法"),
	SERVICE_NOT_ACCESS("服务不能访问"),
	ILLEGAL_SIGN("签名校验失败"),
	EXECUTE_FAILURE("执行失败"),
	;

	private BornApiExceptionEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	private String desc = null;

	public static String getCodeDesc(String resultCode) {
		try {
			BornApiExceptionEnum x = BornApiExceptionEnum.valueOf(resultCode);
			return x.getDesc();
		} catch (Throwable ignore) {
			return "异常";
		}
	}
}
