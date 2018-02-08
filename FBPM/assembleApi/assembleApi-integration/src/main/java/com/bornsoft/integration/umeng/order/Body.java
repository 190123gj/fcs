package com.bornsoft.integration.umeng.order;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Body {
	
	/** 提示文字 */
	String ticker;
	/** 标题 */
	String title;
	/** 内容 */
	String text;
	/** 自定义推送方式 */
	String after_open = "go_custom";
	/** 定义推送方式 */
	Map<String, String> custom;
	/** 自定义通知声音 */
	String sound = "diancanmp3.caf";
	public String getTicker() {
		return this.ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
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
	
	public String getAfter_open() {
		return this.after_open;
	}
	
	public void setAfter_open(String after_open) {
		this.after_open = after_open;
	}
	
	public Map<String, String> getCustom() {
		return this.custom;
	}
	
	public void setCustom(Map<String, String> custom) {
		
		this.custom = custom;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
