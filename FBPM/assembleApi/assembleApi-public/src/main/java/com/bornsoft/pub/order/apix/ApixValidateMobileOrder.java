package com.bornsoft.pub.order.apix;

import com.bornsoft.pub.enums.ApixServiceEnum;
import com.bornsoft.utils.tool.SerializeAlias;

/**
 * @Description: 手机号实名校验
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:29:54
 * @version V1.0
 */
public class ApixValidateMobileOrder extends ApixOrderBase {
	/**
	 */
	private static final long serialVersionUID = -6305141192584920010L;

	private String type;
	@SerializeAlias(alias = "phone")
	private String mobile;
	private String name;
	@SerializeAlias(alias = "cardno")
	private String certNo;

	public ApixValidateMobileOrder() {
		this.type = "mobile";
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	@Override
	public String getUrl() {
		return "http://v.apix.cn/apixcredit/idcheck/mobile";
	}
	
	@Override
	public String getService() {
		return ApixServiceEnum.ValidateMobile.code();
	}
}
