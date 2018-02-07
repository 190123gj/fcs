package com.born.fcs.face.integration.pm.service.counterguarantee;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.counterguarantee.FCounterGuaranteeReleaseInfo;
import com.born.fcs.pm.ws.info.counterguarantee.FCounterGuaranteeRepayInfo;
import com.born.fcs.pm.ws.info.counterguarantee.GuaranteeApplyCounterInfo;
import com.born.fcs.pm.ws.info.counterguarantee.ReleaseApplyInfo;
import com.born.fcs.pm.ws.order.counterguarantee.CounterGuaranteeQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.FCounterGuaranteeReleaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.counterguarantee.CounterGuaranteeService;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 到期解保
 *
 *
 * @author lirz
 *
 * 2016-5-12 下午6:46:42
 */
@Service("counterGuaranteeServiceClient")
public class CounterGuaranteeServiceClient extends ClientAutowiredBaseService implements
																			CounterGuaranteeService {
	
	@Override
	public FormBaseResult save(final FCounterGuaranteeReleaseOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return counterGuaranteeWebService.save(order);
			}
		});
	}

	@Override
	public FCounterGuaranteeReleaseInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FCounterGuaranteeReleaseInfo>() {
			
			@Override
			public FCounterGuaranteeReleaseInfo call() throws SocketTimeoutException {
				return counterGuaranteeWebService.findByFormId(formId);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ReleaseApplyInfo> queryList(final CounterGuaranteeQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ReleaseApplyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ReleaseApplyInfo> call() throws SocketTimeoutException {
				return counterGuaranteeWebService.queryList(queryOrder);
			}
		});
	}

	@Override
	public FcsBaseResult canRelease(final FCounterGuaranteeReleaseOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return counterGuaranteeWebService.canRelease(order);
			}
		});
	}

	@Override
	public Money queryReleasedAmount(final String projectCode) {
		return callInterface(new CallExternalInterface<Money>() {
			
			@Override
			public Money call() throws SocketTimeoutException {
				return counterGuaranteeWebService.queryReleasedAmount(projectCode);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjects(final QueryProjectBase queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() throws SocketTimeoutException {
				return counterGuaranteeWebService.queryProjects(queryOrder);
			}
		});
	}

	@Override
	public Money queryReleasingAmount(final String projectCode) {
		return callInterface(new CallExternalInterface<Money>() {
			
			@Override
			public Money call() throws SocketTimeoutException {
				return counterGuaranteeWebService.queryReleasingAmount(projectCode);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<GuaranteeApplyCounterInfo> queryCounts(final long formId) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<GuaranteeApplyCounterInfo>>() {
			
			@Override
			public QueryBaseBatchResult<GuaranteeApplyCounterInfo> call() throws SocketTimeoutException {
				return counterGuaranteeWebService.queryCounts(formId);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<FCounterGuaranteeRepayInfo> queryRepays(final QueryProjectBase queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FCounterGuaranteeRepayInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FCounterGuaranteeRepayInfo> call()
																			throws SocketTimeoutException {
				return counterGuaranteeWebService.queryRepays(queryOrder);
			}
		});
	}
	
}
