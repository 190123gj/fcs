package com.born.fcs.face.integration.pm.service.basicmaintain;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionInstitutionService;

/**
 * 
 * @author jil
 *
 */
@Service("decisionInstitutionServiceClient")
public class DecisionInstitutionServiceClient extends ClientAutowiredBaseService implements
																				DecisionInstitutionService {
	
	@Override
	public DecisionInstitutionInfo findById(final long institutionId) {
		return callInterface(new CallExternalInterface<DecisionInstitutionInfo>() {
			
			@Override
			public DecisionInstitutionInfo call() {
				return decisionInstitutionService.findById(institutionId);
			}
		});
	}
	
	@Override
	public DecisionInstitutionInfo findDecisionInstitutionByInstitutionName(final String institutionName) {
		return callInterface(new CallExternalInterface<DecisionInstitutionInfo>() {
			
			@Override
			public DecisionInstitutionInfo call() {
				return decisionInstitutionService
					.findDecisionInstitutionByInstitutionName(institutionName);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final DecisionInstitutionOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return decisionInstitutionService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<DecisionInstitutionInfo> query(	final DecisionInstitutionQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<DecisionInstitutionInfo>>() {
			
			@Override
			public QueryBaseBatchResult<DecisionInstitutionInfo> call() {
				return decisionInstitutionService.query(order);
			}
		});
	}
	
	@Override
	public int deleteById(final long institutionId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return decisionInstitutionService.deleteById(institutionId);
			}
		});
	}
	
}
