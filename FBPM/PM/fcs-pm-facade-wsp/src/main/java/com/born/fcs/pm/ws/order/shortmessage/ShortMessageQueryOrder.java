/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.shortmessage;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 短信发送Order
 *
 * @author jil
 *
 */
public class ShortMessageQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -7393118631671928403L;
	
	private String beginMessageSendDate;
	
	private String endMessageSendDate;
	
	private String messageSenderName;//发送人
	
	private String messageReceiver;//接收人电话号码
	
	public String getBeginMessageSendDate() {
		return beginMessageSendDate;
	}
	
	public void setBeginMessageSendDate(String beginMessageSendDate) {
		this.beginMessageSendDate = beginMessageSendDate;
	}
	
	public String getEndMessageSendDate() {
		return endMessageSendDate;
	}
	
	public void setEndMessageSendDate(String endMessageSendDate) {
		this.endMessageSendDate = endMessageSendDate;
	}
	
	public String getMessageSenderName() {
		return messageSenderName;
	}
	
	public void setMessageSenderName(String messageSenderName) {
		this.messageSenderName = messageSenderName;
	}
	
	public String getMessageReceiver() {
		return messageReceiver;
	}
	
	public void setMessageReceiver(String messageReceiver) {
		this.messageReceiver = messageReceiver;
	}
	
}
