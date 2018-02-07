package com.born.fcs.face.integration.pm.service.fund;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanInfo;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.fund.ChargeRepayPlanService;

@Service("chargeRepayPlanServiceClient")
public class ChargeRepayPlanServiceClient extends ClientAutowiredBaseService implements
																			ChargeRepayPlanService {
	
	@Override
	public FcsBaseResult savePlan(final ChargeRepayPlanOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return chargeRepayPlanWebService.savePlan(order);
			}
		});
	}
	
	@Override
	public ChargeRepayPlanInfo queryPlanById(final long planId) {
		return callInterface(new CallExternalInterface<ChargeRepayPlanInfo>() {
			
			@Override
			public ChargeRepayPlanInfo call() throws SocketTimeoutException {
				return chargeRepayPlanWebService.queryPlanById(planId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ChargeRepayPlanInfo> queryPlan(final ChargeRepayPlanQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ChargeRepayPlanInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ChargeRepayPlanInfo> call() throws SocketTimeoutException {
				return chargeRepayPlanWebService.queryPlan(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ChargeRepayPlanDetailInfo> queryPlanDetail(	final ChargeRepayPlanDetailQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ChargeRepayPlanDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ChargeRepayPlanDetailInfo> call()
																			throws SocketTimeoutException {
				return chargeRepayPlanWebService.queryPlanDetail(order);
			}
		});
	}
}
