package com.born.fcs.face.integration.pm.service.investigation;

import java.net.SocketTimeoutException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCsRationalityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialDataExplainInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationLitigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMajorEventInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationProjectStatusInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationRiskInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationUnderwritingInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.checking.InvestigationCheckingInfo;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCsRationalityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialDataExplainOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationLitigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMajorEventOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOpabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationProjectStatusOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationRiskOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationUnderwritingOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCustomerOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.InvestigationBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.checking.InvestigationCheckingOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.DeclareBaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;

/**
 * 
 * 尽职调查报告
 * 
 * @author lirz
 * 
 * 2016-3-15 下午3:27:42
 */
@Service("investigationServiceClient")
public class InvestigationServiceClient extends ClientAutowiredBaseService implements
																			InvestigationService {
	
	@Override
	public FcsBaseResult updateInvestigation(final InvestigationBaseOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return investigationWebService.updateInvestigation(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveInvestigation(final FInvestigationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveInvestigation(order);
			}
		});
	}
	
	@Override
	public FInvestigationInfo findInvestigationByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationInfo>() {
			
			@Override
			public FInvestigationInfo call() throws SocketTimeoutException {
				return investigationWebService.findInvestigationByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveInvestigationCreditScheme(final FInvestigationCreditSchemeOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveInvestigationCreditScheme(order);
			}
		});
	}
	
	@Override
	public FInvestigationCreditSchemeInfo findInvestigationCreditSchemeByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationCreditSchemeInfo>() {
			
			@Override
			public FInvestigationCreditSchemeInfo call() throws SocketTimeoutException {
				return investigationWebService.findInvestigationCreditSchemeByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveInvestigationMainlyReview(final FInvestigationMainlyReviewOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveInvestigationMainlyReview(order);
			}
		});
	}
	
	@Override
	public FInvestigationMainlyReviewInfo findInvestigationMainlyReviewByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationMainlyReviewInfo>() {
			
			@Override
			public FInvestigationMainlyReviewInfo call() throws SocketTimeoutException {
				return investigationWebService.findInvestigationMainlyReviewByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationMabilityReview(	final FInvestigationMabilityReviewOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationMabilityReview(order);
			}
		});
	}
	
	@Override
	public FInvestigationMabilityReviewInfo findFInvestigationMabilityReviewByFormId(	final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationMabilityReviewInfo>() {
			
			@Override
			public FInvestigationMabilityReviewInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationMabilityReviewByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationOpabilityReview(final FInvestigationOpabilityReviewOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationOpabilityReview(order);
			}
		});
	}
	
	@Override
	public FInvestigationOpabilityReviewInfo findFInvestigationOpabilityReviewByFormId(	final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationOpabilityReviewInfo>() {
			
			@Override
			public FInvestigationOpabilityReviewInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationOpabilityReviewByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationMajorEvent(final FInvestigationMajorEventOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationMajorEvent(order);
			}
		});
	}
	
	@Override
	public FInvestigationMajorEventInfo findFInvestigationMajorEventByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationMajorEventInfo>() {
			
			@Override
			public FInvestigationMajorEventInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationMajorEventByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationProjectStatus(	final FInvestigationProjectStatusOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationProjectStatus(order);
			}
		});
	}
	
	@Override
	public FInvestigationProjectStatusInfo findFInvestigationProjectStatusByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationProjectStatusInfo>() {
			
			@Override
			public FInvestigationProjectStatusInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationProjectStatusByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationCsRationalityReview(final FInvestigationCsRationalityReviewOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationCsRationalityReview(order);
			}
		});
	}
	
	@Override
	public FInvestigationCsRationalityReviewInfo findFInvestigationCsRationalityReviewByFormId(	final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationCsRationalityReviewInfo>() {
			
			@Override
			public FInvestigationCsRationalityReviewInfo call() throws SocketTimeoutException {
				return investigationWebService
					.findFInvestigationCsRationalityReviewByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationRisk(final FInvestigationRiskOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationRisk(order);
			}
		});
	}
	
	@Override
	public FInvestigationRiskInfo findFInvestigationRiskByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationRiskInfo>() {
			
			@Override
			public FInvestigationRiskInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationRiskByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationFinancialReview(final FInvestigationFinancialReviewOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationFinancialReview(order);
			}
		});
	}
	
	@Override
	public FInvestigationFinancialReviewInfo findFInvestigationFinancialReviewByFormId(	final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationFinancialReviewInfo>() {
			
			@Override
			public FInvestigationFinancialReviewInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationFinancialReviewByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<InvestigationInfo> queryInvestigation(	final InvestigationQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<InvestigationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<InvestigationInfo> call() throws SocketTimeoutException {
				return investigationWebService.queryInvestigation(queryOrder);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationLitigation(final FInvestigationLitigationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationLitigation(order);
			}
		});
	}
	
	@Override
	public FInvestigationLitigationInfo findFInvestigationLitigationByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationLitigationInfo>() {
			
			@Override
			public FInvestigationLitigationInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationLitigationByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveFInvestigationUnderwriting(final FInvestigationUnderwritingOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationUnderwriting(order);
			}
		});
	}
	
	@Override
	public FInvestigationUnderwritingInfo findFInvestigationUnderwritingByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInvestigationUnderwritingInfo>() {
			
			@Override
			public FInvestigationUnderwritingInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationUnderwritingByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveFInvestigationFinancialDataExplain(final FInvestigationFinancialDataExplainOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return investigationWebService.saveFInvestigationFinancialDataExplain(order);
			}
		});
	}
	
	@Override
	public FInvestigationFinancialDataExplainInfo findFInvestigationFinancialDataExplainById(	final long id) {
		return callInterface(new CallExternalInterface<FInvestigationFinancialDataExplainInfo>() {
			
			@Override
			public FInvestigationFinancialDataExplainInfo call() throws SocketTimeoutException {
				return investigationWebService.findFInvestigationFinancialDataExplainById(id);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> querySelectableProject(final InvestigationQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() throws SocketTimeoutException {
				return investigationWebService.querySelectableProject(queryOrder);
			}
		});
	}
	
	@Override
	public long queryInvestigationFormIdByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() throws SocketTimeoutException {
				return investigationWebService.queryInvestigationFormIdByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateToShow(final long formId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return investigationWebService.updateToShow(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final DeclareBaseOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return investigationWebService.update(order);
			}
		});
	}
	
	@Override
	public long investigationUseAssetCount(final long assetsId) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() throws SocketTimeoutException {
				return investigationWebService.investigationUseAssetCount(assetsId);
			}
		});
	}

	@Override
	public FcsBaseResult updateCustomerInfo(final UpdateInvestigationCustomerOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return investigationWebService.updateCustomerInfo(order);
			}
		});
	}

	@Override
	public FcsBaseResult createChecking(final InvestigationCheckingOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return investigationWebService.createChecking(order);
			}
		});
	}

	@Override
	public InvestigationCheckingInfo findCheckingByFormId(final long formId) {
		return callInterface(new CallExternalInterface<InvestigationCheckingInfo>() {
			
			@Override
			public InvestigationCheckingInfo call() throws SocketTimeoutException {
				return investigationWebService.findCheckingByFormId(formId);
			}
		});
	}

	@Override
	public InvestigationCheckingInfo findCheckingByCheckPiontAndRelatedFormId(	final String checkPoint,
																				final long relatedFormId) {
		return callInterface(new CallExternalInterface<InvestigationCheckingInfo>() {
			
			@Override
			public InvestigationCheckingInfo call() throws SocketTimeoutException {
				return investigationWebService.findCheckingByCheckPiontAndRelatedFormId(checkPoint,
					relatedFormId);
			}
		});
	}

	@Override
	public void deleteInvestigationCommonAttachment(final long formId) {
	}

	@Override
	public List<InvestigationCheckingInfo> findCheckingByRelatedFormId(final long relatedFormId) {
		return callInterface(new CallExternalInterface<List<InvestigationCheckingInfo>>() {
			
			@Override
			public List<InvestigationCheckingInfo> call() throws SocketTimeoutException {
				return investigationWebService.findCheckingByRelatedFormId(relatedFormId);
			}
		});
	}

	@Override
	public FInvestigationFinancialReviewInfo findFInvestigationFinancialReviewByFormIdAndSubindex(	final long formId,
																									final int subIndex) {
		return callInterface(new CallExternalInterface<FInvestigationFinancialReviewInfo>() {
			
			@Override
			public FInvestigationFinancialReviewInfo call() throws SocketTimeoutException {
				return investigationWebService
					.findFInvestigationFinancialReviewByFormIdAndSubindex(formId, subIndex);
			}
		});
	}
	
}
