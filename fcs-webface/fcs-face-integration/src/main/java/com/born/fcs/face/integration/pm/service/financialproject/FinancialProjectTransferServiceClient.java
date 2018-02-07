package com.born.fcs.face.integration.pm.service.financialproject;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialTansferApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectTransferFormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeTansferInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialTansferApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectTransferFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;

@Service("financialProjectTransferServiceClient")
public class FinancialProjectTransferServiceClient extends ClientAutowiredBaseService implements
																						FinancialProjectTransferService {
	
	@Override
	public QueryBaseBatchResult<FinancialProjectTransferFormInfo> queryPage(final FinancialProjectTransferFormQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FinancialProjectTransferFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FinancialProjectTransferFormInfo> call() {
				return financialProjectTransferWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> queryTrade(	final ProjectFinancialTradeTansferQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectFinancialTradeTansferInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> call() {
				return financialProjectTransferWebService.queryTrade(order);
			}
		});
	}
	
	@Override
	public FProjectFinancialTansferApplyInfo queryApplyByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectFinancialTansferApplyInfo>() {
			
			@Override
			public FProjectFinancialTansferApplyInfo call() {
				return financialProjectTransferWebService.queryApplyByFormId(formId);
			}
		});
	}
	
	@Override
	public ProjectFinancialTradeTansferInfo queryTradeByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<ProjectFinancialTradeTansferInfo>() {
			
			@Override
			public ProjectFinancialTradeTansferInfo call() {
				return financialProjectTransferWebService.queryTradeByApplyId(applyId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FProjectFinancialTansferApplyOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return financialProjectTransferWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveTrade(final ProjectFinancialTradeTansferOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProjectTransferWebService.saveTrade(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult syncForecast(long applyId) {
		return null;
	}
}
