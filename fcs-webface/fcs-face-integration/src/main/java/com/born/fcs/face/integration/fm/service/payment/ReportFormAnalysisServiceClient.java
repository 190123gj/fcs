package com.born.fcs.face.integration.fm.service.payment;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.OfficialCardInfo;
import com.born.fcs.fm.ws.order.payment.AccountPayQueryOrder;
import com.born.fcs.fm.ws.order.payment.OfficialCardQueryOrder;
import com.born.fcs.fm.ws.order.payment.ReportAccountDetailOrder;
import com.born.fcs.fm.ws.order.report.ProjectDepositQueryOrder;
import com.born.fcs.fm.ws.result.report.AccountDetailResult;
import com.born.fcs.fm.ws.result.report.DeptAccountTypeBankMessageResult;
import com.born.fcs.fm.ws.result.report.ProjectFinancialDetailResult;
import com.born.fcs.fm.ws.service.report.ReportFormAnalysisService;
import com.born.fcs.pm.ws.order.common.ProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;

@Service("reportFormAnalysisServiceClient")
public class ReportFormAnalysisServiceClient extends ClientAutowiredBaseService implements
																				ReportFormAnalysisService {
	
	@Override
	public QueryBaseBatchResult<OfficialCardInfo> queryOfficialCards(	final OfficialCardQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<OfficialCardInfo>>() {
			@Override
			public QueryBaseBatchResult<OfficialCardInfo> call() {
				return reportFormAnalysisWebService.queryOfficialCards(order);
			}
		});
	}
	
	@Override
	public DeptAccountTypeBankMessageResult usedAccountDetail() {
		return callInterface(new CallExternalInterface<DeptAccountTypeBankMessageResult>() {
			@Override
			public DeptAccountTypeBankMessageResult call() {
				return reportFormAnalysisWebService.usedAccountDetail();
			}
		});
	}
	
	@Override
	public AccountDetailResult accountDetail(final ReportAccountDetailOrder order) {
		return callInterface(new CallExternalInterface<AccountDetailResult>() {
			@Override
			public AccountDetailResult call() {
				return reportFormAnalysisWebService.accountDetail(order);
			}
		});
	}
	
	@Override
	public ProjectFinancialDetailResult projectFinancialDetail(	final FinancialProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<ProjectFinancialDetailResult>() {
			@Override
			public ProjectFinancialDetailResult call() {
				return reportFormAnalysisWebService.projectFinancialDetail(order);
			}
		});
	}
	
	@Override
	public ReportDataResult getEntrustedLoanDetail(final ProjectOrder order) {
		return callInterface(new CallExternalInterface<ReportDataResult>() {
			@Override
			public ReportDataResult call() {
				return reportFormAnalysisWebService.getEntrustedLoanDetail(order);
			}
		});
	}
	
	@Override
	public ReportDataResult getProjectDepositDetail(final ProjectDepositQueryOrder order) {
		return callInterface(new CallExternalInterface<ReportDataResult>() {
			@Override
			public ReportDataResult call() {
				return reportFormAnalysisWebService.getProjectDepositDetail(order);
			}
		});
	}

	@Override
	public ReportDataResult getProjectDepositInterest(final ProjectDepositQueryOrder order) {
		return callInterface(new CallExternalInterface<ReportDataResult>() {
			@Override
			public ReportDataResult call() {
				return reportFormAnalysisWebService.getProjectDepositInterest(order);
			}
		});
	}

	@Override
	public ReportDataResult getAccountPay(final AccountPayQueryOrder order) {
		return callInterface(new CallExternalInterface<ReportDataResult>() {
			@Override
			public ReportDataResult call() {
				return reportFormAnalysisWebService.getAccountPay(order);
			}
		});
	}
}
