package com.bornsoft.pub.order.umeng;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * 请求order
 */
public class UMengOrder extends BornOrderBase {

	private static final long serialVersionUID = 7153121480795150861L;
	/** 标题 */
	String title;
	/** 通知文字描述：内容 */
	String text;
	/** 通知栏提示文字 */
	String ticker = "广播";
	/** 定时发送 */
	String start_time;
	/** 有效期 */
	String expire_time;
	/** 推送用户名 */
	String userName;
	/** 是否播放语音 */
	boolean playVoice = true;
	/** 设备唯一表示 */
	String device_tokens;
	/** message消息 notification通知 */
	String displayType = "notification";
	/** 通知消息类型*/
	private MessageTypeEnum messageType;

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasText(title, "标题");
		ValidateParamUtil.hasText(userName, "用户名");
		ValidateParamUtil.hasText(text, "通知文字描述");
		ValidateParamUtil.hasText(ticker, "通知栏提示文字 ");
	}

	public String getDevice_tokens() {
		return device_tokens;
	}

	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStart_time() {
		return this.start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getExpire_time() {
		return this.expire_time;
	}

	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}

	public String getTicker() {
		return this.ticker;
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

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public boolean isPlayVoice() {
		return playVoice;
	}

	public void setPlayVoice(boolean playVoice) {
		this.playVoice = playVoice;
	}
	
	
	public MessageTypeEnum getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
	}


	public static enum MessageTypeEnum implements IBornEnum{

		Message("message", "消息"),
		Wait("wait", "待办任务"),
		;
		
		/** 枚举值 */
		private final String code;

		/** 枚举描述 */
		private final String message;

		private MessageTypeEnum(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String code() {
			return code;
		}

		public String message() {
			return message;
		}
	}

}
