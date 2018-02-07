package com.born.fcs.face.integration.pm.service.summary;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.summary.AfterwardsProjectSummaryInfo;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryOrder;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.summary.AfterwardsProjectSummaryResult;
import com.born.fcs.pm.ws.service.summary.AfterwardsProjectSummaryService;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * @author jil
 * 
 */
@Service("afterwardsProjectSummaryServiceClient")
public class AfterwardsProjectSummaryServiceClient extends ClientAutowiredBaseService implements
																						AfterwardsProjectSummaryService {
	
	@Override
	public AfterwardsProjectSummaryInfo findById(final long summaryId) {
		return callInterface(new CallExternalInterface<AfterwardsProjectSummaryInfo>() {
			
			@Override
			public AfterwardsProjectSummaryInfo call() {
				return afterwardsProjectSummaryWebService.findById(summaryId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final AfterwardsProjectSummaryOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return afterwardsProjectSummaryWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AfterwardsProjectSummaryInfo> query(final AfterwardsProjectSummaryQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AfterwardsProjectSummaryInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AfterwardsProjectSummaryInfo> call() {
				return afterwardsProjectSummaryWebService.query(order);
			}
		});
	}
	
	@Override
	public int deleteById(final long summaryId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return afterwardsProjectSummaryWebService.deleteById(summaryId);
			}
		});
	}
	
	@Override
	public int findHouseholdsByCondition(final List<String> busiTypeList, final Date finishTime,
											final String deptCode) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return afterwardsProjectSummaryWebService.findHouseholdsByCondition(busiTypeList,
					finishTime, deptCode);
			}
		});
	}
	
	@Override
	public Money findReleasingAmountByCondition(final List<String> busiTypeList,
												final Date finishTime, final String deptCode) {
		return callInterface(new CallExternalInterface<Money>() {
			
			@Override
			public Money call() {
				return afterwardsProjectSummaryWebService.findReleasingAmountByCondition(
					busiTypeList, finishTime, deptCode);
			}
		});
	}
	
	@Override
	public AfterwardsProjectSummaryInfo findByDeptCodeAndReportPeriod(final String deptCode,
																		final String reportPeriod) {
		return callInterface(new CallExternalInterface<AfterwardsProjectSummaryInfo>() {
			
			@Override
			public AfterwardsProjectSummaryInfo call() {
				return afterwardsProjectSummaryWebService.findByDeptCodeAndReportPeriod(deptCode,
					reportPeriod);
			}
		});
	}
	
	@Override
	public AfterwardsProjectSummaryInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<AfterwardsProjectSummaryInfo>() {
			
			@Override
			public AfterwardsProjectSummaryInfo call() {
				return afterwardsProjectSummaryWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public AfterwardsProjectSummaryResult findProjectSummaryInfoByCondition(final boolean isGuarantee,
																			final Date finishTime,
																			final String deptCode) {
		return callInterface(new CallExternalInterface<AfterwardsProjectSummaryResult>() {
			
			@Override
			public AfterwardsProjectSummaryResult call() {
				return afterwardsProjectSummaryWebService.findProjectSummaryInfoByCondition(
					isGuarantee, finishTime, deptCode);
			}
		});
	}
	
	@Override
	public AfterwardsProjectSummaryResult queryProjectSummaryInfoByCondition(	final Date finishTime,
																				final String projectCode) {
		return callInterface(new CallExternalInterface<AfterwardsProjectSummaryResult>() {
			
			@Override
			public AfterwardsProjectSummaryResult call() {
				return afterwardsProjectSummaryWebService.queryProjectSummaryInfoByCondition(
					finishTime, projectCode);
			}
		});
	}
	
}
