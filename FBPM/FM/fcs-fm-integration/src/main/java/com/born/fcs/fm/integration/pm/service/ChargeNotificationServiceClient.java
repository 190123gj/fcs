package com.born.fcs.fm.integration.pm.service;

import java.net.SocketTimeoutException;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;

@Service("chargeNotificationServiceClient")
public class ChargeNotificationServiceClient extends ClientAutowiredBaseService implements
																				ChargeNotificationService {
	@Autowired
	protected ChargeNotificationService chargeNotificationWebService;
	
	@Override
	public FormBaseResult saveChargeNotification(final FChargeNotificationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return chargeNotificationWebService.saveChargeNotification(order);
			}
		});
	}
	
	@Override
	public FChargeNotificationInfo queryChargeNotificationById(final long notificationId) {
		
		return callInterface(new CallExternalInterface<FChargeNotificationInfo>() {
			
			@Override
			public FChargeNotificationInfo call() throws SocketTimeoutException {
				return chargeNotificationWebService.queryChargeNotificationById(notificationId);
			}
		});
	}
	
	@Override
	public FcsBaseResult destroyChargeNotificationById(final FChargeNotificationOrder order) {
		
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return chargeNotificationWebService.destroyChargeNotificationById(order);
			}
		});
	}
	
	@Override
	public FChargeNotificationInfo queryChargeNotificationByFormId(final long formId) {
		
		return callInterface(new CallExternalInterface<FChargeNotificationInfo>() {
			
			@Override
			public FChargeNotificationInfo call() throws SocketTimeoutException {
				return chargeNotificationWebService.queryChargeNotificationByFormId(formId);
			}
		});
	}
	
	@Override
	public List<FChargeNotificationInfo> queryChargeNotificationByProjectCode(	final String projectCode) {
		
		return callInterface(new CallExternalInterface<List<FChargeNotificationInfo>>() {
			
			@Override
			public List<FChargeNotificationInfo> call() throws SocketTimeoutException {
				return chargeNotificationWebService
					.queryChargeNotificationByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FChargeNotificationInfo> queryChargeNotification(	final FChargeNotificationQueryOrder order) {
		
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FChargeNotificationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FChargeNotificationInfo> call()
																		throws SocketTimeoutException {
				return chargeNotificationWebService.queryChargeNotification(order);
			}
		});
	}
	
	@Override
	public ReportDataResult chargeNorificationExport(	final FChargeNotificationQueryOrder chargeNotificationQueryOrder) {
		return callInterface(new CallExternalInterface<ReportDataResult>() {
			
			@Override
			public ReportDataResult call() throws SocketTimeoutException {
				return chargeNotificationWebService
					.chargeNorificationExport(chargeNotificationQueryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FChargeNotificationResultInfo> queryChargeNotificationList(	final FChargeNotificationQueryOrder order) {
		
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FChargeNotificationResultInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FChargeNotificationResultInfo> call()
																				throws SocketTimeoutException {
				return chargeNotificationWebService.queryChargeNotificationList(order);
			}
		});
	}
	
	@Override
	public List<FChargeNotificationInfo> queryAuditingByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<FChargeNotificationInfo>>() {
			
			@Override
			public List<FChargeNotificationInfo> call() throws SocketTimeoutException {
				return chargeNotificationWebService.queryAuditingByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public RefundApplicationResult queryChargeTotalAmount(final String projectCode,
															final FeeTypeEnum feeType) {
		return callInterface(new CallExternalInterface<RefundApplicationResult>() {
			
			@Override
			public RefundApplicationResult call() throws SocketTimeoutException {
				return chargeNotificationWebService.queryChargeTotalAmount(projectCode, feeType);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectSimpleDetailInfo> selectChargeNotificationProject(	final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectSimpleDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectSimpleDetailInfo> call()
																		throws SocketTimeoutException {
				return chargeNotificationWebService.selectChargeNotificationProject(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult recoveryEenteustedLoanPrincipalWithdrawal(final String projectCode,
																	final String amount) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return chargeNotificationWebService.recoveryEenteustedLoanPrincipalWithdrawal(
					projectCode, amount);
			}
		});
	}
}
