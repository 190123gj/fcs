package com.bornsoft.facade.api.umeng;

import javax.jws.WebService;

import com.bornsoft.facade.order.UMengSendOrder;
import com.bornsoft.pub.result.umeng.UmengSendResult;

/**
 * @Description: 友盟消息发送 
 * @author taibai@yiji.com
 * @date 2017-2-6 下午4:22:59
 * @version V1.0
 */
@WebService
public interface UmenMessageSendFacade {
	public UmengSendResult send(UMengSendOrder order);
}
