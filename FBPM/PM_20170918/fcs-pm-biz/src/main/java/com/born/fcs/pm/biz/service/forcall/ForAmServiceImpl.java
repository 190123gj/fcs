package com.born.fcs.pm.biz.service.forcall;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectPledgeAssetDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemePledgeAssetDO;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.forcall.ForAmService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 供am调用
 * 
 * @author lirz
 * 
 * 2016-9-18 上午10:51:32
 * 
 */
@Service("forAmService")
public class ForAmServiceImpl extends BaseFormAutowiredDomainService implements ForAmService {
	
	@Override
	public FcsBaseResult updatePledgeAsset(	final UpdateInvestigationCreditSchemePledgeAssetOrder order) {
		return commonProcess(order, "更新资产评估价格/抵押率", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				//更新尽调(只更新表单状态未 草稿、撤回、驳回的)
				FInvestigationCreditSchemePledgeAssetDO asset = new FInvestigationCreditSchemePledgeAssetDO();
				BeanCopier.staticCopy(order, asset);
				FInvestigationCreditSchemePledgeAssetDAO.updateByAssertsId(asset);
				
				//更新会议纪要(只更新表单状态未 草稿、撤回、驳回的)
				FCouncilSummaryProjectPledgeAssetDO sumamryAsset = new FCouncilSummaryProjectPledgeAssetDO();
				BeanCopier.staticCopy(order, asset);
				FCouncilSummaryProjectPledgeAssetDAO.updateByAssertsId(sumamryAsset);
				return null;
			}
		}, null, null);
	}
	
}
