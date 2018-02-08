package com.born.fcs.pm.ws.service.brokerbusiness;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.brokerbusiness.BrokerBusinessFormInfo;
import com.born.fcs.pm.ws.info.brokerbusiness.FBrokerBusinessInfo;
import com.born.fcs.pm.ws.order.brokerbusiness.BrokerBusinessQueryOrder;
import com.born.fcs.pm.ws.order.brokerbusiness.FBrokerBusinessOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/***
 * 经纪业务Service
 * @author wuzj
 */
@WebService
public interface BrokerBusinessService {
	
	/**
	 * 保存经纪业务表单
	 * @param order
	 * @return
	 */
	FormBaseResult save(FBrokerBusinessOrder order);
	
	/**
	 * 列表查询
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<BrokerBusinessFormInfo> queryPage(BrokerBusinessQueryOrder order);
	
	/**
	 * 根据表单ID查询
	 * @param formId
	 * @return
	 */
	FBrokerBusinessInfo findByFormId(long formId);
}
