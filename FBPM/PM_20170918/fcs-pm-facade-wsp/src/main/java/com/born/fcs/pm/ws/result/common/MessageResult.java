package com.born.fcs.pm.ws.result.common;

import com.born.fcs.pm.ws.info.common.MessageReceivedInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public class MessageResult extends FcsBaseResult {
	
	private static final long serialVersionUID = -6573704269130355064L;
	com.born.fcs.pm.ws.info.common.MessageReceivedInfo messageReceivedInfo;
	
	public MessageReceivedInfo getMessageReceivedInfo() {
		return this.messageReceivedInfo;
	}
	
	public void setMessageReceivedInfo(MessageReceivedInfo messageReceivedInfo) {
		this.messageReceivedInfo = messageReceivedInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageResult [messageReceivedInfo=");
		builder.append(messageReceivedInfo);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
