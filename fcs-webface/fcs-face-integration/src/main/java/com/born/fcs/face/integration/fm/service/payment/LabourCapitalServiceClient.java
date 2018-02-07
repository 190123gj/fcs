package com.born.fcs.face.integration.fm.service.payment;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalQueryOrder;
import com.born.fcs.fm.ws.service.payment.LabourCapitalService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 费用支付申请单客户端
 * @author lzb
 * 
 */
@Service("labourCapitalServiceClient")
public class LabourCapitalServiceClient extends ClientAutowiredBaseService implements
																			LabourCapitalService {
	
	@Override
	public QueryBaseBatchResult<FormLabourCapitalInfo> queryPage(final LabourCapitalQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormLabourCapitalInfo>>() {
			@Override
			public QueryBaseBatchResult<FormLabourCapitalInfo> call() {
				return labourCapitalService.queryPage(order);
			}
		});
	}
	
	@Override
	public FormLabourCapitalInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FormLabourCapitalInfo>() {
			@Override
			public FormLabourCapitalInfo call() {
				return labourCapitalService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FormLabourCapitalInfo queryById(final long labourCapitalId) {
		return callInterface(new CallExternalInterface<FormLabourCapitalInfo>() {
			@Override
			public FormLabourCapitalInfo call() {
				return labourCapitalService.queryById(labourCapitalId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final LabourCapitalOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return labourCapitalService.save(order);
			}
		});
	}
	
	@Override
	public List<FormLabourCapitalDetailInfo> findSublInfoByDeptCategory(final long deptId,
																		final long categoryId,
																		final Date applyTimeStart,
																		final Date applyTimeEnd) {
		return callInterface(new CallExternalInterface<List<FormLabourCapitalDetailInfo>>() {
			@Override
			public List<FormLabourCapitalDetailInfo> call() {
				return labourCapitalService.findSublInfoByDeptCategory(deptId, categoryId,
					applyTimeStart, applyTimeEnd);
			}
		});
	}
	
	@Override
	public FormBaseResult updatePayBank(final LabourCapitalOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return labourCapitalService.updatePayBank(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult syncForecast(final long formId, final boolean afterAudit) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return labourCapitalService.syncForecast(formId, afterAudit);
			}
		});
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(final FormLabourCapitalInfo expenseInfo) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return labourCapitalService.sync2FinancialSys(expenseInfo);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return labourCapitalService.updateVoucher(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> queryPageAll(	final LabourCapitalQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormLabourCapitalDetailAllInfo>>() {
			@Override
			public QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> call() {
				return labourCapitalService.queryPageAll(order);
			}
		});
	}
}
