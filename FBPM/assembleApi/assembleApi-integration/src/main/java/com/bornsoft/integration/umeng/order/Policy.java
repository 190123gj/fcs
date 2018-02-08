package com.bornsoft.integration.umeng.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Policy {
	/** 定时发送 */
	String start_time;
	/** 有效期 */
	String expire_time;
	/** 每秒发送的最大条数 */
	String max_send_num;
	/** 消息标识 防止重复发送 */
	String out_biz_no;
	
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
	
	public String getMax_send_num() {
		return this.max_send_num;
	}
	
	public void setMax_send_num(String max_send_num) {
		this.max_send_num = max_send_num;
	}
	
	public String getOut_biz_no() {
		return this.out_biz_no;
	}
	
	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
