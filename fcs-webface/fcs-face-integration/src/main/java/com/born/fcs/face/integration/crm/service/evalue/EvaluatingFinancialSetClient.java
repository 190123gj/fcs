package com.born.fcs.face.integration.crm.service.evalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.EvaluatingFinancialSetService;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.query.FinancialSetQueryOrder;
import com.born.fcs.crm.ws.service.result.FinancialSetResult;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 财务指标配置
 * */
@Service("evaluatingFinancialSetClient")
public class EvaluatingFinancialSetClient extends ClientAutowiredBaseService implements
																			EvaluatingFinancialSetService {
	
	@Autowired
	EvaluatingFinancialSetService EvaluatingFinancialSetService;
	
	@Override
	public FcsBaseResult update(final ListOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return EvaluatingFinancialSetService.update(order);
			}
		});
	}
	
	@Override
	public FinancialSetResult list(final FinancialSetQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<FinancialSetResult>() {
			
			@Override
			public FinancialSetResult call() {
				return EvaluatingFinancialSetService.list(queryOrder);
			}
		});
	}
}
