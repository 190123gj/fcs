package com.bornsoft.facade.order;


import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 短信发送请求order 
 * @author taibai@yiji.com
 * @date 2016-12-1 上午10:41:25
 * @version V1.0
 */
public class SmsSendOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 手机号 **/
	private String mobile;
	/** 短信内容[用于普通短信发送] **/
	private String content;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		//手机号码校验
		ValidateParamUtil.isMobile(mobile);
		//短信内容
		ValidateParamUtil.hasText(content, "短信内容[content]不能为空!");
	}
}
