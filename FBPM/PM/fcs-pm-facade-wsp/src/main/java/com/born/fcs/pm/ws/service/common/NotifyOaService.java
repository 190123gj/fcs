package com.born.fcs.pm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.FormInfo;

@WebService
public interface NotifyOaService {
	
	/**
	 * 产生代办任务通知
	 * @param formInfo
	 * @param title
	 * @param message
	 * @return
	 */
	void notifyNext(FormInfo formInfo, String title, String message);
	
	/**
	 * 任务交办通知
	 * @param taskId
	 * @param assignId 被交办人
	 * @return
	 */
	void notifyAfterAssign(FormInfo formInfo, long taskId, long assignId, String title,
							String message);
	
	/**
	 * 任务处理通知
	 * @param taskId
	 * @param userId
	 * @return
	 */
	void notifyAfterAudit(long taskId, long userId);
}
