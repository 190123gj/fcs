package com.born.fcs.face.integration.pm.service.financialproject;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectSetupFormInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;

/**
 * 理财项目立项
 * @author wuzj
 *
 */
public class FinancialProjectSetupServiceClient extends ClientAutowiredBaseService implements
																					FinancialProjectSetupService {
	
	@Override
	public QueryBaseBatchResult<FinancialProjectSetupFormInfo> query(	final FinancialProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FinancialProjectSetupFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FinancialProjectSetupFormInfo> call() {
				return financialProjectSetupWebService.query(order);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FProjectFinancialOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return financialProjectSetupWebService.save(order);
			}
		});
	}
	
	@Override
	public FProjectFinancialInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FProjectFinancialInfo>() {
			
			@Override
			public FProjectFinancialInfo call() {
				return financialProjectSetupWebService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FProjectFinancialInfo queryByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FProjectFinancialInfo>() {
			
			@Override
			public FProjectFinancialInfo call() {
				return financialProjectSetupWebService.queryByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult syncForecast(String projectCode) {
		return null;
	}
}
