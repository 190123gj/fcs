package com.born.fcs.face.integration.pm.service.riskreview;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.FormProjectInfo;
import com.born.fcs.pm.ws.info.riskreview.RiskReviewReportInfo;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewOrder;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskreview.RiskReviewReportService;

/**
 * 风险审查报告
 * 
 * @author Fei
 *
 */
@Service("riskReviewReportServiceClient")
public class RiskReviewReportServiceClient extends ClientAutowiredBaseService implements
																				RiskReviewReportService {
	
	@Override
	public FormBaseResult saveRiskReview(final FRiskReviewOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return riskReviewReportWebService.saveRiskReview(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateRiskReviewReportContent(final FRiskReviewOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return riskReviewReportWebService.updateRiskReviewReportContent(order);
			}
		});
	}
	
	@Override
	public RiskReviewReportInfo queryRiskReviewByFormId(final long formId) {
		return callInterface(new CallExternalInterface<RiskReviewReportInfo>() {
			
			@Override
			public RiskReviewReportInfo call() {
				return riskReviewReportWebService.queryRiskReviewByFormId(formId);
			}
		});
	}
	
	@Override
	public RiskReviewReportInfo queryRiskReviewById(final long id) {
		return callInterface(new CallExternalInterface<RiskReviewReportInfo>() {
			
			@Override
			public RiskReviewReportInfo call() {
				return riskReviewReportWebService.queryRiskReviewById(id);
			}
		});
	}
	
	@Override
	public RiskReviewReportInfo queryRiskReviewByCode(final String projectCode) {
		return callInterface(new CallExternalInterface<RiskReviewReportInfo>() {
			
			@Override
			public RiskReviewReportInfo call() {
				return riskReviewReportWebService.queryRiskReviewByCode(projectCode);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormProjectInfo> queryRiskReview(	final FRiskReviewQueryOrder riskReviewQueryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormProjectInfo> call() {
				return riskReviewReportWebService.queryRiskReview(riskReviewQueryOrder);
			}
		});
	}

	@Override
	public FcsBaseResult save(final FRiskReviewOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return riskReviewReportWebService.save(order);
			}
		});
	}
	
}
