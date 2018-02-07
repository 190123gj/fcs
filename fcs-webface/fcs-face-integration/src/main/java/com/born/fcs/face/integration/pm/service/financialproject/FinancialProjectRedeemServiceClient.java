package com.born.fcs.face.integration.pm.service.financialproject;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialRedeemApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectRedeemFormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeRedeemInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialRedeemApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectRedeemFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectRedeemService;

@Service("financialProjectRedeemServiceClient")
public class FinancialProjectRedeemServiceClient extends ClientAutowiredBaseService implements
																					FinancialProjectRedeemService {
	
	@Override
	public QueryBaseBatchResult<FinancialProjectRedeemFormInfo> queryPage(	final FinancialProjectRedeemFormQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FinancialProjectRedeemFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FinancialProjectRedeemFormInfo> call() {
				return financialProjectRedeemWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> queryTrade(final ProjectFinancialTradeRedeemQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> call() {
				return financialProjectRedeemWebService.queryTrade(order);
			}
		});
	}
	
	@Override
	public FProjectFinancialRedeemApplyInfo queryApplyByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectFinancialRedeemApplyInfo>() {
			
			@Override
			public FProjectFinancialRedeemApplyInfo call() {
				return financialProjectRedeemWebService.queryApplyByFormId(formId);
			}
		});
	}
	
	@Override
	public ProjectFinancialTradeRedeemInfo queryTradeByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<ProjectFinancialTradeRedeemInfo>() {
			
			@Override
			public ProjectFinancialTradeRedeemInfo call() {
				return financialProjectRedeemWebService.queryTradeByApplyId(applyId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FProjectFinancialRedeemApplyOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return financialProjectRedeemWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveTrade(final ProjectFinancialTradeRedeemOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProjectRedeemWebService.saveTrade(order);
			}
		});
	}
	
}
