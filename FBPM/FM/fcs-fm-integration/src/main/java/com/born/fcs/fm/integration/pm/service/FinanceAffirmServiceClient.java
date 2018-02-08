package com.born.fcs.fm.integration.pm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeCapitalOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;

@Service("financeAffirmServiceClient")
public class FinanceAffirmServiceClient extends ClientAutowiredBaseService implements
																			FinanceAffirmService {
	
	@Autowired
	FinanceAffirmService financeAffirmWebService;
	
	@Override
	public QueryBaseBatchResult<ProjectChargePayInfo> queryProjectChargePayDetail(	final ProjectChargePayQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectChargePayInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectChargePayInfo> call() {
				return financeAffirmWebService.queryProjectChargePayDetail(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectChargePayInfo> queryProjectChargePayDetailChoose(final ProjectChargePayQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectChargePayInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectChargePayInfo> call() {
				return financeAffirmWebService.queryProjectChargePayDetailChoose(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveChargeCapital(final ChargeCapitalOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financeAffirmWebService.saveChargeCapital(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final FFinanceAffirmOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financeAffirmWebService.save(order);
			}
		});
	}
}
