package com.born.fcs.fm.integration.pm.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectWithdrawingInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialSettlementInfo;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectWithdrawingQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialSettlementOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialSettlementQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryAmountBatchResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.yjf.common.lang.util.money.Money;

@Service("financialProjectServiceClient")
public class FinancialProjectServiceClient extends ClientAutowiredBaseService implements
																				FinancialProjectService {
	
	@Autowired
	private FinancialProjectService financialProjectWebService;
	
	@Override
	public QueryBaseBatchResult<ProjectFinancialInfo> query(final FinancialProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectFinancialInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectFinancialInfo> call() {
				return financialProjectWebService.query(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectFinancialInfo> queryPurchasing(	final FinancialProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectFinancialInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectFinancialInfo> call() {
				return financialProjectWebService.queryPurchasing(order);
			}
		});
	}
	
	@Override
	public ProjectFinancialInfo queryByCode(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectFinancialInfo>() {
			
			@Override
			public ProjectFinancialInfo call() {
				return financialProjectWebService.queryByCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult confirm(final FinancialProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProjectWebService.confirm(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult changeStatus(final String projectCode,
										final FinancialProjectStatusEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProjectWebService.changeStatus(projectCode, status);
			}
		});
	}
	
	@Override
	public double redeemingNum(final String projectCode, final long applyId) {
		return callInterface(new CallExternalInterface<Double>() {
			
			@Override
			public Double call() {
				return financialProjectWebService.redeemingNum(projectCode, applyId);
			}
		});
	}
	
	@Override
	public double transferingNum(final String projectCode, final long applyId) {
		return callInterface(new CallExternalInterface<Double>() {
			
			@Override
			public Double call() {
				return financialProjectWebService.transferingNum(projectCode, applyId);
			}
		});
	}
	
	@Override
	public FinancialProjectInterestResult caculateInterest(final String projectCode,
															final Date calculateDate) {
		return callInterface(new CallExternalInterface<FinancialProjectInterestResult>() {
			
			@Override
			public FinancialProjectInterestResult call() {
				return financialProjectWebService.caculateInterest(projectCode, calculateDate);
			}
		});
	}
	
	@Override
	public FinancialProjectInterestResult caculateProjectInterest(	final ProjectFinancialInfo project,
																	final Date calculateDate) {
		return callInterface(new CallExternalInterface<FinancialProjectInterestResult>() {
			
			@Override
			public FinancialProjectInterestResult call() {
				return financialProjectWebService.caculateProjectInterest(project, calculateDate);
			}
		});
	}
	
	@Override
	public Money caculateTransferInterest(final String projectCode, final Money transferPrice,
											final double transferNum, final Date transferDate) {
		return callInterface(new CallExternalInterface<Money>() {
			
			@Override
			public Money call() {
				return financialProjectWebService.caculateTransferInterest(projectCode,
					transferPrice, transferNum, transferDate);
			}
		});
	}
	
	@Override
	public Money caculateRedeemInterest(final String projectCode, final Money redeemPrice,
										final double redeemNum, final Date redeemDate) {
		return callInterface(new CallExternalInterface<Money>() {
			
			@Override
			public Money call() {
				return financialProjectWebService.caculateRedeemInterest(projectCode, redeemPrice,
					redeemNum, redeemDate);
			}
		});
	}
	
	@Override
	public double caculateInterestRate(final String projectCode, final Money interest,
										final Money principal, final Date caculateDate) {
		return callInterface(new CallExternalInterface<Double>() {
			
			@Override
			public Double call() {
				return financialProjectWebService.caculateInterestRate(projectCode, interest,
					principal, caculateDate);
			}
		});
	}
	
	@Override
	public QueryAmountBatchResult<FinancialProjectWithdrawingInfo> queryWithdraw(	final FinancialProjectWithdrawingQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryAmountBatchResult<FinancialProjectWithdrawingInfo>>() {
			
			@Override
			public QueryAmountBatchResult<FinancialProjectWithdrawingInfo> call() {
				return financialProjectWebService.queryWithdraw(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult initPorjectRecord(String setupProjectCode, Money applyAmount) {
		return null;
	}
	
	@Override
	public QueryAmountBatchResult<ProjectFinancialSettlementInfo> querySettlement(	final ProjectFinancialSettlementQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryAmountBatchResult<ProjectFinancialSettlementInfo>>() {
			
			@Override
			public QueryAmountBatchResult<ProjectFinancialSettlementInfo> call() {
				return financialProjectWebService.querySettlement(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult settlement(ProjectFinancialSettlementOrder order) {
		return null;
	}
}
