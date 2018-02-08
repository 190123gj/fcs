package com.born.fcs.pm.integration.fm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailListInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmInfo;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeBatchConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailListQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmQueryOrder;
import com.born.fcs.fm.ws.result.incomeconfirm.IncomeDetailBatchResult;
import com.born.fcs.fm.ws.service.incomeconfirm.IncomeConfirmService;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;

@Service("incomeConfirmServiceClient")
public class IncomeConfirmServiceClient extends ClientAutowiredBaseService implements
																			IncomeConfirmService {
	@Autowired
	IncomeConfirmService incomeConfirmWebService;
	
	@Override
	public FcsBaseResult save(final IncomeConfirmOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return incomeConfirmWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<IncomeConfirmInfo> query(final IncomeConfirmQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<IncomeConfirmInfo>>() {
			
			@Override
			public QueryBaseBatchResult<IncomeConfirmInfo> call() {
				return incomeConfirmWebService.query(order);
			}
		});
	}
	
	@Override
	public IncomeConfirmInfo findById(final long incomeId) {
		return callInterface(new CallExternalInterface<IncomeConfirmInfo>() {
			
			@Override
			public IncomeConfirmInfo call() {
				return incomeConfirmWebService.findById(incomeId);
			}
		});
	}
	
	@Override
	public IncomeConfirmInfo findByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<IncomeConfirmInfo>() {
			
			@Override
			public IncomeConfirmInfo call() {
				return incomeConfirmWebService.findByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public List<IncomeConfirmDetailInfo> findIncomeConfirmDetailById(final long incomeId) {
		return callInterface(new CallExternalInterface<List<IncomeConfirmDetailInfo>>() {
			
			@Override
			public List<IncomeConfirmDetailInfo> call() {
				return incomeConfirmWebService.findIncomeConfirmDetailById(incomeId);
			}
		});
	}
	
	@Override
	public List<ProjectChargeDetailInfo> queryFeeList(final String projectCode) {
		return callInterface(new CallExternalInterface<List<ProjectChargeDetailInfo>>() {
			
			@Override
			public List<ProjectChargeDetailInfo> call() {
				return incomeConfirmWebService.queryFeeList(projectCode);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<IncomeConfirmDetailInfo> queryDetail(	final IncomeConfirmDetailQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<IncomeConfirmDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<IncomeConfirmDetailInfo> call() {
				return incomeConfirmWebService.queryDetail(order);
			}
		});
	}
	
	@Override
	public IncomeDetailBatchResult<IncomeConfirmDetailListInfo> queryBatchConfirmList(	final IncomeConfirmDetailListQueryOrder order) {
		return callInterface(new CallExternalInterface<IncomeDetailBatchResult<IncomeConfirmDetailListInfo>>() {
			
			@Override
			public IncomeDetailBatchResult<IncomeConfirmDetailListInfo> call() {
				return incomeConfirmWebService.queryBatchConfirmList(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult batchConfirm(final IncomeBatchConfirmOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return incomeConfirmWebService.batchConfirm(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateIncomeConfirmDetail(final String projectCode, final long incomeId,
													final String incomePeriod) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return incomeConfirmWebService.updateIncomeConfirmDetail(projectCode, incomeId,
					incomePeriod);
			}
		});
	}
}
