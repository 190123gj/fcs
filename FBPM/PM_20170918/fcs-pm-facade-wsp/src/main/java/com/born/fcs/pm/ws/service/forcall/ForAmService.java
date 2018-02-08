package com.born.fcs.pm.ws.service.forcall;

import javax.jws.WebService;

import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 供am项目调用pm方法
 *
 * @author lirz
 * 
 * 2016-9-18 上午10:36:20
 *
 */
@WebService
public interface ForAmService {
	
	/**
	 * 更新尽调资产抵押价格抵押率
	 * @param order
	 * @return
	 */
	FcsBaseResult updatePledgeAsset(UpdateInvestigationCreditSchemePledgeAssetOrder order);
}
