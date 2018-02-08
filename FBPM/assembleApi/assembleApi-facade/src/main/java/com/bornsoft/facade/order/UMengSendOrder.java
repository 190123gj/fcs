package com.bornsoft.facade.order;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * 请求order
 */
public class UMengSendOrder extends BornOrderBase {

	private static final long serialVersionUID = 7153121480795150861L;
	 
	/** 标题 */
	String title;
	/** 通知文字描述：内容 */
	String text;
	/** 通知栏提示文字 */
	String ticker = "广播";
	/** 推送用户名 */
	String userName;

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasText(title, "标题");
		ValidateParamUtil.hasText(text, "通知文字描述");
		ValidateParamUtil.hasText(ticker, "通知栏提示文字 ");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
