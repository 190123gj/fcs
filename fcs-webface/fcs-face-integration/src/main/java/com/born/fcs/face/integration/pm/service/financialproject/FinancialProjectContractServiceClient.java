package com.born.fcs.face.integration.pm.service.financialproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialContractInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectContractFormInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractStatusOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectContractService;

@Service("financialProjectContractServiceClient")
public class FinancialProjectContractServiceClient extends ClientAutowiredBaseService implements
																						FinancialProjectContractService {
	
	@Autowired
	FinancialProjectContractService financialProjectContractWebService;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectContractFormInfo> queryPage(final ProjectFinancialContractFormQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FinancialProjectContractFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FinancialProjectContractFormInfo> call() {
				return financialProjectContractWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public FProjectFinancialContractInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectFinancialContractInfo>() {
			
			@Override
			public FProjectFinancialContractInfo call() {
				return financialProjectContractWebService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final ProjectFinancialContractOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return financialProjectContractWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult changeStatus(final ProjectFinancialContractStatusOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProjectContractWebService.changeStatus(order);
			}
		});
	}
}
