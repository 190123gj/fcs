package com.bornsoft.integration.umeng.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Payload {
	
	/** 默认为通知 */
	String display_type = "notification";
	/** 消息体 */
	Body body;
	/** ios消息体 */
	Aps aps;
	/**消息类型[IOS]*/
	private String messageType;
	
	public String getDisplay_type() {
		return this.display_type;
	}
	
	public void setDisplay_type(String display_type) {
		this.display_type = display_type;
	}
	
	public Body getBody() {
		return this.body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}
	
	public Aps getAps() {
		return this.aps;
	}
	
	public void setAps(Aps aps) {
		this.aps = aps;
	}
	
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
