package com.bornsoft.facade.api.sms;

import javax.jws.WebService;

import com.bornsoft.facade.order.SmsSendOrder;
import com.bornsoft.facade.result.SmsSendResult;

/**
 * @Description: 手机短信
 * @author taibai@yiji.com
 * @date 2016-12-1 上午10:35:17
 * @version V1.0
 */
@WebService
public interface SmsFacade {
	
	/**
	 * 发送短信
	 * @param order
	 * @return
	 */
	public SmsSendResult sendSms(SmsSendOrder order);
	
}
