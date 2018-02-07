package com.born.fcs.face.integration.fm.service.payment;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.fm.ws.service.payment.TravelExpenseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 差旅费报销单客户端
 * @author lzb
 * 
 */
@Service("travelExpenseServiceClient")
public class TravelExpenseServiceClient extends ClientAutowiredBaseService implements
																			TravelExpenseService {
	
	@Override
	public FormBaseResult save(final TravelExpenseOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return travelExpenseService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormTravelExpenseInfo> queryPage(final TravelExpenseQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormTravelExpenseInfo>>() {
			@Override
			public QueryBaseBatchResult<FormTravelExpenseInfo> call() {
				return travelExpenseService.queryPage(order);
			}
		});
	}
	
	@Override
	public FormTravelExpenseInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FormTravelExpenseInfo>() {
			@Override
			public FormTravelExpenseInfo call() {
				return travelExpenseService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FormTravelExpenseInfo queryById(final long travelId) {
		return callInterface(new CallExternalInterface<FormTravelExpenseInfo>() {
			@Override
			public FormTravelExpenseInfo call() {
				return travelExpenseService.queryById(travelId);
			}
		});
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(FormTravelExpenseInfo travelInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public FcsBaseResult updateVoucher(UpdateVoucherOrder order) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<FormTravelExpenseDetailInfo> findApprlInfoByDeptId(final String deptId,
																	final Date applyTimeStart,
																	final Date applyTimeEnd) {
		return callInterface(new CallExternalInterface<List<FormTravelExpenseDetailInfo>>() {
			@Override
			public List<FormTravelExpenseDetailInfo> call() {
				return travelExpenseService.findApprlInfoByDeptId(deptId, applyTimeStart,
					applyTimeEnd);
			}
		});
	}
	
	@Override
	public FormBaseResult updatePayBank(final TravelExpenseOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return travelExpenseService.updatePayBank(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult syncForecast(long formId, boolean afterAudit) {
		return null;
	}

	@Override
	public FcsBaseResult confirmBranchPay(final ConfirmBranchPayOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return travelExpenseService.confirmBranchPay(order);
			}
		});
	}
}
