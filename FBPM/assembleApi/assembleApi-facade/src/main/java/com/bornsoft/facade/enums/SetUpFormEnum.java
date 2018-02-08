package com.bornsoft.facade.enums;

import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.tool.GsonUtil;

/**
 * @Description: 立项申请表单类型
 * @author taibai@yiji.com
 * @date 2016-9-8 上午10:42:43
 * @version V1.0
 */
public enum SetUpFormEnum implements IBornEnum {

	SET_UP_GUARANTEE_ENTRUSTED("SET_UP_GUARANTEE_ENTRUSTED", "担保/委贷类项目立项"),
	SET_UP_UNDERWRITING("SET_UP_UNDERWRITING", "承销类项目立项"), 
	SET_UP_ENTERPRISE_LITIGATION("SET_UP_ENTERPRISE_LITIGATION", "诉讼保函业务立项-企业"),
	/**目前个人只有以下这一种类型**/
	SET_UP_PERSIONAL_LITIGATION("SET_UP_PERSIONAL_LITIGATION", "诉讼保函业务立项-个人"), ;
	
	private String code;
	private String message;

	private SetUpFormEnum(String code, String message) {
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
	
	public static void main(String[] args) {
		System.out.println(GsonUtil.toJson(SetUpFormEnum.SET_UP_ENTERPRISE_LITIGATION));;
	}

}
