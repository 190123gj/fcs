package com.born.fcs.face.integration.pm.service.basicmaintain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionMemberOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionMemberQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionMemberService;

/**
 * 
 * @author jil
 *
 */
@Service("decisionMemberServiceClient")
public class DecisionMemberServiceClient extends ClientAutowiredBaseService implements
																			DecisionMemberService {
	
	@Override
	public DecisionMemberInfo findById(final long memberId) {
		return callInterface(new CallExternalInterface<DecisionMemberInfo>() {
			
			@Override
			public DecisionMemberInfo call() {
				return decisionMemberService.findById(memberId);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final DecisionMemberOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return decisionMemberService.save(order);
			}
		});
	}
	
	@Override
	public DecisionMemberInfo findDecisionMemberByUserName(final String userName) {
		return callInterface(new CallExternalInterface<DecisionMemberInfo>() {
			
			@Override
			public DecisionMemberInfo call() {
				return decisionMemberService.findDecisionMemberByUserName(userName);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<DecisionMemberInfo> query(final DecisionMemberQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<DecisionMemberInfo>>() {
			
			@Override
			public QueryBaseBatchResult<DecisionMemberInfo> call() {
				return decisionMemberService.query(order);
			}
		});
	}
	
	@Override
	public List<DecisionMemberInfo> queryDecisionMemberInfo(final Long institutionId) {
		return callInterface(new CallExternalInterface<List<DecisionMemberInfo>>() {
			
			@Override
			public List<DecisionMemberInfo> call() {
				return decisionMemberService.queryDecisionMemberInfo(institutionId);
			}
		});
	}
	
}
