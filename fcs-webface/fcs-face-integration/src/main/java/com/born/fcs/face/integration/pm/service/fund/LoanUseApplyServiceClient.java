package com.born.fcs.face.integration.pm.service.fund;

import java.net.SocketTimeoutException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyFeeInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyReceiptBatchOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.LoanUseApplyResult;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;

@Service("loanUseApplyServiceClient")
public class LoanUseApplyServiceClient extends ClientAutowiredBaseService implements
																			LoanUseApplyService {
	
	@Override
	public LoanUseApplyResult saveApply(final FLoanUseApplyOrder order) {
		return callInterface(new CallExternalInterface<LoanUseApplyResult>() {
			
			@Override
			public LoanUseApplyResult call() throws SocketTimeoutException {
				return loanUseApplyWebService.saveApply(order);
			}
		});
	}
	
	@Override
	public FLoanUseApplyInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FLoanUseApplyInfo>() {
			
			@Override
			public FLoanUseApplyInfo call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FLoanUseApplyInfo queryByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<FLoanUseApplyInfo>() {
			
			@Override
			public FLoanUseApplyInfo call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryByApplyId(applyId);
			}
		});
	}
	
	@Override
	public List<FLoanUseApplyInfo> queryByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<FLoanUseApplyInfo>>() {
			
			@Override
			public List<FLoanUseApplyInfo> call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public List<FLoanUseApplyReceiptInfo> queryReceipt(final String projectCode) {
		return callInterface(new CallExternalInterface<List<FLoanUseApplyReceiptInfo>>() {
			
			@Override
			public List<FLoanUseApplyReceiptInfo> call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryReceipt(projectCode);
			}
		});
	}
	
	@Override
	public List<FLoanUseApplyFeeInfo> queryFeeByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<List<FLoanUseApplyFeeInfo>>() {
			
			@Override
			public List<FLoanUseApplyFeeInfo> call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryFeeByApplyId(applyId);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveApplyReceipt(final FLoanUseApplyReceiptBatchOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return loanUseApplyWebService.saveApplyReceipt(order);
			}
		});
	}
	
	@Override
	public List<FLoanUseApplyReceiptInfo> queryReceiptByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<List<FLoanUseApplyReceiptInfo>>() {
			
			@Override
			public List<FLoanUseApplyReceiptInfo> call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryReceiptByApplyId(applyId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<LoanUseApplyFormInfo> searchApplyForm(	final LoanUseApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<LoanUseApplyFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<LoanUseApplyFormInfo> call() throws SocketTimeoutException {
				return loanUseApplyWebService.searchApplyForm(order);
			}
		});
	}
	
	@Override
	public LoanUseApplyResult queryProjectAmount(final ProjectInfo project,
													final long currentApplyId) {
		return callInterface(new CallExternalInterface<LoanUseApplyResult>() {
			
			@Override
			public LoanUseApplyResult call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryProjectAmount(project, currentApplyId);
			}
		});
	}
	
	@Override
	public LoanUseApplyResult queryFormAmount(final FLoanUseApplyInfo apply) {
		return callInterface(new CallExternalInterface<LoanUseApplyResult>() {
			
			@Override
			public LoanUseApplyResult call() throws SocketTimeoutException {
				return loanUseApplyWebService.queryFormAmount(apply);
			}
		});
	}
	
	@Override
	public LoanUseApplyResult getApplyingAmountByProjectCode(final String projectCode,
																final long currentApplyId) {
		return callInterface(new CallExternalInterface<LoanUseApplyResult>() {
			
			@Override
			public LoanUseApplyResult call() throws SocketTimeoutException {
				return loanUseApplyWebService.getApplyingAmountByProjectCode(projectCode,
					currentApplyId);
			}
		});
	}
	
	@Override
	public LoanUseApplyResult getApplyingAmount(final ProjectInfo project, final long currentApplyId) {
		return callInterface(new CallExternalInterface<LoanUseApplyResult>() {
			
			@Override
			public LoanUseApplyResult call() throws SocketTimeoutException {
				return loanUseApplyWebService.getApplyingAmount(project, currentApplyId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectSimpleDetailInfo> selectLoanUseApplyProject(	final QueryProjectBase order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectSimpleDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectSimpleDetailInfo> call()
																		throws SocketTimeoutException {
				return loanUseApplyWebService.selectLoanUseApplyProject(order);
			}
		});
	}
}
