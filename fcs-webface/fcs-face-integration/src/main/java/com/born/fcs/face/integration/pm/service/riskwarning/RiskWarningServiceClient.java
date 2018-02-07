package com.born.fcs.face.integration.pm.service.riskwarning;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningInfo;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningService;

/**
 * 
 * 风险预警
 * 
 * 
 * @author lirz
 * 
 * 2016-4-22 上午11:43:38
 */
@Service("riskWarningServiceClient")
public class RiskWarningServiceClient extends ClientAutowiredBaseService implements
																		RiskWarningService {
	
	@Override
	public FormBaseResult save(final FRiskWarningOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return riskWarningWebService.save(order);
			}
		});
	}
	
	@Override
	public FRiskWarningInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FRiskWarningInfo>() {
			
			@Override
			public FRiskWarningInfo call() throws SocketTimeoutException {
				return riskWarningWebService.findById(id);
			}
		});
	}
	
	@Override
	public FRiskWarningInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FRiskWarningInfo>() {
			
			@Override
			public FRiskWarningInfo call() throws SocketTimeoutException {
				return riskWarningWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<RiskWarningInfo> queryList(final RiskWarningQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<RiskWarningInfo>>() {
			
			@Override
			public QueryBaseBatchResult<RiskWarningInfo> call() throws SocketTimeoutException {
				return riskWarningWebService.queryList(queryOrder);
			}
		});
	}
	
}
