package com.born.fcs.face.integration.pm.service.council;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteInfo;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilProjectVoteService;

@Service("councilProjectVoteServiceClient")
public class CouncilProjectVoteServiceClient extends ClientAutowiredBaseService implements
																				CouncilProjectVoteService {
	
	@Override
	public FcsBaseResult saveCouncilProjectVote(final CouncilProjectVoteOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return councilProjectVoteWebService.saveCouncilProjectVote(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult destroyCouncilProjectVote(final CouncilProjectVoteOrder order) {
		
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return councilProjectVoteWebService.destroyCouncilProjectVote(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilProjectVoteInfo> queryCouncilProjectVote(final CouncilProjectVoteQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilProjectVoteInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CouncilProjectVoteInfo> call() {
				return councilProjectVoteWebService.queryCouncilProjectVote(order);
			}
		});
	}
	
	@Override
	public List<CouncilProjectVoteInfo> queryCouncilProjectVoteByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<CouncilProjectVoteInfo>>() {
			
			@Override
			public List<CouncilProjectVoteInfo> call() {
				return councilProjectVoteWebService
					.queryCouncilProjectVoteByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateCouncilProjectVote(final CouncilProjectVoteOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return councilProjectVoteWebService.updateCouncilProjectVote(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult urgeVote(final CouncilProjectVoteOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return councilProjectVoteWebService.urgeVote(order);
			}
		});
	}
	
}
