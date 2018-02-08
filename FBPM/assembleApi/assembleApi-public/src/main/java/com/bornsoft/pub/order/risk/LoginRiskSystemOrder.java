package com.bornsoft.pub.order.risk;

import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/***
  * @Description: 风险监控系统可信任登录 【跳转】
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午4:00:09
  * @version V1.0
 */
public class LoginRiskSystemOrder extends BornOutOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;
	/** 操作员账户名 */
	private String operator;
	/** 登陆后跳转地址 */
	private String toUrl;
	
	public String getToUrl() {
		return toUrl;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(this.operator, "operator");
	}
	
	@Override
	public String getService() {
		return "loginRiskSystem";
	}

	@Override
	public boolean isRedirect() {
		return true;
	}

}
