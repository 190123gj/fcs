package com.born.fcs.crm.integration.risk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.crm.integration.bpm.service.ClientAutowiredBaseService;
import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.pub.order.risk.QuerySimilarEnterpriseOrder;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder;
import com.bornsoft.pub.order.risk.SynWatchListOrder;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder;
import com.bornsoft.pub.order.risk.VerifyOrganizationOrder;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult;
import com.bornsoft.pub.result.risk.VerifyOrganizationResult;
import com.bornsoft.utils.base.BornSynResultBase;

/**
 * 风险系统接口
 * **/
@Service("riskSystemFacadeClient")
public class RiskSystemFacadeClient extends ClientAutowiredBaseService implements RiskSystemFacade {
	@Autowired
	RiskSystemFacade riskSystemFacade;
	
	@Override
	public QuerySimilarEnterpriseResult querySimilarEnterprise(	final QuerySimilarEnterpriseOrder arg0) {
		return callInterface(new CallExternalInterface<QuerySimilarEnterpriseResult>() {
			
			@Override
			public QuerySimilarEnterpriseResult call() {
				return riskSystemFacade.querySimilarEnterprise(arg0);
			}
		});
	}
	
	@Override
	public BornSynResultBase synCustomInfo(final SynsCustomInfoOrder arg0) {
		return callInterface(new CallExternalInterface<BornSynResultBase>() {
			
			@Override
			public BornSynResultBase call() {
				return riskSystemFacade.synCustomInfo(arg0);
			}
		});
	}
	
	/**
	 * 同步评级等级
	 * */
	@Override
	public BornSynResultBase synCustomLevel(final SynCustomLevelOrder arg0) {
		return callInterface(new CallExternalInterface<BornSynResultBase>() {
			
			@Override
			public BornSynResultBase call() {
				return riskSystemFacade.synCustomLevel(arg0);
			}
		});
	}
	
	@Override
	public BornSynResultBase synOperatorInfo(final SynOperatorInfoOrder arg0) {
		return callInterface(new CallExternalInterface<BornSynResultBase>() {
			
			@Override
			public BornSynResultBase call() {
				
				return riskSystemFacade.synOperatorInfo(arg0);
			}
		});
	}
	
	@Override
	public BornSynResultBase synRiskInfo(final SynRiskInfoOrder arg0) {
		return callInterface(new CallExternalInterface<BornSynResultBase>() {
			
			@Override
			public BornSynResultBase call() {
				return riskSystemFacade.synRiskInfo(arg0);
			}
		});
	}
	
	@Override
	public BornSynResultBase synWatchList(final SynWatchListOrder arg0) {
		return callInterface(new CallExternalInterface<BornSynResultBase>() {
			
			@Override
			public BornSynResultBase call() {
				return riskSystemFacade.synWatchList(arg0);
			}
		});
	}
	
	@Override
	public VerifyOrganizationResult verifyOrganizationInfo(final VerifyOrganizationOrder arg0) {
		return callInterface(new CallExternalInterface<VerifyOrganizationResult>() {
			
			@Override
			public VerifyOrganizationResult call() {
				return riskSystemFacade.verifyOrganizationInfo(arg0);
			}
		});
	}
	
}
