package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;

public class VerifyMessageInfo implements Serializable {
	private static final long serialVersionUID = -3077995975461794615L;
	/** 查询key */
	private String errorKey;
	/** 客户Id */
	private long customerId;
	/** 手机校验消息 */
	private String mobileMessage;
	/** 实名校验消息 */
	private String cardMessage;
	/** 消息类型 */
	private String messageType;
	
	public String getErrorKey() {
		return this.errorKey;
	}
	
	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getMobileMessage() {
		return this.mobileMessage;
	}
	
	public void setMobileMessage(String mobileMessage) {
		this.mobileMessage = mobileMessage;
	}
	
	public String getCardMessage() {
		return this.cardMessage;
	}
	
	public void setCardMessage(String cardMessage) {
		this.cardMessage = cardMessage;
	}
	
	public String getMessageType() {
		return this.messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VerifyMessageInfo [errorKey=");
		builder.append(errorKey);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", mobileMessage=");
		builder.append(mobileMessage);
		builder.append(", cardMessage=");
		builder.append(cardMessage);
		builder.append(", messageType=");
		builder.append(messageType);
		builder.append("]");
		return builder.toString();
	}
	
}
