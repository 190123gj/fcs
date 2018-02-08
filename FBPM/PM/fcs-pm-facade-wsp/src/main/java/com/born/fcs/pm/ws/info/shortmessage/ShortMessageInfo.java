package com.born.fcs.pm.ws.info.shortmessage;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 短信发送
 *
 *
 * @author jil
 *
 */
public class ShortMessageInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3282645973655995777L;
	
	private long id;
	
	private long messageSenderId;
	
	private String messageSenderName;
	
	private String messageSenderAccount;
	
	private String messageReceiver;
	
	private String messageContent;
	
	private Date rawAddTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
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
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
