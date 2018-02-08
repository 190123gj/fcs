package com.born.fcs.crm.biz.message;

public interface RemindMessageService {
	
	/** 给授信时间即将到期的渠道发送提醒消息 */
	void sendMessage();
}
