package com.bornsoft.utils.base;

import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * 外部 接口 order
 */
public class BornOutOrderBase extends BornOrderBase {

	private static final long serialVersionUID = 1L;

	/** 平台ID **/
	private String partnerId;
	/** 签名 */
	private String sign;
	/** 签名方式 */
	private String signType;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.checkBaseParameter(getOrderNo(), getPartnerId());
	}
}
