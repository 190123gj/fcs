package com.born.fcs.pm.ws.service.shortmessage;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.shortmessage.ShortMessageInfo;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageOrder;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 短信发送
 *
 * @author jil
 */
@WebService
public interface ShortMessageService {
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	ShortMessageInfo findById(long id);
	
	/**
	 * 保存短信信息
	 * @param order
	 * @return
	 */
	FcsBaseResult save(ShortMessageOrder order);
	
	/**
	 * 查询决策机构名称
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ShortMessageInfo> query(ShortMessageQueryOrder order);
	
}
