package com.born.fcs.face.integration.pm.service.council;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.council.CouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuarantorInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.order.council.CouncilSummaryRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummarySubmitOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummaryUploadOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectBondOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectEntrustedOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectGuaranteeOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectLgLitigationOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectUnderwritingOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryRiskHandleOrder;
import com.born.fcs.pm.ws.order.council.OneVoteDownOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.council.CouncilSummaryResult;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;

/**
 * 会议纪要
 * 
 * @author wuzj
 */
@Service("councilSummaryServiceClient")
public class CouncilSummaryServiceClient extends ClientAutowiredBaseService implements
																			CouncilSummaryService {
	
	@Override
	public CouncilSummaryResult initCouncilSummary(final FCouncilSummaryOrder order) {
		return callInterface(new CallExternalInterface<CouncilSummaryResult>() {
			
			@Override
			public CouncilSummaryResult call() {
				return councilSummaryWebService.initCouncilSummary(order);
			}
		});
	}
	
	@Override
	public CouncilSummaryResult saveCouncilSummary(final FCouncilSummaryOrder order) {
		return callInterface(new CallExternalInterface<CouncilSummaryResult>() {
			
			@Override
			public CouncilSummaryResult call() {
				return councilSummaryWebService.saveCouncilSummary(order);
			}
		});
	}
	
	@Override
	public CouncilSummaryResult saveGmworkingCouncilSummary(final CouncilSummaryUploadOrder order) {
		return callInterface(new CallExternalInterface<CouncilSummaryResult>() {
			
			@Override
			public CouncilSummaryResult call() {
				return councilSummaryWebService.saveGmworkingCouncilSummary(order);
			}
		});
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryById(final long summaryId) {
		return callInterface(new CallExternalInterface<FCouncilSummaryInfo>() {
			
			@Override
			public FCouncilSummaryInfo call() {
				return councilSummaryWebService.queryCouncilSummaryById(summaryId);
			}
		});
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FCouncilSummaryInfo>() {
			
			@Override
			public FCouncilSummaryInfo call() {
				return councilSummaryWebService.queryCouncilSummaryByFormId(formId);
			}
		});
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryByCouncilId(final long councilId) {
		return callInterface(new CallExternalInterface<FCouncilSummaryInfo>() {
			
			@Override
			public FCouncilSummaryInfo call() {
				return councilSummaryWebService.queryCouncilSummaryByCouncilId(councilId);
			}
		});
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryByCouncilCode(final String councilCode) {
		return callInterface(new CallExternalInterface<FCouncilSummaryInfo>() {
			
			@Override
			public FCouncilSummaryInfo call() {
				return councilSummaryWebService.queryCouncilSummaryByCouncilCode(councilCode);
			}
		});
	}
	
	@Override
	public List<FCouncilSummaryProjectInfo> queryProjectCsBySummaryId(final long summaryId) {
		return callInterface(new CallExternalInterface<List<FCouncilSummaryProjectInfo>>() {
			
			@Override
			public List<FCouncilSummaryProjectInfo> call() {
				return councilSummaryWebService.queryProjectCsBySummaryId(summaryId);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectInfo queryProjectCsByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectInfo>() {
			
			@Override
			public FCouncilSummaryProjectInfo call() {
				return councilSummaryWebService.queryProjectCsByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectInfo queryProjectCsBySpCode(final String spCode) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectInfo>() {
			
			@Override
			public FCouncilSummaryProjectInfo call() {
				return councilSummaryWebService.queryProjectCsBySpCode(spCode);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectInfo queryProjectCsBySpId(final long spId) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectInfo>() {
			
			@Override
			public FCouncilSummaryProjectInfo call() {
				return councilSummaryWebService.queryProjectCsBySpId(spId);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveProjectCsCommon(final FCouncilSummaryProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilSummaryWebService.saveProjectCsCommon(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveBondProjectCs(final FCouncilSummaryProjectBondOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return councilSummaryWebService.saveBondProjectCs(order);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectBondInfo queryBondProjectCsByProjectCode(final String projectCode,
																			final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectBondInfo>() {
			
			@Override
			public FCouncilSummaryProjectBondInfo call() {
				return councilSummaryWebService.queryBondProjectCsByProjectCode(projectCode,
					queryAll);
			}
		});
	}
	
	@Override
	public FormBaseResult saveGuaranteeProjectCs(final FCouncilSummaryProjectGuaranteeOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return councilSummaryWebService.saveGuaranteeProjectCs(order);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectGuaranteeInfo queryGuaranteeProjectCsByProjectCode(final String projectCode,
																					final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectGuaranteeInfo>() {
			
			@Override
			public FCouncilSummaryProjectGuaranteeInfo call() {
				return councilSummaryWebService.queryGuaranteeProjectCsByProjectCode(projectCode,
					queryAll);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectGuaranteeInfo queryGuaranteeProjectCsBySpId(	final long spId,
																				final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectGuaranteeInfo>() {
			
			@Override
			public FCouncilSummaryProjectGuaranteeInfo call() {
				return councilSummaryWebService.queryGuaranteeProjectCsBySpId(spId, queryAll);
			}
		});
	}
	
	@Override
	public FormBaseResult saveEntrustedProjectCs(final FCouncilSummaryProjectEntrustedOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return councilSummaryWebService.saveEntrustedProjectCs(order);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectEntrustedInfo queryEntrustedProjectCsByProjectCode(final String projectCode,
																					final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectEntrustedInfo>() {
			
			@Override
			public FCouncilSummaryProjectEntrustedInfo call() {
				return councilSummaryWebService.queryEntrustedProjectCsByProjectCode(projectCode,
					queryAll);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectEntrustedInfo queryEntrustedProjectCsBySpId(	final long spId,
																				final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectEntrustedInfo>() {
			
			@Override
			public FCouncilSummaryProjectEntrustedInfo call() {
				return councilSummaryWebService.queryEntrustedProjectCsBySpId(spId, queryAll);
			}
		});
	}
	
	@Override
	public FormBaseResult saveLgLitigationProjectCs(final FCouncilSummaryProjectLgLitigationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return councilSummaryWebService.saveLgLitigationProjectCs(order);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectLgLitigationInfo queryLgLitigationProjectCsByProjectCode(	final String projectCode,
																							final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectLgLitigationInfo>() {
			
			@Override
			public FCouncilSummaryProjectLgLitigationInfo call() {
				return councilSummaryWebService.queryLgLitigationProjectCsByProjectCode(
					projectCode, queryAll);
			}
		});
	}
	
	@Override
	public FormBaseResult saveUnderwritingProjectCs(final FCouncilSummaryProjectUnderwritingOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return councilSummaryWebService.saveUnderwritingProjectCs(order);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectUnderwritingInfo queryUnderwritingProjectCsByProjectCode(	final String projectCode,
																							final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectUnderwritingInfo>() {
			
			@Override
			public FCouncilSummaryProjectUnderwritingInfo call() {
				return councilSummaryWebService.queryUnderwritingProjectCsByProjectCode(
					projectCode, queryAll);
			}
		});
	}
	
	@Override
	public FormBaseResult saveRiskHandleCs(final FCouncilSummaryRiskHandleOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return councilSummaryWebService.saveRiskHandleCs(order);
			}
		});
	}
	
	@Override
	public List<FCouncilSummaryRiskHandleInfo> queryRiskHandleCsBySummaryId(final long SummaryId) {
		return callInterface(new CallExternalInterface<List<FCouncilSummaryRiskHandleInfo>>() {
			
			@Override
			public List<FCouncilSummaryRiskHandleInfo> call() {
				return councilSummaryWebService.queryRiskHandleCsBySummaryId(SummaryId);
			}
		});
	}
	
	@Override
	public List<FCouncilSummaryRiskHandleInfo> queryRiskHandleCsByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<FCouncilSummaryRiskHandleInfo>>() {
			
			@Override
			public List<FCouncilSummaryRiskHandleInfo> call() {
				return councilSummaryWebService.queryRiskHandleCsByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectBondInfo queryBondProjectCsBySpId(final long spId,
																	final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectBondInfo>() {
			
			@Override
			public FCouncilSummaryProjectBondInfo call() {
				return councilSummaryWebService.queryBondProjectCsBySpId(spId, queryAll);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectLgLitigationInfo queryLgLitigationProjectCsBySpId(	final long spId,
																					final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectLgLitigationInfo>() {
			
			@Override
			public FCouncilSummaryProjectLgLitigationInfo call() {
				return councilSummaryWebService.queryLgLitigationProjectCsBySpId(spId, queryAll);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectUnderwritingInfo queryUnderwritingProjectCsBySpId(	final long spId,
																					final boolean queryAll) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectUnderwritingInfo>() {
			
			@Override
			public FCouncilSummaryProjectUnderwritingInfo call() {
				return councilSummaryWebService.queryUnderwritingProjectCsBySpId(spId, queryAll);
			}
		});
	}
	
	@Override
	public FcsBaseResult oneVoteDown(final OneVoteDownOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilSummaryWebService.oneVoteDown(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilSummaryRiskHandleInfo> queryApprovalRiskHandleCs(final CouncilSummaryRiskHandleQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilSummaryRiskHandleInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CouncilSummaryRiskHandleInfo> call() {
				return councilSummaryWebService.queryApprovalRiskHandleCs(order);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectCreditConditionInfo queryCreditConditionBySpId(final long spId,
																				final boolean queryCommon) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectCreditConditionInfo>() {
			
			@Override
			public FCouncilSummaryProjectCreditConditionInfo call() {
				return councilSummaryWebService.queryCreditConditionBySpId(spId, queryCommon);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectGuarantorInfo queryGuarantorById(final long itemId) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectGuarantorInfo>() {
			
			@Override
			public FCouncilSummaryProjectGuarantorInfo call() {
				return councilSummaryWebService.queryGuarantorById(itemId);
			}
		});
	}
	
	@Override
	public FCouncilSummaryProjectPledgeAssetInfo queryPledgeAssetById(final long itemId) {
		return callInterface(new CallExternalInterface<FCouncilSummaryProjectPledgeAssetInfo>() {
			
			@Override
			public FCouncilSummaryProjectPledgeAssetInfo call() {
				return councilSummaryWebService.queryPledgeAssetById(itemId);
			}
		});
	}
	
	@Override
	public FCouncilSummaryRiskHandleInfo queryRiskHandleCsByHandleId(final long handleId) {
		return callInterface(new CallExternalInterface<FCouncilSummaryRiskHandleInfo>() {
			
			@Override
			public FCouncilSummaryRiskHandleInfo call() {
				return councilSummaryWebService.queryRiskHandleCsByHandleId(handleId);
			}
		});
	}
	
	@Override
	public FCouncilSummaryRiskHandleInfo queryRiskHandleCsByRedoProjectCode(final String redoProjectCode) {
		return callInterface(new CallExternalInterface<FCouncilSummaryRiskHandleInfo>() {
			
			@Override
			public FCouncilSummaryRiskHandleInfo call() {
				return councilSummaryWebService.queryRiskHandleCsByRedoProjectCode(redoProjectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult submitSummary(final CouncilSummarySubmitOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return councilSummaryWebService.submitSummary(order);
			}
		});
	}
}
