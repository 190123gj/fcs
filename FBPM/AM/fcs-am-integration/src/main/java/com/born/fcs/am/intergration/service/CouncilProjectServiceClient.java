package com.born.fcs.am.intergration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.order.council.CouncilProjectOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;

@Service("councilProjectServiceClient")
public class CouncilProjectServiceClient extends ClientAutowiredBaseService implements
																			CouncilProjectService {
	
	@Override
	public List<CouncilProjectInfo> queryByCouncilId(final long councilId) {
		
		return callInterface(new CallExternalInterface<List<CouncilProjectInfo>>() {
			@Override
			public List<CouncilProjectInfo> call() {
				return councilProjectWebService.queryByCouncilId(councilId);
			}
		});
		
	}
	
	@Override
	public List<CouncilProjectVoteResultInfo> queryProjectVoteResultByCouncilId(final long councilId) {
		
		return callInterface(new CallExternalInterface<List<CouncilProjectVoteResultInfo>>() {
			@Override
			public List<CouncilProjectVoteResultInfo> call() {
				return councilProjectWebService.queryProjectVoteResultByCouncilId(councilId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilProjectVoteResultInfo> queryProjectVoteResult(	final CouncilVoteProjectQueryOrder order) {
		
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilProjectVoteResultInfo>>() {
			@Override
			public QueryBaseBatchResult<CouncilProjectVoteResultInfo> call() {
				return councilProjectWebService.queryProjectVoteResult(order);
			}
		});
	}
	
	@Override
	public CouncilProjectVoteResultInfo query(final long councilId, final String projectCode) {
		
		return callInterface(new CallExternalInterface<CouncilProjectVoteResultInfo>() {
			@Override
			public CouncilProjectVoteResultInfo call() {
				return councilProjectWebService.query(councilId, projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult oneVoteDown(final CouncilProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return councilProjectWebService.oneVoteDown(order);
			}
		});
	}
	
	@Override
	public CouncilProjectInfo getLastInfoByApplyId(final Long applyId) {
		return callInterface(new CallExternalInterface<CouncilProjectInfo>() {
			@Override
			public CouncilProjectInfo call() {
				return councilProjectWebService.getLastInfoByApplyId(applyId);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveCompereAfterMessage(final CouncilProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return councilProjectWebService.saveCompereAfterMessage(order);
			}
		});
	}
}
