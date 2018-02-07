package com.born.fcs.face.integration.pm.service.financialproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialReviewInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectReviewFormInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialRiskReviewOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectReviewService;

@Service("financialProjectReviewServiceClient")
public class FinancialProjectReviewServiceClient extends ClientAutowiredBaseService implements
																					FinancialProjectReviewService {
	
	@Autowired
	FinancialProjectReviewService financialProjectReviewWebService;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectReviewFormInfo> queryPage(	final ProjectFinancialReviewFormQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FinancialProjectReviewFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FinancialProjectReviewFormInfo> call() {
				return financialProjectReviewWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public FProjectFinancialReviewInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectFinancialReviewInfo>() {
			
			@Override
			public FProjectFinancialReviewInfo call() {
				return financialProjectReviewWebService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final ProjectFinancialReviewOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return financialProjectReviewWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveRiskReview(final ProjectFinancialRiskReviewOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProjectReviewWebService.saveRiskReview(order);
			}
		});
	}
	
}
