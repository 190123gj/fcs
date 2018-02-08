/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.shortmessage;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 短信发送Order
 *
 * @author jil
 *
 */
public class ShortMessageOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4459614217876366746L;
	
	private Long id;
	
	private long messageSenderId;
	
	private String messageSenderName;
	
	private String messageSenderAccount;
	
	private String messageReceiver;
	
	private String messageContent;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getMessageSenderId() {
		return messageSenderId;
	}
	
	public void setMessageSenderId(long messageSenderId) {
		this.messageSenderId = messageSenderId;
	}
	
	public String getMessageSenderName() {
		return messageSenderName;
	}
	
	public void setMessageSenderName(String messageSenderName) {
		this.messageSenderName = messageSenderName;
	}
	
	public String getMessageSenderAccount() {
		return messageSenderAccount;
	}
	
	public void setMessageSenderAccount(String messageSenderAccount) {
		this.messageSenderAccount = messageSenderAccount;
	}
	
	public String getMessageReceiver() {
		return messageReceiver;
	}
	
	public void setMessageReceiver(String messageReceiver) {
		this.messageReceiver = messageReceiver;
	}
	
	public String getMessageContent() {
		return messageContent;
	}
	
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
}
