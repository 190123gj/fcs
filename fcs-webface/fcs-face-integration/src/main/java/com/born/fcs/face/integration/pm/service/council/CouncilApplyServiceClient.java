package com.born.fcs.face.integration.pm.service.council;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;

/**
 * 
 * 
 * 
 * @author Fei
 * 
 */
@Service("councilApplyServiceClient")
public class CouncilApplyServiceClient extends ClientAutowiredBaseService implements
																			CouncilApplyService {
	
	@Override
	public FcsBaseResult saveCouncilApply(final CouncilApplyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilApplyWebService.saveCouncilApply(order);
			}
		});
	}
	
	@Override
	public List<CouncilApplyInfo> queryCouncilApplyByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<CouncilApplyInfo>>() {
			@Override
			public List<CouncilApplyInfo> call() {
				return councilApplyWebService.queryCouncilApplyByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public CouncilApplyInfo queryCouncilApplyByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<CouncilApplyInfo>() {
			@Override
			public CouncilApplyInfo call() {
				return councilApplyWebService.queryCouncilApplyByApplyId(applyId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilApplyInfo> queryCouncilApply(final CouncilApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilApplyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CouncilApplyInfo> call() {
				return councilApplyWebService.queryCouncilApply(order);
			}
		});
	}
	
	@Override
	public String getAvailableCouncilCodeSeq(final CompanyNameEnum company,
												final CouncilTypeEnum councilType) {
		return callInterface(new CallExternalInterface<String>() {
			@Override
			public String call() {
				return councilApplyWebService.getAvailableCouncilCodeSeq(company, councilType);
			}
		});
	}
	
}
