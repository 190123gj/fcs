package com.born.fcs.face.integration.crm.service.evalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.EvaluetingService;
import com.born.fcs.crm.ws.service.info.EvaluetingListAuditInfo;
import com.born.fcs.crm.ws.service.order.EvaluetingOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluetingListQueryOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluetingQueryOrder;
import com.born.fcs.crm.ws.service.result.EvalutingResult;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 评价
 * */
@Service("evaluetingClient")
public class EvaluetingClient extends ClientAutowiredBaseService implements EvaluetingService {
	
	@Autowired
	EvaluetingService evaluetingService;
	
	@Override
	public FcsBaseResult baseEvalueting(final EvaluetingOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return evaluetingService.baseEvalueting(order);
			}
		});
	}
	
	@Override
	public EvalutingResult queryEvalueResult(final EvaluetingQueryOrder order) {
		return callInterface(new CallExternalInterface<EvalutingResult>() {
			
			@Override
			public EvalutingResult call() {
				return evaluetingService.queryEvalueResult(order);
			}
		});
	}
	
	@Override
	public EvalutingResult evalueCount(final EvaluetingQueryOrder order) {
		return callInterface(new CallExternalInterface<EvalutingResult>() {
			
			@Override
			public EvalutingResult call() {
				return evaluetingService.evalueCount(order);
			}
		});
	}
	
	@Override
	public EvalutingResult financeInfoFromPro(final long userId) {
		return callInterface(new CallExternalInterface<EvalutingResult>() {
			
			@Override
			public EvalutingResult call() {
				return evaluetingService.financeInfoFromPro(userId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<EvaluetingListAuditInfo> list(final EvaluetingListQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<EvaluetingListAuditInfo>>() {
			
			@Override
			public QueryBaseBatchResult<EvaluetingListAuditInfo> call() {
				return evaluetingService.list(order);
			}
		});
	}
	
	@Override
	public EvaluetingListAuditInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<EvaluetingListAuditInfo>() {
			
			@Override
			public EvaluetingListAuditInfo call() {
				return evaluetingService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult aditMakeData(final EvaluetingQueryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return evaluetingService.aditMakeData(order);
			}
		});
	}
	
}
