package com.born.fcs.face.integration.pm.service.riskwarning;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningSignalInfo;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningSignalQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningSignalService;

/**
 * 
 * 风险预警信号
 * 
 * 
 * @author lirz
 * 
 * 2016-4-22 上午11:44:16
 */
@Service("riskWarningSignalServiceClient")
public class RiskWarningSignalServiceClient extends ClientAutowiredBaseService implements
																				RiskWarningSignalService {
	
	@Override
	public QueryBaseBatchResult<RiskWarningSignalInfo> findByCondition(	final RiskWarningSignalQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<RiskWarningSignalInfo>>() {
			
			@Override
			public QueryBaseBatchResult<RiskWarningSignalInfo> call() throws SocketTimeoutException {
				return riskWarningSignalWebService.findByCondition(queryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<RiskWarningSignalInfo> findCompanySpecial() {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<RiskWarningSignalInfo>>() {
			
			@Override
			public QueryBaseBatchResult<RiskWarningSignalInfo> call() throws SocketTimeoutException {
				return riskWarningSignalWebService.findCompanySpecial();
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<RiskWarningSignalInfo> findCompanyNomal() {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<RiskWarningSignalInfo>>() {
			
			@Override
			public QueryBaseBatchResult<RiskWarningSignalInfo> call() throws SocketTimeoutException {
				return riskWarningSignalWebService.findCompanyNomal();
			}
		});
	}
	
}
