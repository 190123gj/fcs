package com.bornsoft.integration.umeng.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * IOS消息体
 * */
public class Aps {
	/** 内容 */
	String alert;
	String badge = "1";
	/** 自定义通知声音 */
	String sound = "diancanmp3.caf";

	String contentAvailable = "1";
	public String getAlert() {
		return this.alert;
	}
	
	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	public String getBadge() {
		return this.badge;
	}
	
	public void setBadge(String badge) {
		this.badge = badge;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getContentAvailable() {
		return contentAvailable;
	}

	public void setContentAvailable(String contentAvailable) {
		this.contentAvailable = contentAvailable;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
