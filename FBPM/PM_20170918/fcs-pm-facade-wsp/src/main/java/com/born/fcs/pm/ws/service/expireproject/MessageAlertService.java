package com.born.fcs.pm.ws.service.expireproject;

import com.born.fcs.pm.ws.info.expireproject.MessageAlertInfo;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertOrder;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 保后及等级评定消息提醒
 * 
 * @author lirz
 * 
 * 2016-7-29 上午10:39:57
 * 
 */
public interface MessageAlertService {
	
	/**
	 * 
	 * 添加提醒列表
	 * 
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult add(MessageAlertOrder order);
	
	/**
	 * 
	 * 更新发送了消息
	 * 
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult updateSend(MessageAlertOrder order);
	
	/**
	 * 
	 * 取消提醒
	 * 
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult stopAlert(MessageAlertOrder order);
	
	/**
	 * 按条件查询列表
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<MessageAlertInfo> queryList(MessageAlertQueryOrder queryOrder);
}
