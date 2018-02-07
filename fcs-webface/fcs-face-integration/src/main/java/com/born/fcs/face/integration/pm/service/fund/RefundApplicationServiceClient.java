package com.born.fcs.face.integration.pm.service.fund;

import java.net.SocketTimeoutException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyFeeInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyReceiptOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationQueryOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.LoanUseApplyResult;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.fund.RefundApplicationService;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.yjf.common.lang.util.money.Money;

@Service("refundApplicationServiceClient")
public class RefundApplicationServiceClient extends ClientAutowiredBaseService implements
																				RefundApplicationService {
	
	@Override
	public FRefundApplicationInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FRefundApplicationInfo>() {
			
			@Override
			public FRefundApplicationInfo call() throws SocketTimeoutException {
				return refundApplicationWebService.findById(id);
			}
		});
	}
	
	@Override
	public FRefundApplicationInfo findRefundApplicationByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FRefundApplicationInfo>() {
			
			@Override
			public FRefundApplicationInfo call() throws SocketTimeoutException {
				return refundApplicationWebService.findRefundApplicationByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FRefundApplicationInfo> query(	final FRefundApplicationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FRefundApplicationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FRefundApplicationInfo> call()
																		throws SocketTimeoutException {
				return refundApplicationWebService.query(order);
			}
		});
	}
	
	@Override
	public List<FRefundApplicationInfo> findByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<FRefundApplicationInfo>>() {
			
			@Override
			public List<FRefundApplicationInfo> call() throws SocketTimeoutException {
				return refundApplicationWebService.findByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FormBaseResult saveRefundApplication(final FRefundApplicationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return refundApplicationWebService.saveRefundApplication(order);
			}
		});
	}
	
	@Override
	public List<FRefundApplicationFeeInfo> findByRefundId(final long refundId) {
		return callInterface(new CallExternalInterface<List<FRefundApplicationFeeInfo>>() {
			
			@Override
			public List<FRefundApplicationFeeInfo> call() throws SocketTimeoutException {
				return refundApplicationWebService.findByRefundId(refundId);
			}
		});
	}
	
	@Override
	public RefundApplicationResult findAmountByChargeNotification(final String projectCode,
																	final long refundId,
																	final Boolean isEdit) {
		return callInterface(new CallExternalInterface<RefundApplicationResult>() {
			
			@Override
			public RefundApplicationResult call() throws SocketTimeoutException {
				return refundApplicationWebService.findAmountByChargeNotification(projectCode,
					refundId, isEdit);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryRefundApplicationByCharge(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() throws SocketTimeoutException {
				return refundApplicationWebService.queryRefundApplicationByCharge(order);
			}
		});
	}
	
	@Override
	public Money findRefundApplicationByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<Money>() {
			
			@Override
			public Money call() throws SocketTimeoutException {
				return refundApplicationWebService.findRefundApplicationByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult syncForecast(long formId, boolean afterAudit) {
		return null;
	}
}
